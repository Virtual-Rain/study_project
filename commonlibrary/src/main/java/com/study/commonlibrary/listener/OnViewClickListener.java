package com.study.commonlibrary.listener;

import android.view.View;

import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.base.viewHolder.BindViewHolder;

/**
 * Author:zx on 2019/9/1819:31
 */
public interface OnViewClickListener {
    void onViewClick(BindViewHolder viewHolder, View view, ZDialog dialog);
}
