package com.tools.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/14
 * @description :
 */
public class DateUtil {

    /**
     * 日期格式年月日（yyyy-MM-dd）
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * yyyy/MM/dd
     */
    public static final String DEFAULT_DATE_FORMAT_LINE = "yyyy/MM/dd";
    /**
     * yyyyMMdd
     */
    public static final String DEFAULT_DATE_FORMAT_NO_SPACE = "yyyyMMdd";

    /**
     * 日期格式年月日时分秒（yyyy-MM-dd HH:mm:ss）
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT_LIEN = "yyyy/MM/dd HH:mm:ss";
    /**
     * yyyyMMddHHmmss
     */
    public static final String DEFAULT_DATETIME_FORMAT_NO_SPACE = "yyyyMMddHHmmss";
    /**
     * HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String format(Date date, String format) {

        return new SimpleDateFormat(format).format(date);
    }

    public static String format(Date date) {
        return format(date, DEFAULT_DATETIME_FORMAT);
    }

    public static Date parser(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }
    public static Date parser(String date) throws ParseException {
        return parser(date, DEFAULT_DATETIME_FORMAT);
    }

}
