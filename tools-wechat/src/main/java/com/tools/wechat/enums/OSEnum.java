package com.tools.wechat.enums;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public enum OSEnum {
    WINDOWS("windows"), LINUX("linux"), DARWIN("darwin"), MAC("mac"), OTHER("");

    private String value;

    OSEnum(String value) {
        this.value = value;
    }

    public static OSEnum getLocalOS() {
        String os = System.getProperty("os.name");
        if(os==null){
            return  OTHER;
        }
        for (OSEnum e : values()) {
            if (os.toLowerCase().contains(e.getValue())) {
                return e;
            }
        }
        return OTHER;
    }

    public String getValue() {
        return value;
    }
}
