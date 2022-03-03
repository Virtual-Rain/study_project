package com.study.commonlibrary.httpsdk.bean.request;

import com.study.commonlibrary.httpsdk.HttpManager;

/**
 * 请求的Builder
 */
public abstract class CustomRequestBuilder {
    protected HttpManager httpManager;

    public CustomRequestBuilder(HttpManager httpManager) {
        this.httpManager = httpManager;
    }
}
