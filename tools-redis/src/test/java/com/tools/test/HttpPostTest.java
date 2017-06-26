package com.tools.test;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/20
 * @description :
 */

import com.tools.common.util.ResourceUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpPostTest {
    void testPost(String urlStr) {
        try {
            String xmlInfo = getContent();
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Content-Length", xmlInfo.getBytes().length + "");

            System.out.println("xmlInfo=" + xmlInfo);
            con.getOutputStream().write(xmlInfo.getBytes("UTF-8"));


            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line;
            for (line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContent() throws IOException {
        InputStream inStream = ResourceUtil.getResourceAsStream("test.xml");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toString();
    }


    public static void main(String[] args) {


        String url = "http://10.0.3.148:8080/third/cshx/statusReport.json";
        new HttpPostTest().testPost(url);
    }
}