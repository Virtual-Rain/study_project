package com.study.commonlibrary.base.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.study.commonlibrary.R;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.uitls.ScreenUtils;

/**
 * Author:zx on 2019/9/1819:30
 * DialogFragment的基类
 * 1.系统默认onCreateDialog方法返回一个Dialog对象,对其不做处理
 * 2.主要操作onCreateView方法
 * 因为DialogFragment继承自Fragment,所以可以在onCreteView()方法返回xml布局,
 */
public abstract class BaseDialogFragment extends DialogFragment {

    public static final String TAG = "ZDialog";
    private static final float DEFAULT_DIMAMOUNT = 0.2F;


    //布局资源
    protected abstract int getLayoutRes();

    protected abstract void initView(View view);

    //绑定View：ViewHolder;
    protected abstract void bindView(View view);

    //自定义的DialogView
    protected abstract View getDialogView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (getLayoutRes() > 0) {
            view = inflater.inflate(getLayoutRes(), container, false);
        }
        if (null != getDialogView()) {
            view = getDialogView();
        }
        initView(view);
        bindView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //去掉Dialog默认头部
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*弹窗外部是否可以点击*/
        dialog.setCanceledOnTouchOutside(isCancelableOutside());
        if (null != dialog.getWindow() && getDialogAnimationRes() > 0) {
            dialog.getWindow().setWindowAnimations(getDialogAnimationRes());
        }
        if (null != getOnKeyListener()) {
            dialog.setOnKeyListener(getOnKeyListener());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (null != window) {
            //设置窗体为透明色
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置宽高
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            if (getDialogWidth() > 0) {
                layoutParams.width = getDialogWidth();
            } else {
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (getDialogHeight() > 0) {
                layoutParams.height = getDialogHeight();
            } else {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            //透明度
            layoutParams.dimAmount = getDimAmount();
            //位置
            layoutParams.gravity = getGravity();
            if (getDialogX() > 0) {
                layoutParams.x = getDialogX();
            }
            if (getDialogY() > 0) {
                layoutParams.y = getDialogY();
            }
            window.setAttributes(layoutParams);
        }
    }

    //默认弹窗位置为中心
    public int getGravity() {
        return Gravity.CENTER;
    }

    public int getDialogX() {
        return 0;
    }

    public int getDialogY() {
        return 0;
    }

    //默认宽高为包裹内容
    public int getDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    //默认透明度0.2
    public float getDimAmount() {
        return DEFAULT_DIMAMOUNT;
    }

    public String getFragmentTag() {
        return TAG;
    }

    /*子类实现*/
    protected DialogInterface.OnKeyListener getOnKeyListener() {
        return null;
    }

    protected boolean isCancelableOutside() {
        return true;
    }

    //获取弹窗显示动画
    protected int getDialogAnimationRes() {
        return 0;
    }


    //获取设备屏幕宽度
    public static final int getScreenWidth(Context context) {
        return ScreenUtils.getScreenWidth(context);
    }

    //获取设备屏幕高度
    public static final int getScreenHeight(Context context) {
        return ScreenUtils.getScreenHeight(context);
    }
}
