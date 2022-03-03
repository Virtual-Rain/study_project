package com.study.commonlibrary.httpsdk.cookie;


import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
/**
 * okhttp的cookie实现类，对于188那样的奇葩cookie处理则继承该类重新saveFromResponse和loadForRequest方法即可
 *
 */
public class CookieJarImpl implements CookieJar {
    private CacheCookie cacheCookie;

    public CookieJarImpl(CacheCookie cacheCookie) {
        this.cacheCookie = cacheCookie;
    }

    private CookieJarImpl() {
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cacheCookie.add(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cacheCookie.get(url);
    }

    public List<Cookie> getCookies() {
        return cacheCookie.getCookies();
    }
}
