package com.tencent.wxcloudrun.config;

public class BusinessException extends RuntimeException {
    private ResultCode resultCode;

    public BusinessException(String message) {
        super(message);
        this.resultCode = ResultCode.BUSINESS_ERROR;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
