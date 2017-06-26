package com.tools.wechat.exception;

import java.io.IOException;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
public class WechatException extends Exception {

    private static final long serialVersionUID = 1240015972592825169L;
    private boolean logged;
    private int errorCode = 0;

    public WechatException() {
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String errMsg, boolean logged) {
        super(errMsg);
        this.logged = logged;
    }

    public WechatException(int errorCode, String errMsg, boolean logged) {
        super(errMsg);
        this.logged = logged;
        this.errorCode = errorCode;
    }

    public WechatException(String errMsg, Throwable throwable) {
        this(errMsg, throwable, true);
    }

    public WechatException(String errMsg, Throwable throwable, boolean logged) {
        super(errMsg, throwable);
        this.logged = logged;
    }

    public WechatException(int errorCode, String errMsg, Throwable throwable, boolean logged) {
        super(errMsg, throwable);
        this.logged = logged;
        this.errorCode = errorCode;
    }

    public boolean isLogged() {
        return this.logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
