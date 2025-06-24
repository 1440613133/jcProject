package com.tencent.wxcloudrun.config;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    // 用户相关错误码
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_ALREADY_EXIST(1002, "用户已存在"),
    USER_LOGIN_FAILED(1003, "用户名或密码错误"),
    USER_ACCOUNT_FORBIDDEN(1004, "账号已被禁用"),
    USER_NOT_AUTH(1005, "用户未认证"),

    // 业务相关错误码
    BUSINESS_ERROR(2001, "业务处理失败"),
    DATA_NOT_FOUND(2002, "数据不存在"),
    DATA_ALREADY_EXIST(2003, "数据已存在"),

    // 系统相关错误码
    SYSTEM_ERROR(5000, "系统错误"),
    SERVICE_UNAVAILABLE(5001, "服务不可用");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
