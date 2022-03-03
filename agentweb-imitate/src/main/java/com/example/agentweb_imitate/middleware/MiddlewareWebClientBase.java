package com.example.agentweb_imitate.middleware;

import android.webkit.WebViewClient;

import com.example.agentweb_imitate.delegate.WebViewClientDelegate;

/**
 * Author:zx on 2019/8/3021:25
 */
public class MiddlewareWebClientBase extends WebViewClientDelegate {

    private MiddlewareWebClientBase mMiddlewareWebChromeBase;
    private static String TAG = MiddlewareWebChromeBase.class.getSimpleName();

    MiddlewareWebClientBase(MiddlewareWebClientBase client) {
        super(client);
    }

    protected MiddlewareWebClientBase(WebViewClient client) {
        super(client);
    }

    protected MiddlewareWebClientBase() {
        super(null);
    }

    final MiddlewareWebClientBase next() {
        return this.mMiddlewareWebChromeBase;
    }

    @Override
    protected void setDelegate(WebViewClient delegate) {
        super.setDelegate(delegate);
    }

    final MiddlewareWebClientBase enq(MiddlewareWebClientBase middlewareWebClientBase) {
        setDelegate(middlewareWebClientBase);
        this.mMiddlewareWebChromeBase = middlewareWebClientBase;
        return middlewareWebClientBase;
    }
}
