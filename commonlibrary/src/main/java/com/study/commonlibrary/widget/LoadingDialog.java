package com.study.commonlibrary.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.study.commonlibrary.uitls.StringUtils;
import com.study.commonlibrary.widget.dialog.DialogViewHolder;

/**
 * Author:zx on 2019/9/2716:55
 */
public abstract class LoadingDialog extends DialogViewHolder {
    public LoadingDialog( Context context)
    {
        super(context);
    }

    public void setMessage(String message)
    {
        if (getMsgView() != null){
            if (!StringUtils.isEmpty(message))
            {
                getMsgView().setVisibility(View.VISIBLE);
                getMsgView().setText(message);
            }
            else getMsgView().setVisibility(View.GONE);
        }

    }

    protected abstract TextView getMsgView();
}
