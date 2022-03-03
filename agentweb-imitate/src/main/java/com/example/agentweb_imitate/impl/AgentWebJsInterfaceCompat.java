package com.example.agentweb_imitate.impl;

import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agentweb_imitate.AgentWebZ;
import com.example.agentweb_imitate.widget.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Author:zx on 2019/9/720:18
 */
public class AgentWebJsInterfaceCompat {

    private WeakReference<AgentWebZ> mReference = null;
    private WeakReference<AppCompatActivity> mActivityWeakReference = null;
    public final String TAG = this.getClass().getSimpleName();

    AgentWebJsInterfaceCompat(AgentWebZ agentWeb, AppCompatActivity activity) {
        mReference = new WeakReference<AgentWebZ>(agentWeb);
        mActivityWeakReference = new WeakReference<AppCompatActivity>(activity);
    }

    @JavascriptInterface
    public void uploadFile() {
        uploadFile("*/*");
    }

    @JavascriptInterface
    public void uploadFile(String acceptType) {
        LogUtils.i(TAG, acceptType + "  " + mActivityWeakReference.get() + "  " + mReference.get());

    }
}
