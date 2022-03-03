package com.study.common.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    /**
     * code : 0
     * msg : ok
     */
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}