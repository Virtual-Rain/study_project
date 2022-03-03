package com.study.common.entity;

public class Generic<T>{
    T value;

    public Object getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
