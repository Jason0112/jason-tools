package com.tools.interceptor.filter;

import com.tools.interceptor.exception.ServiceException;
import com.tools.interceptor.model.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseResult handleBusinessException(ServiceException e) {
        LOGGER.error("业务异常:{}", e.getMessage());
        return new ResponseResult(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseResult handleException(Exception e) {
        LOGGER.error("程序异常:", e);
        return new ResponseResult(e.getMessage());
    }
}