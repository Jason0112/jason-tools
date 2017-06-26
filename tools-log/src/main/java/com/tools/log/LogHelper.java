package com.tools.log;

import com.tools.log.handler.MonitorHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
public class LogHelper {

    private static boolean isInit = false;
    private static Logger loggerRoot;

    private static Logger GetLoggerRoot() {
        if (loggerRoot == null) {
            loggerRoot = Logger.getRootLogger();
        }
        return loggerRoot;
    }

    static {
        try {
            init();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 初始化
     *
     * @throws IOException
     */
    public static void init() throws IOException {
        if (isInit) {
            return;
        }
        org.apache.log4j.LogManager.resetConfiguration();
        InputStream inCfg = LogHelper.class.getClassLoader().getResourceAsStream("properties/log4j.properties");
        Properties prop = new Properties();
        prop.clear();
        prop.load(inCfg);
        inCfg.close();
        org.apache.log4j.PropertyConfigurator.configure(prop);
        isInit = true;
        try {
            loggerRoot = Logger.getRootLogger();
        } catch (Exception ex) {
            System.out.println("初始化log4j配置失败：\r\n" + ex.getMessage());
        }
    }

    /**
     * debug
     *
     * @param message 信息
     */
    public static void debug(String message) {
        if (!isInit) {
            System.out.println("log4j还未初始化：" + message);
            return;
        }
        LogHelper.GetLoggerRoot().debug(message);
    }

    public static void info(String message) {
        if (!isInit) {
            System.out.println("log4j还未初始化：" + message);
            return;
        }
        LogHelper.GetLoggerRoot().info(message);
    }

    /**
     * 告警
     *
     * @param message 信息
     */
    public static void warn(String message) {
        if (!isInit) {
            System.out.println("log4j还未初始化：" + message);
            return;
        }
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes.length > 2) {
            LogHelper.GetLoggerRoot().warn(stes[2] + "-" + message);
        } else {
            LogHelper.GetLoggerRoot().warn(message);
        }
        // 汇报
        try {
            if (StringUtils.isNotBlank(message)) {
                MonitorHandler.reportWarning(message);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 错误信息
     *
     * @param message 消息
     */
    public static void error(String message) {
        if (!isInit) {
            System.out.println("log4j还未初始化：" + message);
            return;
        }
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes.length > 2) {
            LogHelper.GetLoggerRoot().error(stes[2] + "-" + message);
        } else {
            LogHelper.GetLoggerRoot().error(message);
        }
        // 汇报
        try {
            if (StringUtils.isNotBlank(message)) {
                MonitorHandler.reportError("异常信息", new Exception(message));
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 错误信息
     *
     * @param throwable 错误
     * @param message   消息
     */
    public static void error(final Throwable throwable, String message) {
        if (!isInit) {
            System.out.println("log4j还未初始化：" + message);
            return;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            LogHelper.GetLoggerRoot().error(stackTrace[2] + "-" + message, throwable);
        } else {
            LogHelper.GetLoggerRoot().error(throwable);
        }
        // 汇报
        try {
            if (StringUtils.isNotBlank(message)) {
                MonitorHandler.reportError(message, throwable);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
