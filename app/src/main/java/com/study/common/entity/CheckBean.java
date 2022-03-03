package com.study.common.entity;

/**
 * Author:zx on 2019/10/1917:32
 */
public class CheckBean {
    String name;
    boolean isCheck;

    public CheckBean(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
