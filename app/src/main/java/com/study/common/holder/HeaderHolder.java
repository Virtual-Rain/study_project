package com.study.common.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;

/**
 * Author:zx on 2019/9/2714:04
 */
public class HeaderHolder extends RecyclerView.ViewHolder {
    public CheckBox titleView;
    public TextView openView;

    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        titleView = (CheckBox) itemView.findViewById(R.id.cb_title);
//        openView = (TextView) itemView.findViewById(R.id.tv_open);
    }
}
