package com.example.agentweb_imitate;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;

import com.example.agentweb_imitate.base.EventInterceptor;
import com.example.agentweb_imitate.base.IAgentWebSettings;
import com.example.agentweb_imitate.base.IUrlLoader;
import com.example.agentweb_imitate.base.IVideo;
import com.example.agentweb_imitate.base.IWebLayout;
import com.example.agentweb_imitate.base.IndicatorController;
import com.example.agentweb_imitate.base.JsInterfaceHolder;
import com.example.agentweb_imitate.base.PermissionInterceptor;
import com.example.agentweb_imitate.base.WebCreator;
import com.example.agentweb_imitate.base.WebLifeCycle;
import com.example.agentweb_imitate.client.DefaultWebClient;
import com.example.agentweb_imitate.client.WebChromeClient;
import com.example.agentweb_imitate.client.WebViewClient;
import com.example.agentweb_imitate.handler.IEventHandler;
import com.example.agentweb_imitate.http.HttpHeaders;
import com.example.agentweb_imitate.impl.AbsAgentWebUIController;
import com.example.agentweb_imitate.impl.DefaultWebCreator;
import com.example.agentweb_imitate.impl.UrlLoaderImpl;
import com.example.agentweb_imitate.impl.VideoImpl;
import com.example.agentweb_imitate.middleware.MiddlewareWebChromeBase;
import com.example.agentweb_imitate.middleware.MiddlewareWebClientBase;
import com.example.agentweb_imitate.view.BaseIndicatorView;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Author:zx on 2019/8/3020:26
 */
public class AgentWebZ {
    public static final String TAG = AgentWebZ.class.getSimpleName();

    private int TAG_TARGET = 0;
    private AppCompatActivity mActivity;
    /**
     * 承载 WebParentLayout 的 ViewGroup
     */
    private ViewGroup mViewGroup;
    /**
     * 负责创建布局 WebView ，WebParentLayout  Indicator等。
     */
    private WebCreator mWebCreator;

    /**
     * 管理 WebSettings
     */
    private IAgentWebSettings mAgentWebSettings;
    /**
     * AgentWebZ
     */
    private AgentWebZ mAgentWeb = null;
    /**
     * IndicatorController 控制Indicator
     */
    private IndicatorController mIndicatorController;
    /**
     * is show indicator
     */
    private boolean mEnableIndicator;
    /**
     * WebChromeClient
     */
    private WebChromeClient mWebChromeClient;
    /**
     * WebViewClient
     */
    private WebViewClient mWebViewClient;
    /**
     * MiddlewareWebClientBase WebViewClient 中间件
     */
    private MiddlewareWebClientBase mMiddlewareWebClientBaseHeader;
    /**
     * MiddlewareWebChromeBase WebChromeClient 中间件
     */
    private MiddlewareWebChromeBase mMiddlewareWebChromeBaseHeader;
    /**
     * URL Loader ， 提供了 WebView#loadUrl(url) reload() stopLoading（） postUrl()等方法
     */
    private IUrlLoader mIUrlLoader = null;
    /**
     * WebView 生命周期 ， 跟随生命周期释放CPU
     */
    private WebLifeCycle mWebLifeCycle;
    /**
     * Video 视屏播放管理类
     */
    private IVideo mIVideo = null;
    /**
     * WebViewClient 辅助控制开关
     */
    private boolean mWebClientHelper = true;
    /**
     * PermissionInterceptor 权限拦截
     */
    private PermissionInterceptor mPermissionInterceptor;
    /**
     * 是否拦截未知的Url， @link{DefaultWebClient}
     */
    private boolean mIsInterceptUnknowUrl = false;
    private int mUrlHandleWays = -1;
    /**
     * 事件拦截
     */
    private EventInterceptor mEventInterceptor;
    /**
     * IEventHandler 处理WebView相关返回事件
     */
    private IEventHandler mIEventHandler;
    /**
     * 注入对象管理类
     */
    private JsInterfaceHolder mJsInterfaceHolder = null;
    /**
     * WebView 注入对象
     */
    private ArrayMap<String, Object> mJavaObjects = new ArrayMap<>();



    public static final int ACTIVITY_TAG = 0;
    public static final int FRAGMENT_TAG = 1;

    private AgentWebZ(AgentBuilder agentBuilder) {
        TAG_TARGET = agentBuilder.mTag;
        mAgentWeb = this;
        this.mActivity = agentBuilder.mActivity;
        this.mViewGroup = agentBuilder.mViewGroup;
        this.mIEventHandler = agentBuilder.mIEventHandler;
        this.mEnableIndicator = agentBuilder.mEnableIndicator;
        mWebCreator = agentBuilder.mWebCreator;
        this.mIUrlLoader=new UrlLoaderImpl(mWebCreator.create().getWebView());
    }

    /*AgentBuilder*/
    public enum SecurityType {
        DEFAULT_CHECK, STRICT_CHECK;
    }

    /*准备*/
    private AgentWebZ ready() {
        return this;
    }

    /*Builder 模式*/
    public static final class AgentBuilder {
        private AppCompatActivity mActivity;
        private Fragment mFragment;
        private ViewGroup mViewGroup;
        private boolean mIsNeedDefaultProgress;
        private int mIndex = -1;
        private ViewGroup.LayoutParams mLayoutParams = null;
        private WebViewClient mWebViewClient;
        private WebChromeClient mWebChromeClient;
        /*进度条 默认显示*/
        private BaseIndicatorView mBaseIndicatorView;
        private int mIndicatorColor = -1;
        private IndicatorController mIndicatiorController = null;
        private boolean mEnableIndicator = true;
        private IAgentWebSettings mAgentWebSettings;
        private WebCreator mWebCreator;
        private HttpHeaders mHttpHeaders = null;
        private IEventHandler mIEventHandler;
        private int mHeight = -1;
        private SecurityType mSecurityType = SecurityType.DEFAULT_CHECK;
        private boolean mWebClientHelper = true;
        /*WebView*/
        private WebView mWebView;
        private IWebLayout mIWebLayout = null;
        /*javascript*/
        private ArrayMap<String, Object> mJavaObject;

        private PermissionInterceptor mPermissionInterceptor = null;
        private AbsAgentWebUIController mAbsAgentWebUIController;
        private DefaultWebClient.OpenOtherPageWays mOpenOtherPage = null;
        private boolean mIsInterceptUnKnownUrl = false;
        private MiddlewareWebClientBase mMiddlewareWebClientBaseheader;
        private MiddlewareWebClientBase mMiddlewareWebClientBaseTail;
        private MiddlewareWebChromeBase mMiddlewareWebWareHeader = null;
        private MiddlewareWebChromeBase mMiddlewareWebWareTail = null;
        private View mErrorView;
        private int mErrorLayout;
        private int mReloadId;
        private int mTag = -1;


        private AgentBuilder(@NonNull AppCompatActivity activity, @NonNull Fragment fragment) {
            mActivity = activity;
            mFragment = fragment;
            mTag = AgentWebZ.FRAGMENT_TAG;
        }

        private AgentBuilder(@NonNull AppCompatActivity activity) {
            mActivity = activity;
            mTag = AgentWebZ.ACTIVITY_TAG;
        }

        public IndicatorBuilder setAgentWebParent(@Nullable ViewGroup v, @Nullable ViewGroup.LayoutParams lp) {
            this.mViewGroup = v;
            this.mLayoutParams = lp;
            return new IndicatorBuilder(this);
        }

        public IndicatorBuilder setAgentWebParent(@Nullable ViewGroup v, int index, @Nullable ViewGroup.LayoutParams lp) {
            this.mViewGroup = v;
            this.mLayoutParams = lp;
            this.mIndex = index;
            return new IndicatorBuilder(this);
        }

        private PreAgentWeb buildAgentWeb() {
            if (mTag == AgentWebZ.FRAGMENT_TAG && this.mViewGroup == null) {
                throw new NullPointerException("ViewGroup is null ,Please check you parameters");
            }
            return new PreAgentWeb(HookManager.hookAgentWeb(new AgentWebZ(this), this));
        }

        private void addJavaObject(String key, Object o) {
            if (mJavaObject == null) {
                mJavaObject = new ArrayMap<>();
            }
            mJavaObject.put(key, o);
        }

        private void addHeader(String baseUrl, String k, String v) {
            if (mHttpHeaders == null) {
                mHttpHeaders = HttpHeaders.create();
            }
            mHttpHeaders.additionalHttpHeader(baseUrl, k, v);
        }

        private void addHeader(String baseUrl, Map<String, String> headers) {
            if (mHttpHeaders == null) {
                mHttpHeaders = HttpHeaders.create();
            }
            mHttpHeaders.additionalHttpHeader(baseUrl, headers);
        }
    }

    public static AgentBuilder with(@NonNull AppCompatActivity activity) {
        if (activity == null) {
            throw new NullPointerException("activity can not be null");
        }
        return new AgentBuilder(activity);
    }

    public static AgentBuilder with(@NonNull Fragment fragment) {
        AppCompatActivity activity = null;
        if ((activity = (AppCompatActivity) fragment.getActivity()) == null) {
            throw new NullPointerException("activity can not be null .");
        }
        return new AgentBuilder(activity, fragment);
    }

    /*Url JavaScript*/
    public IUrlLoader getUrlLoder() {
        return this.mIUrlLoader;
    }


    /*进度条*/
    public static class IndicatorBuilder {
        private AgentBuilder mAgentBuilder = null;

        public IndicatorBuilder(AgentBuilder agentBuilder) {
            this.mAgentBuilder = agentBuilder;
        }

        public CommonBuilder userDefaultIndicator(){
            this.mAgentBuilder.mEnableIndicator=true;
            return new CommonBuilder(mAgentBuilder);
        }

    }

    /*公共Builder*/
    public static class CommonBuilder {
        private AgentBuilder mAgentBuilder;

        public CommonBuilder(AgentBuilder agentBuilder) {
            this.mAgentBuilder = agentBuilder;
        }

        public CommonBuilder setEventHandler(@Nullable IEventHandler iEventHandler) {
            return this;
        }

        public PreAgentWeb createAgentWeb(){
            return this.mAgentBuilder.buildAgentWeb();
        }
    }

    public IndicatorController getIndicatorController() {
        return this.mIndicatorController;
    }

    /*预览*/
    public static class PreAgentWeb {
        private AgentWebZ mAgentWeb;
        private boolean isReady = false;

        PreAgentWeb(AgentWebZ agentWeb) {
            this.mAgentWeb = agentWeb;
        }

        public PreAgentWeb ready() {
            if (!isReady) {
                mAgentWeb.ready();
                isReady = true;
            }
            return this;
        }

        public AgentWebZ get() {
            ready();
            return mAgentWeb;
        }

        public AgentWebZ go(@Nullable String url) {
            if (!isReady) {
                ready();
            }
            return mAgentWeb.go(url);
        }

    }

    public static final class PermissionInterceptorWrapper implements PermissionInterceptor {

        private WeakReference<PermissionInterceptor> mWeakReference;

        private PermissionInterceptorWrapper(PermissionInterceptor permissionInterceptor) {
            this.mWeakReference = new WeakReference<PermissionInterceptor>(permissionInterceptor);
        }

        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            return false;
        }
    }

    private DefaultWebCreator configWebCreator(BaseIndicatorView progressView, int index, ViewGroup.LayoutParams lp,
                                               int indicatorColor, int height_dp, WebView webView, IWebLayout webLayout) {
        if (progressView != null && mEnableIndicator) {
            return new DefaultWebCreator(mActivity, mViewGroup, lp, progressView, index, webView, webLayout);
        } else {
            return mEnableIndicator ?
                    new DefaultWebCreator(mActivity, mViewGroup, lp, index, indicatorColor, height_dp, webView, webLayout)
                    : new DefaultWebCreator(mActivity, mViewGroup, lp, index, webView, webLayout);
        }
    }

    /*加载Url*/
    private AgentWebZ go(String url) {
        this.getUrlLoder().loadUrl(url);
        IndicatorController indicatorController = null;
        /*显示进度条*/
        if (!TextUtils.isEmpty(url) && (indicatorController = getIndicatorController()) != null
                && indicatorController.offerIndicator() != null) {
            getIndicatorController().offerIndicator().show();
        }
        return this;
    }
    /*拦截器*/
    private EventInterceptor getInterceptor(){
        if(this.mEventInterceptor!=null){
            return this.mEventInterceptor;
        }
        if(mIVideo instanceof VideoImpl){
            return this.mEventInterceptor=(EventInterceptor)this.mIVideo;
        }
        return null;
    }

    /*初始化 java*/
    private void init(){

    }
    private void doCompat(){
//        mJavaObjects.put("agentWeb",mAgent)
    }
}

