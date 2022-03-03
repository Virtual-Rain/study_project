package com.example.agentweb_imitate.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.agentweb_imitate.model.JsCallJava;
import com.example.agentweb_imitate.widget.LogUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AgentWebView extends WebView {
    private static final String TAG = AgentWebView.class.getSimpleName();
    private Map<String, JsCallJava> mJsCallJavas;//js请求
    private Map<String, String> mInjectJavaScripts;//js注入
    private FiexdOnReceivedTitle mFiexdOnReceivedTitle;
    private boolean mIsInited;
    private Boolean mIsAccessibilityEnableOriginal;

    public AgentWebView(Context context) {
        this(context, null);
    }

    public AgentWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        removeSearchBoxJavaBridge();
        mIsInited = true;
        mFiexdOnReceivedTitle = new FiexdOnReceivedTitle();
    }

    /**
     * 经过大量的测试，按照以下方式才能保证JS脚本100%注入成功：
     * 1、在第一次loadUrl之前注入JS（在addJavascriptInterface里面注入即可，setWebViewClient和setWebChromeClient要在addJavascriptInterface之前执行）；
     * 2、在webViewClient.onPageStarted中都注入JS；
     * 3、在webChromeClient.onProgressChanged中都注入JS，并且不能通过自检查（onJsPrompt里面判断）JS是否注入成功来减少注入JS的次数，因为网页中的JS可以同时打开多个url导致无法控制检查的准确性；
     *
     * @deprecated Android 4.2.2及以上版本的 addJavascriptInterface 方法已经解决了安全问题，如果不使用“网页能将JS函数传到Java层”功能，不建议使用该类，毕竟系统的JS注入效率才是最高的；
     */
    @SuppressLint("JavascriptInterface")
    @Override
    @Deprecated
    public void addJavascriptInterface(Object interfaceObj, String interfaceName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            super.addJavascriptInterface(interfaceObj, interfaceName);
            Log.i(TAG, "注入");
            return;
        } else {
            Log.i(TAG, "use mJsCallJavas:" + interfaceName);
        }
        LogUtils.i(TAG, "addJavascriptInterface:" + interfaceObj + "   interfaceName:" + interfaceName);
        mJsCallJavas.put(interfaceName, new JsCallJava(interfaceObj, interfaceName));
        injectJavaScripts();
        if (LogUtils.isDebug()) {
            Log.d(TAG, "injectJavaScript, addJavascriptInterface.interfaceObj = " + interfaceObj + ", interfaceName = " + interfaceName);
        }
        addJavascriptInterfaceSupport(interfaceObj, interfaceName);
    }

    protected void addJavascriptInterfaceSupport(Object interfaceObj, String interfaceName) {

    }

    /**
     * 添加并注入JavaScript脚本（和“addJavascriptInterface”注入对象的注入时机一致，100%能注入成功）；
     * 注意：为了做到能100%注入，需要在注入的js中自行判断对象是否已经存在（如：if (typeof(window.Android) = 'undefined')）；
     *
     * @param javaScript
     */
    private void addInjectJavaScript(String javaScript) {
        if (mInjectJavaScripts == null) {
            mInjectJavaScripts = new HashMap<String, String>();
        }
        mInjectJavaScripts.put(String.valueOf(javaScript.hashCode()), javaScript);

    }

    private void injectJavaScripts() {
        for (Map.Entry<String, JsCallJava> entry : mJsCallJavas.entrySet()) {
            this.loadUrl(buildNotRepeatInjectJs(entry.getKey(), entry.getValue().getmPreloadInterfaceJs()));
        }
    }

    private void injectExtraJavaScript() {
        for (Map.Entry<String, String> entry : mInjectJavaScripts.entrySet()) {
            this.loadUrl(buildNotRepeatInjectJs(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * 构建一个“不会重复注入”的js脚本；
     *
     * @param key
     * @param js
     * @return
     */
    public String buildNotRepeatInjectJs(String key, String js) {
        String obj = String.format("__injectFlag_%1$s__", key);
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:try{(function(){(window.");
        sb.append(obj);
        sb.append("){console.log('");
        sb.append(obj);
        sb.append(" has been injected');return;}window.");
        sb.append(obj);
        sb.append("=true;");
        sb.append(js);
        sb.append("}())}catch(e){console.warn(e)}");
        return sb.toString();
    }


    /**
     * 解决部分手机webView返回时不触发onReceivedTitle的问题（如：三星SM-G9008V 4.4.2）；
     */
    private static class FiexdOnReceivedTitle {
        private WebChromeClient mWebChromeClient;
        private boolean mIsOnReceivedTitle;

        public void setmWebChromeClient(WebChromeClient webChromeClient) {
            mWebChromeClient = webChromeClient;
        }

        public void onPageStarted() {
            mIsOnReceivedTitle = false;
        }

        public void onPageFinished(WebView webView) {
            if (!mIsOnReceivedTitle && mWebChromeClient != null) {
                WebBackForwardList list = null;
                try {
                    list = webView.copyBackForwardList();
                } catch (NullPointerException e) {
                    if (LogUtils.isDebug()) {
                        e.printStackTrace();
                    }
                }
                if (list != null
                        && list.getSize() > 0
                        && list.getCurrentIndex() >= 0
                        && list.getItemAtIndex(list.getCurrentIndex()) != null) {
                    String previousTitle = list.getItemAtIndex(list.getCurrentIndex()).getTitle();
                    mWebChromeClient.onReceivedTitle(webView, previousTitle);
                }
            }
        }

        public void onReceivedTitle() {
            mIsOnReceivedTitle = true;
        }
    }

    /**
     * Android 4.4 KitKat 使用Chrome DevTools 远程调试WebView
     * WebView.setWebContentsDebuggingEnabled(true);
     * http://blog.csdn.net/t12x3456/article/details/14225235
     */
    @TargetApi(19)
    protected void trySetWebDebugEnable() {
        if (LogUtils.isDebug() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class<?> clazz = WebView.class;
                Method method = clazz.getMethod("setWebContentsDebuggingEnabled", boolean.class);
                method.invoke(null, true);
            } catch (Throwable e) {
                if (LogUtils.isDebug()) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected boolean removeSearchBoxJavaBridge() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Method method = this.getClass().getMethod("removeJavascriptInterface", String.class);
                method.invoke(this, "searchBoxJavaBridge_");
                return true;
            }
        } catch (Exception e) {
            if (LogUtils.isDebug()) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
