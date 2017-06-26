package com.tools.redis.exception;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/26
 * @description :序列化异常
 */
public class SerializerException extends RedisException {

    public SerializerException() {
    }

    public SerializerException(String message) {
        super(message);
    }

    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializerException(Throwable cause) {
        super(cause);
    }

    public SerializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
