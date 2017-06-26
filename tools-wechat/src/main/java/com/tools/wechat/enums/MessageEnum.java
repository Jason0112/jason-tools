package com.tools.wechat.enums;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public enum MessageEnum {

    TEXT(1, "Text", "文本消息"),
    IMAGE(3, "Pic", "图片消息"),
    VOICE(34, "Voice", "语音消息"),
    VERIFY(37, "", "好友验证消息"),
    POSSIBLE_FRIEND(40, "", "可能朋友"),
    SHARE_CARD(42, "NameCard", "名片消息"),
    VIDEO(43, "Video", "小视频消息"),
    EMOTICON(47, "Pic", "表情消息"),
    LOCATION(48, "share", "位置消息"),
    APP(49, "share", "分享链接消息"),
    VOIP(50, "", "网络电话"),
    STATUS_NOTIFY(51, "", "微信初始化消息"),
    VOIP_NOTIFY(52, "", "网络电话通知"),
    VOIP_INVITE(53, "", "网络电话邀请"),
    MICRO_VIDEO(62, "Video", "短视频消息"),
    SYS_NOTICE(9999, "", "系统通知"),
    SYS(10000, "", "系统消息"),
    RECALLED(10002, "", "撤回消息"),
    OTHER(-1, "", "其它");

    /**
     * 微信编码
     */
    private int code;
    /**
     * 自定义类型
     */
    private String type;
    /**
     * 编码描述
     */
    private String desc;

    MessageEnum(int code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public static MessageEnum getEnumByCode(int code) {
        for (MessageEnum e : values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return OTHER;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
