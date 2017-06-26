package com.tools.kafka.util;

import com.tools.common.util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ：cheng.zhencai
 * @date ：2017/04/23
 * @description :配置文件加载类
 */


public class ConfigProperties {

    public static final String DEFAULT_FLAG = "true";
    public static boolean USER_TOPIC_FLAG;
    public static String USER_TOPIC_NAME;

    public static String COMPANY_FROM_DC_TOPIC;
    public static boolean COMPANY_FROM_DC_TOPIC_FLAG;

    static {
        Properties configProperties = new Properties();
        InputStream in = null;
        try {
            in = ResourceUtil.getResourceAsStream("properties/tools-config.properties");
            configProperties.load(in);

            USER_TOPIC_NAME = configProperties.getProperty("userTopicName");
            USER_TOPIC_FLAG = DEFAULT_FLAG.equals(configProperties.getProperty("userTopicFlag"));

            COMPANY_FROM_DC_TOPIC = configProperties.getProperty("companyFromDC");

            COMPANY_FROM_DC_TOPIC_FLAG = DEFAULT_FLAG.equals(configProperties.getProperty("companyFromDCFlag"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
