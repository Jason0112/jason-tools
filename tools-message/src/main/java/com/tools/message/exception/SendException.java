package com.tools.message.exception;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :发送异常
 */
public class SendException extends Exception {
    public SendException() {
    }

    public SendException(String message) {
        super(message);
    }

    public SendException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendException(Throwable cause) {
        super(cause);
    }

    public SendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
