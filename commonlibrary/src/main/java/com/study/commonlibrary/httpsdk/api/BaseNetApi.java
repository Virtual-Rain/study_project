package com.study.commonlibrary.httpsdk.api;

import android.os.Handler;
import android.os.Looper;

import com.study.commonlibrary.httpsdk.HttpManager;
import com.study.commonlibrary.httpsdk.cookie.CacheCookie;

public class BaseNetApi implements Callable {
    protected HttpManager httpManager;
    protected Handler handler = new Handler(Looper.getMainLooper());

    public BaseNetApi(HttpManager httpManager) {
        this.httpManager = httpManager;
    }
}
