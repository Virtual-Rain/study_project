package com.study.commonlibrary.httpsdk.bean;

/**
 * 缓存数据类
 */
public class CacheBean {
    private String key;
    /**
     * 缓存有效时间，参数是秒
     */
    private long cacheSecond;

    public CacheBean(String key, long cacheSecond) {
        this.key = key;
        this.cacheSecond = cacheSecond;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCacheSecond() {
        return cacheSecond;
    }

    public void setCacheSecond(long cacheSecond) {
        this.cacheSecond = cacheSecond;
    }
}
