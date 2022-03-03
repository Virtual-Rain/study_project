package com.example.agentweb_imitate.impl;

import android.view.View;
import android.webkit.WebChromeClient;

import com.example.agentweb_imitate.base.EventInterceptor;
import com.example.agentweb_imitate.base.IVideo;

/**
 * Author:zx on 2019/9/720:10
 */
public class VideoImpl implements IVideo, EventInterceptor {
    @Override
    public boolean event() {
        return false;
    }

    @Override
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {

    }

    @Override
    public void onHideCustomView() {

    }

    @Override
    public boolean isVideoState() {
        return false;
    }
}
