package com.example.agentweb_imitate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agentweb_imitate.R;
import com.example.agentweb_imitate.base.Provider;
import com.example.agentweb_imitate.impl.AbsAgentWebUIController;
import com.example.agentweb_imitate.widget.LogUtils;


/**
 * Author:zx on 2019/9/611:29
 */
public class WebParentLayout extends FrameLayout implements Provider<AbsAgentWebUIController> {
    public static final String TAG = WebParentLayout.class.getSimpleName();

    private AbsAgentWebUIController mAbsAgentWebUIController = null;

    @LayoutRes
    private int mErrorLayoutRes;
    @IdRes
    private int mClickId = -1;
    private View mErrorView;
    private WebView mWebView;
    private FrameLayout mErrorLayout = null;

    public WebParentLayout(@NonNull Context context) {
        this(context, null);
        LogUtils.i(TAG, "WebParentLayout");
    }

    public WebParentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WebParentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!(context instanceof AppCompatActivity)) {
            throw new IllegalArgumentException("WebParentLayout context must be activity or activity sub class.");
        }
        this.mErrorLayoutRes = R.layout.agentweb_error_page;
    }

    public void bindController(AbsAgentWebUIController absAgentWebUIController) {
        this.mAbsAgentWebUIController = absAgentWebUIController;
        this.mAbsAgentWebUIController.bindWebParent(this, (AppCompatActivity) getContext());
    }

    @Override
    public AbsAgentWebUIController provider() {
        return this.mAbsAgentWebUIController;
    }

    public void bindWebView(WebView webView) {
        if (this.mWebView == null) {
            mWebView = webView;
        }
    }

    public WebView getWebView() {
        return this.mWebView;
    }
}
