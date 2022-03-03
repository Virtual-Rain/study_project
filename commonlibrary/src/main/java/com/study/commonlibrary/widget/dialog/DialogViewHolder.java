package com.study.commonlibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.study.commonlibrary.R;

/**
 * Author:zx on 2019/9/2716:57
 */
public abstract class DialogViewHolder {

    private Dialog dialog;
    private Context context;
    private DialogInterface.OnDismissListener listener;
    private View view;//自定义dialog的View

    public DialogViewHolder(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        dialog = new Dialog(context, getStyle());
        dialog.setContentView(view);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener != null)
                    listener.onDismiss(dialog);
            }
        });
        if (dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.y = getY();
            params.width = getWidth();
            params.height = getHeight();
            params.gravity = getGravity();
            window.setAttributes(params);
            if (getWindowAnimations() != 0) {
                window.setWindowAnimations(getWindowAnimations());
            }
        }
    }

    protected int getY() {
        return 0;
    }

    /**
     * @return dialog height
     */
    protected int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    /**
     * @return dialog width
     */
    protected abstract int getWidth();

    /**
     * @return dialog layouts id
     */
    protected abstract int getLayoutId();

    /**
     * @return dialog default style
     */
    protected int getStyle() {
        return R.style.default_dialog;
    }

    public View getView() {
        return view;
    }

    /**
     * dialog show
     */
    public void show() {
        if (!dialog.isShowing())
            dialog.show();
    }

    /**
     * dismiss dialog
     */
    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * cancel dialog
     */
    public void cancel() {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
    }

    public Context getContext() {
        return context;
    }

    public boolean isShow() {
        return dialog.isShowing();
    }

    /**
     * 关闭监听
     *
     * @param listener 监听器
     */
    public void setListener(DialogInterface.OnDismissListener listener) {
        this.listener = listener;
    }


    public Window getWindow() {
        return dialog.getWindow();
    }

    /**
     * 默认显示在中间
     *
     * @return int
     */
    protected int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * 默认不加入出现的动画
     *
     * @return int
     */
    protected int getWindowAnimations() {
        return R.style.dialog_animation;
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setCanceledOnTouchOutside(boolean cancelable) {
        dialog.setCanceledOnTouchOutside(cancelable);
    }

    public void setCancelable(boolean b) {
        dialog.setCancelable(b);
    }
}

