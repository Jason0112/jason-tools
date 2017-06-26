package com.tools.wechat.util;


import com.alibaba.fastjson.JSON;
import com.tools.wechat.bean.Core;
import com.tools.wechat.exception.HttpException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final CookieStore cookieStore = new BasicCookieStore();
    private static final HttpClientBuilder clientBuilder = HttpClients.custom();
    private static final CloseableHttpClient httpClient = clientBuilder.setDefaultCookieStore(cookieStore).build();

    private static Core core = Core.getInstance();

    private HttpUtil() {
    }


    public static String getCookie(String name) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }


    /**
     * 处理GET请求
     *
     * @param url       请求地址
     * @param paramMap  请求参数
     * @param redirect  是否转发
     * @param headerMap 请求头
     * @return 结果
     * @throws HttpException
     */
    public static String doGet(String url, Map<String, Object> paramMap, boolean redirect,
                               Map<String, String> headerMap) throws HttpException {
        HttpGet httpGet;
        if (paramMap == null || paramMap.isEmpty()) {
            httpGet = new HttpGet(url);
        } else {
            try {
                String params = EntityUtils.toString(new UrlEncodedFormEntity(covertParams2NVPS(paramMap), Consts.UTF_8));
                httpGet = new HttpGet(url + "?" + params);
            } catch (IOException e) {
                throw new HttpException("请求参数转换异常.", e);
            }
        }
        if (!redirect) {
            httpGet.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build()); // 禁止重定向
        }
        httpGet.setHeader("User-Agent", Config.USER_AGENT);
        if (headerMap != null) {
            Set<Map.Entry<String, String>> entries = headerMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return getResult(httpGet);
    }

    public static String doGet(String url, boolean redirect) throws HttpException {
        return doGet(url, null, redirect, null);
    }

    public static String doGet(String url, Map<String, Object> paramMap) throws HttpException {
        return doGet(url, paramMap, true, null);
    }

    public static HttpEntity doGet(String url) throws HttpException {

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", Config.USER_AGENT);
        CloseableHttpResponse response;
        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            throw new HttpException(e);
        }
        return response.getEntity();
    }

    /**
     * 将Map类型参数转化为NameValuePair类型的ArrayList
     *
     * @param paramMap 请求参数
     * @return 请求结果
     */
    private static ArrayList<BasicNameValuePair> covertParams2NVPS(Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<BasicNameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> param : paramMap.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
        }
        return pairs;
    }

    /**
     * 处理POST请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求结果
     */
    public static String doPost(String url, Map<String, Object> paramMap) throws HttpException {
        StringEntity paramEntity = new StringEntity(JSON.toJSONString(paramMap), Consts.UTF_8);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(paramEntity);
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("User-Agent", Config.USER_AGENT);
        return getResult(httpPost);
    }

    /**
     * 上传文件到服务器
     *
     * @param url      请求地址
     * @param filePath 文件请求路径
     * @return
     */
    public static String doPostFile(String url, String filePath) throws HttpException {
        File f = new File(filePath);
        if (!f.exists() && f.isFile()) {
            logger.info("file is not exist");
            return null;
        }
        String mimeType = new MimetypesFileTypeMap().getContentType(f);
        String mediaType = "";
        if (mimeType == null) {
            mimeType = "text/plain";
        } else {
            mediaType = mimeType.split("/")[0].equals("image") ? "pic" : "doc";
        }
        String lastModifieDate = new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new Date());
        long fileSize = f.length();
        String passTicket = (String) core.getLoginInfo().get("pass_ticket");
        String clientMediaId = String.valueOf(new Date().getTime())
                + String.valueOf(new Random().nextLong()).substring(0, 4);
        String webwxDataTicket = getCookie("webwx_data_ticket");
        if (webwxDataTicket == null) {
            logger.error("get cookie webwx_data_ticket error");
            return null;
        }
        Map<String, Object> paramMap = core.getParamMap();

        paramMap.put("ClientMediaId", clientMediaId);
        paramMap.put("TotalLen", fileSize);
        paramMap.put("StartPos", 0);
        paramMap.put("DataLen", fileSize);
        paramMap.put("MediaType", 4);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        builder.addTextBody("id", "WU_FILE_0", ContentType.TEXT_PLAIN);
        builder.addTextBody("name", filePath, ContentType.TEXT_PLAIN);
        builder.addTextBody("type", mimeType, ContentType.TEXT_PLAIN);
        builder.addTextBody("lastModifieDate", lastModifieDate, ContentType.TEXT_PLAIN);
        builder.addTextBody("size", String.valueOf(fileSize), ContentType.TEXT_PLAIN);
        builder.addTextBody("mediatype", mediaType, ContentType.TEXT_PLAIN);
        builder.addTextBody("uploadmediarequest", JSON.toJSONString(paramMap), ContentType.TEXT_PLAIN);
        builder.addTextBody("webwx_data_ticket", webwxDataTicket, ContentType.TEXT_PLAIN);
        builder.addTextBody("pass_ticket", passTicket, ContentType.TEXT_PLAIN);
        builder.addBinaryBody("filename", f, ContentType.create(mimeType), filePath);
        HttpEntity reqEntity = builder.build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", Config.USER_AGENT);
        httpPost.setEntity(reqEntity);
        return getResult(httpPost);
    }


    /**
     * 处理Http请求结果
     *
     * @param request 请求
     * @return 请求结果
     * @throws HttpException
     */
    private static String getResult(HttpRequestBase request) throws HttpException {
        //通过连接池获取HttpClient
        CloseableHttpResponse response = null;
        System.setProperty("jsse.enableSNIExtension", "false");
        try {
            logger.info(">>>>请求地址：{}", request.getRequestLine().getUri());
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("执行HttpGet方法出错：[{}]", statusCode);
                throw new HttpException("执行HttpGet方法出错：" + statusCode);
            }
            logger.info(">>>>响应结果：statusCode:{}", response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, Consts.UTF_8);
                response.close();
                return result;
            }
        } catch (Exception e) {
            logger.error("访问[{}]发生异常：",
                    request.getURI() != null ? request.getURI().toString() : request.getURI(), e);
            throw new HttpException("执行HttpClient发生异常");
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
