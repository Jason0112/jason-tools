package com.tools.interceptor.exception;

/**
 * date: 2017/6/11
 * description :
 *
 * @author : zhencai.cheng
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1240015972592825169L;
    private boolean logged;
    private int errorCode = 0;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String errMsg, boolean logged) {
        super(errMsg);
        this.logged = logged;
    }

    public ServiceException(int errorCode, String errMsg, boolean logged) {
        super(errMsg);
        this.logged = logged;
        this.errorCode = errorCode;
    }

    public ServiceException(String errMsg, Throwable throwable, boolean logged) {
        super(errMsg, throwable);
        this.logged = logged;
    }

    public ServiceException(int errorCode, String errMsg, Throwable throwable, boolean logged) {
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
