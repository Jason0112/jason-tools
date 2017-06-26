package com.tools.common.advice;

import com.tools.common.exception.ToolsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * date: 2017/5/22
 * description : 全局异常捕获
 *
 * @author : zhencai.cheng
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ToolsException.class})
    public ResponseResult handleBusinessException(ToolsException e) {
        logger.error("业务异常:{}", e.getMessage());
        return new ResponseResult(TraceIDThreadLocal.getTraceID(), e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseResult handleException(Exception e) {
        logger.error("程序异常:", e);
        return new ResponseResult(TraceIDThreadLocal.getTraceID(), -100, e.getMessage());
    }
}
