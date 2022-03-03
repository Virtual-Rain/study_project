package com.study.commonlibrary.httpsdk;

import android.content.Context;

import com.study.commonlibrary.httpsdk.cookie.CookieJarImpl;
import com.study.commonlibrary.httpsdk.cookie.PersistentCacheCookie;
import com.study.commonlibrary.httpsdk.https.HttpsUtils;
import com.study.commonlibrary.uitls.MLog;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * 网络配置
 * cookie sslSocket 超时时间
 */
public class NetWorkConfig {
    /*host映射*/
    private HashMap<String, String> hosts = new HashMap<>();
    protected String defaultHost = "";
    /*SSL相关参数*/
    protected HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
    protected CookieJarImpl cookieJarImpl;
    protected int timeout = 0;
    protected MLog loger = new MLog();
    private boolean openLoger = false;

    public NetWorkConfig(Context context) {
        cookieJarImpl = new CookieJarImpl(new PersistentCacheCookie(context));
    }

    public NetWorkConfig debugMode() {
        openLoger = true;
        return this;
    }

    public boolean isOpenLoger() {
        return openLoger;
    }

    public NetWorkConfig addHost(String bussinessKey, String host) {
        hosts.put(bussinessKey, host);
        return this;
    }

    public NetWorkConfig timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public NetWorkConfig cookieJarImpl(CookieJarImpl cookieJarImpl) {
        this.cookieJarImpl = cookieJarImpl;
        return this;
    }

    public NetWorkConfig sslParams(HttpsUtils.SSLParams sslParams) {
        this.sslParams = sslParams;
        return this;
    }

    public NetWorkConfig defaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
        return this;
    }

    public HashMap<String, String> getHosts() {
        return hosts;
    }

    public String getDefaultHost() {
        return defaultHost;
    }
}
