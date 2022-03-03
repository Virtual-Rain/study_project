package com.example.agentweb_imitate.base;

import com.example.agentweb_imitate.http.HttpHeaders;

import java.util.Map;

/**
 * Author:zx on 2019/8/3111:01
 */
public interface IUrlLoader {
    void loadUrl(String url);

    void loadUrl(String url, Map<String, String> headers);

    void reload();

    void loadData(String data, String mimeType, String encoding);

    void stopLoading();

    void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl);

    void postUrl(String urlk, byte[] params);

    HttpHeaders getHttpHeaders();
}
