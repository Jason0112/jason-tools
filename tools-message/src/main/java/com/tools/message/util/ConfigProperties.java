package com.tools.message.util;

import com.tools.common.util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/7
 * @description :
 */
public class ConfigProperties {

    /**
     * 鸿联
     */
    public static String HLQX_SMS_SENDHTTP;

    /**************************邮件相关*****************************/
    /**
     * 邮件服务器
     */
    public static String MAIL_SERVER_HOST;
    public static String MAIL_SERVER_PORT;
    public static Boolean MAIL_SERVER_AUTH;
    public static String MAIL_FORM_ADDRESS;
    public static String MAIL_USER;
    public static String MAIL_PASSWORD;


    static {

        try {
            InputStream is = ResourceUtil.getResourceAsStream("properties/tools-message.properties");
            Properties properties = new Properties();
            properties.load(is);

            HLQX_SMS_SENDHTTP = properties.getProperty("hlqx.sms.http");
            MAIL_SERVER_HOST = properties.getProperty("email.server.host");
            MAIL_SERVER_PORT = properties.getProperty("email.server.port");
            MAIL_SERVER_AUTH = "true".equalsIgnoreCase(properties.getProperty("email.server.auth"));
            MAIL_FORM_ADDRESS = properties.getProperty("email.from.address");
            MAIL_USER = properties.getProperty("email.user");
            MAIL_PASSWORD = properties.getProperty("email.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
