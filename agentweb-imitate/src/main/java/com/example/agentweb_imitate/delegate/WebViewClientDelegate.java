package com.example.agentweb_imitate.delegate;

import android.webkit.WebViewClient;

/**
 * Author:zx on 2019/8/3021:22
 */
public class WebViewClientDelegate extends WebViewClient {
    private WebViewClient mDelegate;
    public static final String TAG = WebViewClientDelegate.class.getSimpleName();

   public WebViewClientDelegate(WebViewClient client) {
        this.mDelegate = client;
    }

    protected WebViewClient getDelegate() {
        return this.mDelegate;
    }

    protected void setDelegate(WebViewClient delegate) {
        this.mDelegate = delegate;
    }
}
