package com.study.commonlibrary.httpsdk.api;

import com.bumptech.glide.manager.LifecycleListener;

/**
 * 耗时api基类，使用者自己构造组合模式
 */
public class BaseApi implements Callable, LifecycleListener {
    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
