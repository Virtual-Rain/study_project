package com.example.agentweb_imitate.base;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Author:zx on 2019/8/3020:46
 */
public interface IAgentWebSettings<T extends WebSettings> {
    IAgentWebSettings toSetting(WebView webView);

    T getWebSettings();
}
