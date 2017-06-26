package com.tools.message.model;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :短信
 */
public class SMS {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 短信渠道
     */
    private String appKey;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
