package com.gl.springbootcommon.constants;

public enum StatusEnum {
    DISABLE(1, "禁用"),
    ENABLE(0, "启用");
    private int code;

    private String description;
    StatusEnum(int code, String description) {
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
