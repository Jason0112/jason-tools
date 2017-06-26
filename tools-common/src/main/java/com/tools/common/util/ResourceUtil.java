package com.tools.common.util;


import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.URL;

/**
 * @author ：cheng.zhencai
 * @date ：2017/04/23
 * @description :读取文件得到InputStream工具类
 */
public class ResourceUtil {
    //传输协议file:
    public static final String PROTOCOL_FILE = "file:";
    //传输协议classpath*:
    public static final String PROTOCOL_CLASSPATH = "classpath*:";

    /**
     * 通过path,Class得到InputStream
     *
     * @param path   路径
     * @param class1 类型
     * @return 返回流
     */

    public static InputStream getResourceAsStream(String path, Class<?> class1) {
        InputStream inputstream = class1.getResourceAsStream(path);
        return inputstream;
    }

    /**
     * 通过path得到InputStream
     *
     * @param path 路径
     * @return 返回流
     */
    public static InputStream getResourceAsStream(String path) {
        String trip = path.startsWith("/") ? path.substring(1) : path;
        InputStream stream = null;

        if (!StringUtils.isEmpty(trip)) {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(trip);

            if (stream == null) {
                stream = ResourceUtil.class.getClassLoader().getResourceAsStream(trip);
            }

            if (stream == null) {
                stream = ResourceUtil.class.getResourceAsStream(trip);
            }
        }

        return stream;
    }

    /**
     * 通过path得到URL
     *
     * @param path 路径
     * @return 返回URL
     */
    public static URL getResource(String path) {
        String trip = path.startsWith("/") ? path.substring(1) : path;
        URL url = null;

        if (StringUtils.isNotEmpty(trip)) {
            url = Thread.currentThread().getContextClassLoader().getResource(trip);

            if (url == null) {
                url = ResourceUtil.class.getClassLoader().getResource(trip);
            }

            if (url == null) {
                url = ResourceUtil.class.getResource(trip);
            }
        }

        return url;
    }

}
