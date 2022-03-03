package com.example.agentweb_imitate.impl;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agentweb_imitate.view.WebParentLayout;

/**
 * 该类统一控制了与用户交互的界面
 * Author:zx on 2019/9/520:54
 */
public abstract class AbsAgentWebUIController {

    public final String TAG = this.getClass().getSimpleName();

    public static boolean HAS_DESIGN_LIB = false;
    private AppCompatActivity mActivity;
    private WebParentLayout mWebParentLayout;
    private volatile boolean mIsBindWebParent;
    protected AbsAgentWebUIController mAbsAgentWebUIControllerDelegate;

    static {
        try {
            Class.forName("android.support.design.widget.Snackbar");
            Class.forName("android.support.design.widget.BottomSheetDialog");
            HAS_DESIGN_LIB = true;
        } catch (Throwable ignore) {
            HAS_DESIGN_LIB = false;
        }
    }

    protected AbsAgentWebUIController create() {
        return HAS_DESIGN_LIB ? new DefaultDesignUIController() : new DefaultUIController();
    }

    private AbsAgentWebUIController getDelegate() {
        AbsAgentWebUIController mAbsAgentWebUIController = this.mAbsAgentWebUIControllerDelegate;
        if (mAbsAgentWebUIController == null) {
            this.mAbsAgentWebUIControllerDelegate = mAbsAgentWebUIController = create();
        }
        return mAbsAgentWebUIController;
    }

    public final synchronized void bindWebParent(WebParentLayout webParentLayout, AppCompatActivity activity) {
        if (!mIsBindWebParent) {
            mIsBindWebParent = true;
            this.mWebParentLayout = webParentLayout;
            this.mActivity = activity;
            bindSupportWebParent(webParentLayout, activity);
        }
    }

    protected void toDismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void toShowDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    protected abstract void bindSupportWebParent(WebParentLayout webParentLayout, Activity activity);

    /*WebChromeClient#onJsAlert*/
    public abstract void onJsAlert(WebView view, String url, String message);

    /*咨询用户是否前往其他页面*/
    public abstract void onOpenPagePrompt(WebView view, String url, Handler.Callback callback);

    /*WebChromeClient#onJsConfirm*/
    public abstract void onJsConfirm(WebView view, String url, String message, JsResult jsResult);

    public abstract void onSelectItemsPrompt(WebView view, String url, String[] ways, Handler.Callback callback);

    /**
     * 强制下载弹窗
     *
     * @param url      当前下载地址。
     * @param callback 用户操作回调回调
     */
    public abstract void onForceDownloadAlert(String url, Handler.Callback callback);

    /**
     * WebChromeClient#onJsPrompt
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param jsPromptResult
     */
    public abstract void onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult jsPromptResult);

    /**
     * 显示错误页
     *
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    public abstract void onMainFrameError(WebView view, int errorCode, String description, String failingUrl);

    /**
     * 隐藏错误页
     */
    public abstract void onShowMainFrame();

    /**
     * 正在加载...
     *
     * @param msg
     */
    public abstract void onLoading(String msg);

    /**
     * 取消正在加载...
     */
    public abstract void onCancelLoading();

    /**
     * @param message 消息
     * @param intent  说明message的来源，意图
     */
    public abstract void onShowMessage(String message, String intent);

    /**
     * 当权限被拒回调该方法
     *
     * @param permissions
     * @param permissionType
     * @param action
     */
    public abstract void onPermissionsDeny(String[] permissions, String permissionType, String action);


}
