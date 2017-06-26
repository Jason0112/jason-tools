package com.tools.message.model;

import java.util.Date;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description : 发送结果
 */
public class Monitor {

    private Integer id;
    /**
     * 短信id
     */
    private String messageId;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 账号
     */
    private String account;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 发送第三方结果
     */
    private String result;
    /**
     * 提交短信网关时间
     */
    private Date submitGateWayTime;
    /**
     * 用户接收时间
     */
    private Date userReceiveTime;
    /**
     * 运营商返回结果
     */
    private String operatorResult;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getSubmitGateWayTime() {
        return submitGateWayTime;
    }

    public void setSubmitGateWayTime(Date submitGateWayTime) {
        this.submitGateWayTime = submitGateWayTime;
    }

    public Date getUserReceiveTime() {
        return userReceiveTime;
    }

    public void setUserReceiveTime(Date userReceiveTime) {
        this.userReceiveTime = userReceiveTime;
    }

    public String getOperatorResult() {
        return operatorResult;
    }

    public void setOperatorResult(String operatorResult) {
        this.operatorResult = operatorResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
