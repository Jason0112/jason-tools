package com.tools.wechat.bean;

/**
 * date: 2017/6/12
 * description :消息
 *
 * @author : zhencai.cheng
 */
public class Message {
    //
    private String traceID;
    //信息类型
    private Integer code;
    //消息内容
    private String content;
    //"NickName" -> "昵称被神隐藏"
    private String nickName;
    //"UserName" -> "@0cbb6b5fb89d5867de28bca8ace206eff641ff86c199bbcddf9de0ab05a5e4e9"
    private String userName;

    private String filePath;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
