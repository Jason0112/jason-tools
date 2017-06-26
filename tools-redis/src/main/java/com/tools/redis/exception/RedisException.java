package com.tools.redis.exception;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/29
 * @description :redis操作异常
 */
public class RedisException extends Exception {

    public RedisException() {
    }

    public RedisException(String message) {
        super(message);
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    public RedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
