package com.tools.activemq.enums;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :消息枚举
 */
public enum MessageEventEnum {
    USER("user"),
    OTHER("other");

    MessageEventEnum(String type) {
        this.type = type;
    }

    private String type;

    /**
     * 根据消息类型获取消息枚举
     *
     * @param type 类型
     * @return 消息枚举
     */
    public static MessageEventEnum getEventEnumByType(String type) {
        if (type == null) {
            return MessageEventEnum.OTHER;
        }
        for (MessageEventEnum e : values()) {
            if (e.getType().equals(type)) {
                return e;
            }
        }
        return MessageEventEnum.OTHER;
    }


    public String getType() {
        return type;
    }
}
