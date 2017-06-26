package com.tools.log.handler;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
public class MonitorHandler {

    public static String REPORT_INFO_SEND_DIRECTLY = "DIRECTLY";
    private static boolean initFlag = false;
    private static String monitorUrl = "";
    private static String monitorUrlHandShake = "";
    private static String monitorUrlHeartBeat = "";
    private static String monitorUrlReportInfo = "";
    private static String monitorUrlReportWarning = "";
    private static String monitorUrlReportError = "";
    private static String appPath = "";
    private static String appName = "";
    private static String appIdentifier = "";

    public MonitorHandler() {
    }

    public static void init(String appdir, String appname) {
        if (!initFlag) {
            try {
                Properties e = new Properties();
                e.load(MonitorHandler.class.getClassLoader().getResourceAsStream("monitor.properties"));
                monitorUrl = e.getProperty("monitor.report.url");
                monitorUrlHandShake = e.getProperty("monitor.report.url.handShake");
                monitorUrlHeartBeat = e.getProperty("monitor.report.url.heartBeat");
                monitorUrlReportInfo = e.getProperty("monitor.report.url.reportInfo");
                monitorUrlReportWarning = e.getProperty("monitor.report.url.reportWarning");
                monitorUrlReportError = e.getProperty("monitor.report.url.reportError");
                appPath = appdir;
                appName = appname;
                appIdentifier = getAppIdentifier();
                initFlag = true;
                System.out.println("......................................................................");
                System.out.println("..  初始化监控插件...");
                System.out.println("..  appPath=" + appPath);
                System.out.println("..  appName=" + appName);
                System.out.println("..  appIdentifier=" + appIdentifier);
                System.out.println("..  monitorUrl=" + monitorUrl);
                System.out.println("..  monitorUrlHandShake=" + monitorUrlHandShake);
                System.out.println("..  monitorUrlHeartBeat=" + monitorUrlHeartBeat);
                System.out.println("..  monitorUrlReportInfo=" + monitorUrlReportInfo);
                System.out.println("..  monitorUrlReportWarning" + monitorUrlReportWarning);
                System.out.println("..  monitorUrlReportError=" + monitorUrlReportError);
                System.out.println("......................................................................");
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

    }

    public static int handShake() {
        String result;
        try {
            if (!initFlag) {
                throw new Exception("监控插件尚未初始化,握手发送无效");
            }

            Map<String, String> e = new HashMap<>();
            e.put("appPath", appPath);
            e.put("appName", appName);
            e.put("appIdentifier", appIdentifier);
            e.put("monitorUrl", monitorUrl);
            result = RestHandler.restByPost(monitorUrl + monitorUrlHandShake, e);
        } catch (Exception var2) {
            result = "error##" + var2.getMessage();
        }

        return result.indexOf("success");
    }

    public static int heartBeat(int nextBeatSecond) throws Exception {
        String result;
        try {
            if (!initFlag) {
                throw new Exception("监控插件尚未初始化,心跳发送无效");
            }

            Map<String, Object> e = new HashMap<>();
            e.put("appPath", appPath);
            e.put("appName", appName);
            e.put("appIdentifier", appIdentifier);
            e.put("monitorUrl", monitorUrl);
            e.put("nextBeatSecond", nextBeatSecond);
            result = RestHandler.restByPost(monitorUrl + monitorUrlHeartBeat, e);
        } catch (Exception var3) {
            result = "error##" + var3.getMessage();
        }

        return result.indexOf("success");
    }

    public static int reportInfo(String message) {
        String result;
        try {
            if (!initFlag) {
                System.out.println("监控插件尚未初始化,不汇报普通消息.");
                return 0;
            }

            Map<String, String> e = new HashMap<>();
            e.put("appPath", appPath);
            e.put("appName", appName);
            e.put("appIdentifier", appIdentifier);
            e.put("monitorUrl", monitorUrl);
            e.put("message", message);
            result = RestHandler.restByPost(monitorUrl + monitorUrlReportInfo, e);
        } catch (Exception var3) {
            result = "error##" + var3.getMessage();
        }

        return result.indexOf("success");
    }

    public static int reportInfoImmediately(String message) {
        String result;
        try {
            if (!initFlag) {
                System.out.println("监控插件尚未初始化,不汇报普通消息.");
                return 0;
            }

            Map<String, String> e = new HashMap<>();
            e.put("appPath", appPath);
            e.put("appName", appName);
            e.put("appIdentifier", appIdentifier);
            e.put("monitorUrl", monitorUrl);
            e.put("message", message);
            e.put("sendDirectly", REPORT_INFO_SEND_DIRECTLY);
            result = RestHandler.restByPost(monitorUrl + monitorUrlReportInfo, e);
        } catch (Exception var3) {
            result = "error##" + var3.getMessage();
        }

        return result.indexOf("success");
    }

    public static int reportWarning(String message) {
        String result;
        try {
            if (!initFlag) {
                System.out.println("监控插件尚未初始化,不汇报警告消息");
                return 0;
            }

            Map<String, String> e = new HashMap<>();
            e.put("appPath", appPath);
            e.put("appName", appName);
            e.put("appIdentifier", appIdentifier);
            e.put("monitorUrl", monitorUrl);
            e.put("message", message);
            result = RestHandler.restByPost(monitorUrl + monitorUrlReportWarning, e);
        } catch (Exception var3) {
            result = "error##" + var3.getMessage();
        }

        return result.indexOf("success");
    }

    public static int reportError(String message, Throwable throwable) {
        String result;
        try {
            if (!initFlag) {
                System.out.println("监控插件尚未初始化,不汇报异常消息.");
                return 0;
            }

            Map<String, Object> e = new HashMap<>();
            e.put("appPath", appPath);
            e.put("appName", appName);
            e.put("appIdentifier", appIdentifier);
            e.put("monitorUrl", monitorUrl);
            e.put("message", message);
            e.put("throwable", throwable);
            result = RestHandler.restByPost(monitorUrl + monitorUrlReportError, e);
        } catch (Exception var4) {
            result = "error##" + var4.getMessage();
        }

        return result.indexOf("success");
    }

    private static String getAppIdentifier() {
        String dir = "[AppIdentifier:" + Math.random() + "]";
        InetAddress ia;

        try {
            ia = InetAddress.getLocalHost();
            dir = "[host:" + ia.getHostName() + "][" + MonitorHandler.class.getResource("/").toString().replace("file:", "dir:") + "]";
        } catch (Exception var3) {

        }

        return dir;
    }

    public static void main(String[] args) {
        InetAddress ia;

        try {
            ia = InetAddress.getLocalHost();
            String e = ia.getHostName();
            String localIp = ia.getHostAddress();
            System.out.println("本机名称是:" + e);
            System.out.println("本机的ip是:" + localIp);
            System.out.println("程序路径是:" + System.getProperty("user.dir"));
            System.out.println("程序名称是:" + System.getProperty("user.name"));
            System.out.println("程序名称是:" + System.getProperty("user.home"));
            System.out.println(getAppIdentifier());
            System.out.println(MonitorHandler.class.getResource("/"));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}
