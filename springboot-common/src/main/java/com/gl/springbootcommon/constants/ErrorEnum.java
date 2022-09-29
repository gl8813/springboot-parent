package com.gl.springbootcommon.constants;

public enum ErrorEnum {
    /**
     * 请求返回状态码和说明信息
     */
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "认证失败"),
    LOGIN_ERROR(401, "登陆失败，用户名或密码无效"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的资源不存在"),
    OPERATE_ERROR(405, "操作失败，请重试!"),
    TIME_OUT(408, "请求超时"),
    SERVER_ERROR(500, "服务器内部错误");

    private int code;

    private String description;

    ErrorEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return description;
    }
}
