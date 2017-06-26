package com.tools.test;


import com.tools.common.util.ResourceUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/26
 * @description :
 */
public class TestXml {


    public static  void main(String[] args) throws IOException {
        new TestXml().send();
    }
    String urlStr  = "http://localhost:8080/third/test.json";
    String url148  = "http://10.0.3.148:8080//third/cshx/statusReport.json";

    public void send() throws IOException {
        DataInputStream input = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] xmlData = this.getContent().getBytes();
        try {
            //获得到位置服务的链接
            URLConnection urlCon = getUrlConnection(urlStr,xmlData);

            input = new DataInputStream(urlCon.getInputStream());
            byte[] rResult;
            out = new java.io.ByteArrayOutputStream();
            byte[] bufferByte = new byte[256];
            int l ;
            while ((l = input.read(bufferByte)) > -1) {
                out.write(bufferByte, 0, l);
                out.flush();
            }
            rResult = out.toByteArray();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(new ByteArrayInputStream(rResult));
            NodeList nList = d.getElementsByTagName("statusbox");
            for (int i = 0; i < nList.getLength(); i++) {
                Element node = (Element) nList.item(i);
                System.out.println(this.getValue(node, "mobile"));
                System.out.println(this.getValue(node, "taskid"));
                System.out.println(this.getValue(node, "errorcode"));
                System.out.println(this.getValue(node, "receivetime"));
                System.out.println(this.getValue(node, "extno"));
                System.out.println("------------------");
            }

            urlCon = getUrlConnection(url148,rResult);
            System.out.println(urlCon.getContent().toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private URLConnection getUrlConnection(String uri ,byte[] xmlData) throws IOException {
        URL url = new URL(uri);
        URLConnection urlCon = url.openConnection();
        urlCon.setDoOutput(true);
        urlCon.setDoInput(true);
        urlCon.setUseCaches(false);
        //将xml数据发送到位置服务
        urlCon.setRequestProperty("Content-Type", "text/xml");
        urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
        DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
        printout.write(xmlData);
        printout.flush();
        printout.close();
        return urlCon;
    }

    private static String getValue(Element node, String name) {
        Node n =node.getElementsByTagName(name).item(0).getFirstChild();
        if(n==null){
            return  null;
        }
        return n.getNodeValue();
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

}
