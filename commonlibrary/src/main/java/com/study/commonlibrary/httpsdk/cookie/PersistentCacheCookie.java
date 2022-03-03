package com.study.commonlibrary.httpsdk.cookie;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 持久化Cookie
 * 存储到MMKV host name cookie
 */
public class PersistentCacheCookie implements CacheCookie {
    private HashMap<String, ConcurrentHashMap<String, Cookie>> mCookies;
    private final String KEY_HOSTS = "KEY_HOSTS";
    public static MMKV sMMKVHosts;
    public static MMKV sMMKVNames;
    public static MMKV sMMKVCookies;
    private Set<String> mSetHosts = new TreeSet<>();

    public PersistentCacheCookie(Context context) {
        MMKV.initialize(context);
        mCookies = new HashMap<>();
        sMMKVHosts = MMKV.defaultMMKV(1, "hosts");
        sMMKVNames = MMKV.defaultMMKV(1, "names");
        sMMKVCookies = MMKV.defaultMMKV(1, "cookies");
        Set<String> tempHosts = sMMKVHosts.decodeStringSet(KEY_HOSTS);
        if (tempHosts != null && tempHosts.size() > 0) {
            mSetHosts.addAll(tempHosts);
            for (String host : tempHosts) {
                String[] cookieNames = TextUtils.split(sMMKVNames.decodeString(host), ",");
                for (String name : cookieNames) {
                    ParcelCookie parcelCookie = sMMKVCookies.decodeParcelable(name, ParcelCookie.class);
                    if (parcelCookie != null) {
                        Cookie cookie = parcelCookie.getCookie();
                        if (cookie != null) {
                            if (!mCookies.containsKey(host)) {
                                mCookies.put(host, new ConcurrentHashMap<String, Cookie>());
                            }
                            mCookies.get(host).put(name, cookie);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void add(HttpUrl uri, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            String name = getCookieToken(cookie);
            if (cookie.persistent()) {
                if (!mCookies.containsKey(uri.host())) {
                    mCookies.put(uri.host(), new ConcurrentHashMap<String, Cookie>());
                }
                mCookies.get(uri.host()).put(name, cookie);
            } else {
                if (mCookies.containsKey(uri.host())) {
                    mCookies.get(uri.host()).remove(name);
                } else {
                    return;
                }
            }
            if (!mSetHosts.contains(uri.host())) {
                mSetHosts.add(uri.host());
                sMMKVHosts.decodeStringSet(KEY_HOSTS, mSetHosts);
            }
            sMMKVNames.encode(uri.host(), TextUtils.join(",", mCookies.get(uri.host()).keySet()));
            sMMKVCookies.encode(name, new ParcelCookie(cookie));
        }
    }

    @Override
    public List<Cookie> get(HttpUrl uri) {
        ArrayList<Cookie> temp = new ArrayList<>();
        if (mCookies.containsKey(uri.host())) {
            Collection<Cookie> cookies = mCookies.get(uri.host()).values();
            for (Cookie cookie : cookies
            ) {
                if (cookie.expiresAt() < System.currentTimeMillis()) {
                    remove(uri, cookie);
                } else {
                    temp.add(cookie);
                }
            }
        }
        return temp;
    }

    @Override
    public List<Cookie> getCookies() {
        ArrayList<Cookie> temp = new ArrayList<>();
        for (String key : mCookies.keySet()
        ) {
            temp.addAll(mCookies.get(key).values());
        }
        return temp;
    }

    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (mCookies.containsKey(uri.host()) && mCookies.get(uri.host()).containsKey(name)) {
            mCookies.get(uri.host()).remove(name);
            if (sMMKVCookies.contains(name)) {
                sMMKVCookies.remove(name);
            }
            sMMKVCookies.encode(uri.host(), TextUtils.join(",", mCookies.get(uri.host()).keySet()));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        mSetHosts.clear();
        sMMKVHosts.clearAll();
        sMMKVNames.clearAll();
        sMMKVCookies.clearAll();
        mCookies.clear();
        return false;
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }
}
