package com.tools.wechat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2017/6/5
 * description :* 基本请求参数
 * 1. webWxInit      初始化
 * 2. wxStatusNotify 微信状态通知
 *
 * @author : zhencai.cheng
 */
public enum RequestParaEnum {
    Uin("Uin", "wxuin"),
    Sid("Sid", "wxsid"),
    Skey("Skey", "skey"),
    DeviceID("DeviceID", "pass_ticket");

    private String param;
    private String value;
    private static final ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    RequestParaEnum(String param, String value) {
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }

    public static Map<String, String> getCodeMap() {
        if (threadLocal.get() == null) {
            synchronized (RequestParaEnum.class) {
                Map<String, String> codeMap = new HashMap<>();
                for (RequestParaEnum e : values()) {
                    codeMap.put(e.getParam(), e.getValue());
                }
                threadLocal.set(codeMap);
                return codeMap;
            }
        }

        return threadLocal.get();
    }
}
