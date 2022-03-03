package com.study.commonlibrary.widget.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import com.study.commonlibrary.R;
import com.study.commonlibrary.widget.LoadingDialog;

/**
 * Author:zx on 2019/9/2717:03
 */
public class Loading  extends LoadingDialog {
    TextView idTvLoadingmsg;

    public Loading(Context context) {
        super(context);
        idTvLoadingmsg = getView().findViewById(R.id.id_tv_loadingmsg);
    }


    @Override
    protected TextView getMsgView() {
        return idTvLoadingmsg;
    }

    @Override
    protected int getWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.loading;
    }

    @Override
    protected int getStyle() {
        return R.style.progress_dialog;
    }
}

