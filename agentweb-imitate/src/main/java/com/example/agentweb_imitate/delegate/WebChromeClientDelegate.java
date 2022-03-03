package com.example.agentweb_imitate.delegate;

import android.webkit.WebChromeClient;

/**
 * Author:zx on 2019/8/3020:59
 */
public class WebChromeClientDelegate extends WebChromeClient {
    private WebChromeClient mDelegate;

    protected WebChromeClient getDelegate() {
        return mDelegate;
    }

    public WebChromeClientDelegate(WebChromeClient webChromeClient) {
        this.mDelegate = webChromeClient;
    }

    protected void setDelegate(WebChromeClient delegate) {
        this.mDelegate = delegate;
    }
}
