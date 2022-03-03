package com.study.common.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * Author:zx on 2019/11/1417:47
 */
public class Announcement extends SimpleBannerInfo {
    private String content;

    public Announcement(String content) {
        this.content = content;
    }

    @Override
    public String getXBannerUrl() {
        return content;
    }
}
