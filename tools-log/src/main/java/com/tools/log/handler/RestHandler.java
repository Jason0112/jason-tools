package com.tools.log.handler;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
public class RestHandler {

    public static <T> String restByPost(String uri, T param) {
        URL url;
        String result = null;

        try {
            url = new URL(uri);
            HttpURLConnection e = (HttpURLConnection) url.openConnection();
            e.setRequestMethod("POST");
            e.setDoInput(true);
            e.setDoOutput(true);
            e.setConnectTimeout(10000);
            e.setRequestProperty("Charset", "UTF-8");
            e.setRequestProperty("Accept-Charset", "UTF-8");
            e.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
            e.setRequestProperty("Connection", "Keep-Alive");
            e.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            if (param != null) {
                String sb = JSONObject.toJSONString(param);
                OutputStream os = e.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
                writer.write(sb);
                writer.close();
                os.close();
            }

            if (e.getResponseCode() != 200) {
                result = "error##" + e.getResponseCode() + ":" + e.getResponseMessage();
            } else {
                BufferedReader brd1 = new BufferedReader(new InputStreamReader(e.getInputStream()));
                StringBuffer sb1 = new StringBuffer();

                for (result = brd1.readLine(); result != null; result = brd1.readLine()) {
                    sb1.append(result);
                }

                result = "success##" + sb1.toString();
            }

            e.disconnect();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return result;
    }
}
