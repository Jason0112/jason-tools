package com.tools.wechat.filter;

import com.tools.wechat.exception.WechatException;
import com.tools.wechat.util.TraceIDThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({WechatException.class})
    public ResponseEntity handleBusinessException(WechatException e) {
        LOGGER.error("业务异常:{}", e.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("traceID", TraceIDThreadLocal.getTraceID());
        result.put("code", e.getErrorCode());
        result.put("message", e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        LOGGER.error("程序异常:", e);
        Map<String, Object> result = new HashMap<>();
        result.put("traceID", TraceIDThreadLocal.getTraceID());
        result.put("message", e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}