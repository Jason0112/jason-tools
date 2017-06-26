
package com.tools.interceptor.util;

public class TraceIDThreadLocal {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public TraceIDThreadLocal() {
    }

    public static void setTraceID(String traceID) {
        threadLocal.set(traceID);
    }

    public static String getTraceID() {
        return threadLocal.get();
    }

    public static void removeTraceID() {
        threadLocal.remove();
    }
}
