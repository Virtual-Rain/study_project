package com.example.agentweb_imitate.impl;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agentweb_imitate.AgentWebConfig;
import com.example.agentweb_imitate.R;
import com.example.agentweb_imitate.base.BaseIndicatorSpec;
import com.example.agentweb_imitate.base.IWebLayout;
import com.example.agentweb_imitate.base.WebCreator;
import com.example.agentweb_imitate.view.AgentWebView;
import com.example.agentweb_imitate.view.BaseIndicatorView;
import com.example.agentweb_imitate.view.WebParentLayout;
import com.example.agentweb_imitate.widget.LogUtils;


/**
 * Author:zx on 2019/9/609:59
 */
public class DefaultWebCreator implements WebCreator {
    private AppCompatActivity mActivity;
    private ViewGroup mViewGroup;
    private boolean mIsNeedDefaultProgress;
    private int mIndex;
    private BaseIndicatorView mProgressView;
    private ViewGroup.LayoutParams mLayoutParams = null;
    private int mColor = -1;
    /**
     * 单位dp
     */
    private int mHeight;
    private boolean mIsCreated = false;
    private IWebLayout mIWebLayout;
    private BaseIndicatorSpec mBaseIndicatorSpec;
    private WebView mWebView = null;
    private FrameLayout mFrameLayout = null;
    private View mTargetProgress;
    private static final String TAG = DefaultWebCreator.class.getSimpleName();

    /*默认进度条*/
    public DefaultWebCreator(@Nullable AppCompatActivity activity,
                             @Nullable ViewGroup viewGroup,
                             ViewGroup.LayoutParams lp,
                             int index,
                             int color,
                             int mHeight,
                             WebView webView,
                             IWebLayout webLayout) {
        this.mActivity = activity;
        this.mViewGroup = viewGroup;
        this.mIndex = index;
        this.mColor = color;
        this.mIsNeedDefaultProgress = true;
        this.mLayoutParams = lp;
        this.mHeight = mHeight;
        this.mWebView = webView;
        this.mIWebLayout = webLayout;
    }

    /*默认关闭进度条*/
    public DefaultWebCreator(@Nullable AppCompatActivity activity, @Nullable ViewGroup viewGroup, ViewGroup.LayoutParams lp,
                             int index, @Nullable WebView webView, IWebLayout webLayout) {
        this.mActivity = activity;
        this.mViewGroup = viewGroup;
        this.mIsNeedDefaultProgress = false;
        this.mIndex = index;
        this.mLayoutParams = lp;
        this.mWebView = webView;
        this.mIWebLayout = webLayout;
    }

    /*使用自定义Indicator*/
    public DefaultWebCreator(@Nullable AppCompatActivity activity, @Nullable ViewGroup viewGroup, ViewGroup.LayoutParams lp,
                             BaseIndicatorView progressView, int index, @Nullable WebView webView, IWebLayout webLayout) {
        this.mActivity = activity;
        this.mViewGroup = viewGroup;
        this.mIsNeedDefaultProgress = false;
        this.mIndex = index;
        this.mLayoutParams = lp;
        this.mWebView = webView;
        this.mIWebLayout = webLayout;
        this.mProgressView = progressView;
    }

    public void setWebView(WebView webView) {
        mWebView = webView;
    }

    public FrameLayout getFrameLayout() {
        return mFrameLayout;
    }

    public View getTargetProgress() {
        return mTargetProgress;
    }

    public void setmTargetProgress(View targetProgress) {
        this.mTargetProgress = targetProgress;
    }


    @Override
    public BaseIndicatorSpec offer() {
        return mBaseIndicatorSpec;
    }

    @Override
    public WebView getWebView() {
        return mWebView;
    }

    @Override
    public FrameLayout getWebParentLayout() {
        return mFrameLayout;
    }

    @Override
    public WebCreator create() {
        return null;
    }

    /**
     * 创造WebView的布局
     */
    private ViewGroup createLayout() {
        AppCompatActivity activity = this.mActivity;
        WebParentLayout mFrameLayout = new WebParentLayout(mActivity);
        mFrameLayout.setId(R.id.web_parent_layout_id);
        mFrameLayout.setBackgroundColor(Color.WHITE);
        View target = mIWebLayout == null ? (this.mWebView = (WebView) createWebView()) : webLayout();
        FrameLayout.LayoutParams mLayoutParams=new FrameLayout.LayoutParams(-1,-1);
        mFrameLayout.addView(target,mLayoutParams);
        mFrameLayout.bindWebView(this.mWebView);
        if(this.mWebView instanceof AgentWebView){
            AgentWebConfig.WEBVIEW_TYPE= AgentWebConfig.WEBVIEW_AGENTWEB_SAFE_TYPE;
        }
        /*添加错误页面 使用ViewStub减少资源占用*/
        ViewStub viewStub=new ViewStub(mActivity);
        viewStub.setId(R.id.mainframe_error_viewsub_id);
        mFrameLayout.addView(viewStub,new FrameLayout.LayoutParams(-1,-1));
        /*是否使用默认的进度条*/
        if(mIsNeedDefaultProgress){
            FrameLayout.LayoutParams lp=null;

        }
        return mFrameLayout;
    }

    /*获取Layout*/
    private View webLayout() {
        WebView mWebView = null;
        if ((mWebView = mIWebLayout.getWebView()) == null) {
            mWebView = getWebView();
            mIWebLayout.getLayout().addView(mWebView, -1, -1);
            LogUtils.i(TAG, "add webview");
        } else {
            AgentWebConfig.WEBVIEW_TYPE = AgentWebConfig.WEBVIEW_CUSTOM_TYPE;
        }
        this.mWebView = mWebView;
        return mIWebLayout.getLayout();
    }

    /*创建webview*/
    private WebView createWebView() {
        WebView mWebView = null;
        if (this.mWebView != null) {
            mWebView = this.mWebView;
            AgentWebConfig.WEBVIEW_TYPE = AgentWebConfig.WEBVIEW_CUSTOM_TYPE;
        } else if (AgentWebConfig.IS_KITKAT_OR_BELOW_KITKAT) {
            mWebView = new AgentWebView(mActivity);
            AgentWebConfig.WEBVIEW_TYPE = AgentWebConfig.WEBVIEW_AGENTWEB_SAFE_TYPE;
        } else {
            mWebView = new WebView(mActivity);
            AgentWebConfig.WEBVIEW_TYPE = AgentWebConfig.WEBVIEW_DEFAULT_TYPE;
        }
        return mWebView;
    }
}
