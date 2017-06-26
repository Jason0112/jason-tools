package com.tools.kafka.exception;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description : topic消息发送异常
 */
public class TopicSendException extends Exception {
    public TopicSendException() {
    }

    public TopicSendException(String message) {
        super(message);
    }

    public TopicSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicSendException(Throwable cause) {
        super(cause);
    }

    public TopicSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
