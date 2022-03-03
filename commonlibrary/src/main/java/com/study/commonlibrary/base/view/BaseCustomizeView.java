package com.study.commonlibrary.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.study.commonlibrary.ZDialog;

import butterknife.ButterKnife;

/**
 * Author:zx on 2019/10/1820:08
 */
public abstract class BaseCustomizeView extends LinearLayout {
    public ZDialog mZDialog;

    public BaseCustomizeView(Context context) {
        this(context, null);
    }

    public BaseCustomizeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    protected abstract void initView();

    public void setZDialog(ZDialog dialog) {
        this.mZDialog = dialog;
    }

    public void dimissDialogFragment() {
        if (null != mZDialog) {
            mZDialog.dismiss();
        }
    }
}
