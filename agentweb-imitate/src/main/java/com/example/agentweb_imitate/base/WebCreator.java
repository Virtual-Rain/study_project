package com.example.agentweb_imitate.base;

import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Author:zx on 2019/8/3020:40
 */
public interface WebCreator<T extends BaseIndicatorSpec> {
    T offer();

    WebCreator create();

    WebView getWebView();

    FrameLayout getWebParentLayout();
}
