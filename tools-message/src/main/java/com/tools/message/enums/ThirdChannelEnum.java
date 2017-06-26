package com.tools.message.enums;

import com.tools.message.util.ConfigProperties;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :第三方渠道枚举
 */
public enum ThirdChannelEnum {
    HLQX("P_MF_HLQX_SMS", "鸿联企信", ConfigProperties.HLQX_SMS_SENDHTTP),
    OTHER("未匹配", null, null);
    /**
     * 渠道
     */
    private String appKey;
    /**
     * 渠道名称
     */
    private String name;
    /**
     * 渠道地址
     */
    private String http;

    ThirdChannelEnum(String appKey, String name, String http) {
        this.appKey = appKey;
        this.name = name;
        this.http = http;
    }

    public static ThirdChannelEnum getEnumByAppKey(String appKey) {
        if (appKey == null) {
            return ThirdChannelEnum.OTHER;
        }
        for (ThirdChannelEnum e : values()) {
            if (e.getAppKey().equals(appKey)) {
                return e;
            }
        }
        return ThirdChannelEnum.OTHER;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getHttp() {
        return http;
    }

    public String getName() {
        return name;
    }

}
