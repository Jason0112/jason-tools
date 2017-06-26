package com.tools.wechat.enums;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public enum RetCodeEnum {
    NORMAL("0", "普通"),
    LOGIN_OUT("1102", "退出"),
    LOGIN_OTHER_WHERE("1101", "其它地方登陆"),
    MOBILE_LOGIN_OUT("1102", "移动端退出"),
    UNKNOWN("9999", "未知");


    private String code;
    private String type;

    RetCodeEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public static RetCodeEnum getEnumByCode(String code) {
        if (code == null) {
            return RetCodeEnum.UNKNOWN;
        }
        for (RetCodeEnum e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return RetCodeEnum.UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

}
