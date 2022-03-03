package com.example.agentweb_imitate.http;

import android.os.Build;
import android.text.TextUtils;


import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.Map;

/**
 * Author:zx on 2019/8/3111:20
 */
public class HttpHeaders {
    public static HttpHeaders create() {
        return new HttpHeaders();
    }

    private final Map<String, Map<String, String>> mHeaders;

    HttpHeaders() {
        mHeaders = new ArrayMap<String, Map<String, String>>();
    }

    public Map<String, String> getHeaders(String url) {
        String subUrl = subBaseUrl(url);
        if (mHeaders.get(subUrl) == null) {
            Map<String, String> headers = new ArrayMap<>();
            mHeaders.put(subUrl, headers);
            return headers;
        }
        return mHeaders.get(subUrl);
    }

    public Map<String, Map<String, String>> getHeaders() {
        return this.mHeaders;
    }

    public void additionalHttpHeader(String url, String k, String v) {
        if (null == url) {
            return;
        }
        url = subBaseUrl(url);
        Map<String, Map<String, String>> mHeaders = getHeaders();
        Map<String, String> headersMap = mHeaders.get(subBaseUrl(url));
        if (null == headersMap) {
            headersMap = new ArrayMap<>();
        }
        headersMap.put(k, v);
        mHeaders.put(url, headersMap);

    }

    public void additionalHttpHeader(String url, Map<String, String> headers) {
        if (null == url) {
            return;
        }
        String subUrl = subBaseUrl(url);
        Map<String, Map<String, String>> mHeaders = getHeaders();
        Map<String, String> headersMap = headers;
        if (null == headersMap) {
            headersMap = new ArrayMap<>();
        }
        mHeaders.put(subUrl, headersMap);
    }

    private void removeHttpHeader(String url, String k) {
        if (null == url) {
            return;
        }
        String subUrl = subBaseUrl(url);
        Map<String, Map<String, String>> mHeaders = getHeaders();
        Map<String, String> headersMap = mHeaders.get(subUrl);
        if (null != headersMap) {
            headersMap.remove(k);
        }
    }



    private String subBaseUrl(String originUrl) {
        if (TextUtils.isEmpty(originUrl)) {
            return originUrl;
        }
        int index = originUrl.indexOf("?");
        if (index <= 0) {
            return originUrl;
        }
        String subBaseUrl = originUrl.substring(0, index);
        return subBaseUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpHeaders{" +
                "mHeaders=" + mHeaders +
                '}';
    }
}
