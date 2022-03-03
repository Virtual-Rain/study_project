package com.study.commonlibrary.httpsdk.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;

import com.tencent.mmkv.MMKV;

/**
 * 响应缓存
 * 使用了LruCache
 */
public class ResponseCache {
    /*缓存*/
    private LruCache<String, String> cache = new LruCache<>(30);
    /*锁*/
    private Object lock = new Object();
    /*通用 key-value 组件:做网络数据缓存*/
    private MMKV mNetCache;

    /*构造类：MMKV初始化*/
    public ResponseCache(Context context) {
        MMKV.initialize(context);
        mNetCache = MMKV.defaultMMKV(1, "network");
    }

    /*保存内容*/
    public void save(String key, String content) {
        synchronized (lock) {
            if (!TextUtils.isEmpty(key)) {
                cache.put(key, content);
                mNetCache.encode(key, new ResponseCacheBean(key, content, System.currentTimeMillis()));
            }
        }
    }

    /*获取响应文本*/
    public String getResponse(String key) {
        synchronized (lock) {
            if (null != cache.get(key)) {
                return cache.get(key);
            } else {
                ResponseCacheBean responseCacheBean = getResponseCacheBean(key);
                return null != responseCacheBean ? responseCacheBean.content : "";
            }

        }
    }

    /*MMKV中获取value*/
    private ResponseCacheBean getResponseCacheBean(String key) {
        return mNetCache.decodeParcelable(key, ResponseCacheBean.class);
    }

    /*缓存是否在有效时间*/
    public boolean isCachePersistent(String key, long cacheTime) {
        synchronized (lock) {
            if (TextUtils.isEmpty(key)) {
                return false;
            }
            ResponseCacheBean responseCacheBean = getResponseCacheBean(key);
            if (null == responseCacheBean) {
                return false;
            }
            return !TextUtils.isEmpty(responseCacheBean.content) && cacheTime > (System.currentTimeMillis() - responseCacheBean.saveTime) / 1000;
        }
    }

    /*移除指定缓存*/
    public void clear(String key) {
        synchronized (lock) {
            if (!TextUtils.isEmpty(key)) {
                cache.remove(key);
                mNetCache.remove(key);
            }
        }
    }

    /*移除所有缓存*/
    public void clearAll() {
        synchronized (lock) {
            cache.evictAll();
            mNetCache.clearAll();
        }
    }
}
