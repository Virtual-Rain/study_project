package com.study.common.holder;

import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;

/**
 * Author:zx on 2019/9/2714:06
 */
public class ContentHolder extends RecyclerView.ViewHolder {
    public CheckBox contentView;
    public ContentHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        contentView = (CheckBox) itemView.findViewById(R.id.cb_desc);
    }
}
