package com.tools.common.advice;

/**
 * date: 2017/5/22
 * description :
 *
 * @author : zhencai.cheng
 */
public class TraceIDThreadLocal {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setTraceID(String traceID) {
        threadLocal.set(traceID);
    }

    public static String getTraceID() {
        return (String) threadLocal.get();
    }

    public static void removeTraceID() {
        threadLocal.remove();
    }
}
