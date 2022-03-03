package com.example.agentweb_imitate.impl;

import android.app.Activity;
import android.os.Handler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

import com.example.agentweb_imitate.view.WebParentLayout;

/**
 * Author:zx on 2019/9/612:03
 */
public class DefaultUIController extends AbsAgentWebUIController {
    @Override
    protected void bindSupportWebParent(WebParentLayout webParentLayout, Activity activity) {

    }

    @Override
    public void onJsAlert(WebView view, String url, String message) {

    }

    @Override
    public void onOpenPagePrompt(WebView view, String url, Handler.Callback callback) {

    }

    @Override
    public void onJsConfirm(WebView view, String url, String message, JsResult jsResult) {

    }

    @Override
    public void onSelectItemsPrompt(WebView view, String url, String[] ways, Handler.Callback callback) {

    }

    @Override
    public void onForceDownloadAlert(String url, Handler.Callback callback) {

    }

    @Override
    public void onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult jsPromptResult) {

    }

    @Override
    public void onMainFrameError(WebView view, int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onShowMainFrame() {

    }

    @Override
    public void onLoading(String msg) {

    }

    @Override
    public void onCancelLoading() {

    }

    @Override
    public void onShowMessage(String message, String intent) {

    }

    @Override
    public void onPermissionsDeny(String[] permissions, String permissionType, String action) {

    }
}
