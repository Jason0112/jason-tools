package com.tools.message.model;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/7
 * @description :邮件
 */
public class Email {

    private String sendAddress;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;

    private boolean isHtml;
    //是否有附件
    private boolean isWithAttachment;
    // 邮件附件的文件名
    private String[] attachFileNames = {};

    public Email() {
        this.isHtml = false;
        this.isWithAttachment = false;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    public boolean isWithAttachment() {
        return isWithAttachment;
    }

    public void setIsWithAttachment(boolean isWithAttachment) {
        this.isWithAttachment = isWithAttachment;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setIsHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }
}
