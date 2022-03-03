package com.study.common.entity;

import java.io.Serializable;
import java.util.List;

public class LongDragoConfig extends BaseEntity implements Serializable {
    public List<NoticeConfigInfo> data;

    public List<NoticeConfigInfo> getData() {
        return data;
    }

    public void setData(List<NoticeConfigInfo> data) {
        this.data = data;
    }
}

