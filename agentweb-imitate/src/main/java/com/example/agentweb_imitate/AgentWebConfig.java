package com.example.agentweb_imitate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.example.agentweb_imitate.widget.LogUtils;

import java.io.File;


public class AgentWebConfig {

    static final String FILE_CACHE_PATH = "agentweb-cache";
    static final String AGENTWEB_CACHE_PATH = File.separator + "agentweb-cache";

    /**
     * 缓存路径
     */
    static String AGENTWEB_FILE_PATH;

    /**
     * DEBUG 模式 ， 如果需要查看日志请设置为 true
     */
    public static boolean DEBUG = false;

    /**
     * 当前操作系统是否低于 KITKAT
     */
    public static final boolean IS_KITKAT_OR_BELOW_KITKAT = Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT;

    /**
     * 默认 WebView  类型 。
     */
    public static final int WEBVIEW_DEFAULT_TYPE = 1;

    /**
     * 使用 AgentWebView
     */
    public static final int WEBVIEW_AGENTWEB_SAFE_TYPE = 2;
    /**
     * 自定义 WebView
     */
    public static final int WEBVIEW_CUSTOM_TYPE = 3;

    public static int WEBVIEW_TYPE = WEBVIEW_DEFAULT_TYPE;
    private static volatile boolean IS_INITIALIZED = false;
    private static final String TAG = AgentWebConfig.class.getSimpleName();
    /**
     * AgentWeb 的版本
     */
    public static final String AGENTWEB_VERSION = " agentweb/4.0.2 ";
    public static final String AGENTWEB_NAME = "AgentWeb";
    /**
     * 通过JS获取的文件大小， 这里限制最大为5MB ，太大会抛出 OutOfMemoryError
     */
    public static int MAX_FILE_LENGTH = 1024 * 1024 * 5;

    //获取Cookie
    public static String getCookiesByUrl(String url) {
        return CookieManager.getInstance() == null ? null : CookieManager.getInstance().getCookie(url);
    }

    public static void debug() {
        DEBUG = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    /**
     * 删除所有已经过期的 Cookies
     */
    public static void removeExpriredCookies() {
        CookieManager cookieManager = null;
        if ((cookieManager = CookieManager.getInstance()) != null) {
            cookieManager.removeExpiredCookie();

        }
    }

    /*同步清除Cookies*/
    private static void toSyncCookies() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
            return;
        }
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                CookieManager.getInstance().flush();
            }
        });
    }

    static String getDatabasesCachePath(Context context) {
        return context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
    }

    private static ValueCallback<Boolean> getDefaultIgnoreCallback() {
        return new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                LogUtils.i(TAG, "removeExpiredCookies:" + value);
            }
        };
    }
}
