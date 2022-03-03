package com.study.commonlibrary.httpsdk.api;

import com.study.commonlibrary.httpsdk.HttpManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class OkHttpApi extends BaseNetApi{
    public OkHttpClient mOkHttpClient;
    Call mCall;

    public OkHttpApi(OkHttpClient okHttpClient, HttpManager httpManager) {
        super(httpManager);
        this.mOkHttpClient = okHttpClient;
    }
}
