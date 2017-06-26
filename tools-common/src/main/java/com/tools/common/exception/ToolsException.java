package com.tools.common.exception;

/**
 * date: 2017/5/22
 * description :
 *
 * @author : zhencai.cheng
 */
public class ToolsException extends Exception {

    private static final long serialVersionUID = 1240015972592825169L;
    private boolean logged;
    private int errorCode = 0;

    public ToolsException(String errMsg, boolean logged) {
        super(errMsg);
        this.logged = logged;
    }

    public ToolsException(int errorCode, String errMsg, boolean logged) {
        super(errMsg);
        this.logged = logged;
        this.errorCode = errorCode;
    }

    public ToolsException(String errMsg, Throwable throwable, boolean logged) {
        super(errMsg, throwable);
        this.logged = logged;
    }

    public ToolsException(int errorCode, String errMsg, Throwable throwable, boolean logged) {
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