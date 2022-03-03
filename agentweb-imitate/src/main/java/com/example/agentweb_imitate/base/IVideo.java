package com.example.agentweb_imitate.base;

import android.view.View;
import android.webkit.WebChromeClient;

/**
 * Author:zx on 2019/9/609:32
 */
public interface IVideo {
    void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback);

    void onHideCustomView();

    boolean isVideoState();
}
