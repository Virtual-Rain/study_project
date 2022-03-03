package com.study.commonlibrary.httpsdk;

import android.content.Context;

import com.study.commonlibrary.httpsdk.api.OkHttpApi;
import com.study.commonlibrary.httpsdk.cache.ResponseCache;

import java.net.HttpCookie;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * http请求管理类
 * 构建okHttpApi,(CronetApi 暂时不用)
 */
public class HttpManager {
    public ResponseCache responseCache;
    public NetWorkConfig netWorkConfig;
    public OkHttpApi okHttpApi;
    private String httpMagnagerTag = "AfHttpManager";

    /*构建OkHttpClient*/
    public HttpManager(Context context, NetWorkConfig netWorkConfig) {
        this.netWorkConfig = netWorkConfig;
        responseCache = new ResponseCache(context);
        /*OkHttpClient builder 设置*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /*设置cookie*/
        if (null != this.netWorkConfig.cookieJarImpl) {
            builder = builder.cookieJar(this.netWorkConfig.cookieJarImpl);
        }
        /*设置ssl参数*/
        if (null != this.netWorkConfig.sslParams) {
            builder = builder.sslSocketFactory(this.netWorkConfig.sslParams.sSLSocketFactory, this.netWorkConfig.sslParams.trustManager);
        }
        /*分发器 负责将每一次Requst进行分发，压栈到自己的线程池，并通过调用者自己不同的方式进行异步和同步处理！*/
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(20);
        dispatcher.setMaxRequestsPerHost(20);
        builder = builder.hostnameVerifier(this.netWorkConfig.sslParams.unSafeHostnameVerifier);
        /*请求时间*/
        builder = builder.callTimeout(netWorkConfig.timeout, TimeUnit.SECONDS);
        /*重连*/
        builder = builder.retryOnConnectionFailure(true);
        /*连接超时*/
        builder.connectTimeout(netWorkConfig.timeout, TimeUnit.SECONDS);
        /*读取超时*/
        builder.readTimeout(netWorkConfig.timeout, TimeUnit.SECONDS);
        /*写入超时*/
        builder.writeTimeout(netWorkConfig.timeout, TimeUnit.SECONDS);
        /*请求超时*/
        builder.callTimeout(netWorkConfig.timeout, TimeUnit.SECONDS);
        builder.dispatcher(dispatcher);

        /*okHttpClient 构建*/
        OkHttpClient okHttpClient = builder.build();

        okHttpApi=new OkHttpApi(okHttpClient,this);
    }
}
