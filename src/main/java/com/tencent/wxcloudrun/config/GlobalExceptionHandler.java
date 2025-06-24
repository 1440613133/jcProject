package com.tencent.wxcloudrun.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        logger.error("系统异常", e);
        return Result.failed(ResultCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("参数异常", e);
        return Result.validateFailed(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException e) {
        logger.error("业务异常", e);
        return Result.failed(e.getResultCode(), e.getMessage());
    }
}
