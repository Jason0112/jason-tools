package com.tools.wechat.enums;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public enum  LoginEnum {

    LOGIN_ICON("loginicon", "true"),
    UUID("uuid", ""),
    TIP("tip", "0"),
    R("r", ""),
    OTHER("_", "");

    private String para;
    private String value;

    LoginEnum(String para, String value) {
        this.para = para;
        this.value = value;
    }

    public String para() {
        return para;
    }

    public String value() {
        return value;
    }
}
