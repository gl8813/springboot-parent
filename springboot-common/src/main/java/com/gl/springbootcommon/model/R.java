package com.gl.springbootcommon.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class R implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS_STATUS = 0;

    public static final int FAILED_STATUS = 1;

    private int code;

    private String msg;

    private Object data;

    public static R ok(String msg) {
        R r = new R();
        r.setCode(SUCCESS_STATUS);
        r.setMsg(msg);
        return r;
    }

    public static R ok() {
        return ok("success");
    }

    public static R error() {
        return error("error");
    }

    public static R error(String msg) {
        R r = new R();
        r.setCode(FAILED_STATUS);
        r.setMsg(msg);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public R data(Object data) {
        this.setData(data);
        return this;
    }
}
