package com.example.agentweb_imitate.base;

import android.webkit.WebView;

import com.example.agentweb_imitate.base.BaseIndicatorSpec;

/**
 * Author:zx on 2019/8/3020:51
 */
public interface IndicatorController {
    void progress(WebView v, int newProgress);

    BaseIndicatorSpec offerIndicator();

    void showIndicator();

    void setProgress(int newProgress);

    void finish();
}
