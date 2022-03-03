package com.example.agentweb_imitate.impl;

import android.os.Handler;
import android.webkit.WebView;

import com.example.agentweb_imitate.base.IUrlLoader;
import com.example.agentweb_imitate.http.HttpHeaders;
import com.example.agentweb_imitate.widget.LogUtils;

import java.util.Map;

/**
 * Author:zx on 2019/8/3114:42
 */
public class UrlLoaderImpl implements IUrlLoader {

    public static final String TAG = UrlLoaderImpl.class.getSimpleName();

    private Handler mHandler = null;
    private WebView mWebView ;

    public UrlLoaderImpl(WebView webView){
        this.mWebView=webView;
    }

    @Override
    public void loadUrl(String url) {

    }

    @Override
    public void loadUrl(String url, Map<String, String> headers) {
        LogUtils.i(TAG, "loadUrl" + url + " headers:" + headers);
        if(headers==null||headers.isEmpty()){
            this.mWebView.loadUrl(url);
        }else{
            this.mWebView.loadUrl(url,headers);
        }
    }

    @Override
    public void reload() {

    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {

    }

    @Override
    public void postUrl(String urlk, byte[] params) {

    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return null;
    }
}
