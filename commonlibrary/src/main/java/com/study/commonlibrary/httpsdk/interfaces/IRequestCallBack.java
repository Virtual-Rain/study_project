package com.study.commonlibrary.httpsdk.interfaces;

/**
 * 请求结果回调
 */
public interface IRequestCallBack<T> {
    void onSuccess(T t);

    void onError(String exception);

    void onProcess(float process, long totalSize);

    void onReLoginForTokenInvalid();
}
