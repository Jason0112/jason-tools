package com.tools.mybatis;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public abstract class DateUtils {

    public static Date now() {
        return new Date();
    }

    public static Long nowTime() {
        return now().getTime();
    }

    public static Date daysBefore(Date time, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());

        calendar.add(Calendar.DATE, -1 * days);

        return calendar.getTime();
    }


    public static String date2str(Date date, DateFormat dateFormat) {
        if (date == null)
            return null;

        return dateFormat.format(date);
    }

    public static String date2str(Date date, String format) {
        if (date == null)
            return null;

        return new SimpleDateFormat(format).format(date);
    }

    public static Date str2Date(String input, DateFormat format) {
        try {
            return input == null ? null : format.parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date str2Date(String input, String format) {
        try {
            return input == null ? null : new SimpleDateFormat(format).parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static Date daysAfter(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

    public static Date daysAfter(Date time, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());

        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }
    public static Date minuteAfter(Date time, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());

        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    public static Date today() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date HourAfter(Date time, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());

        calendar.add(Calendar.HOUR, count);

        return calendar.getTime();
    }
    public static Date getMinute(Date time, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());

        calendar.add(Calendar.MINUTE, count);

        return calendar.getTime();
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 一天还剩余多少时间（s）
     */
    public static long remainTime(){
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        return TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 格式化日期
     * @param date 日期
     * @param dayFormat 格式化格式
     * @return 返回字面串日期
     */
    public static String format(Date date, String dayFormat) {
        if (date == null || StringUtils.isBlank(dayFormat)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dayFormat);
        return sdf.format(date);
    }

    /**
     * 解析字符串日期
     * @param date 字符日期
     * @param format 格式
     * @return 日期
     */
    public static Date parserDate(String date, String format) {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(format)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
