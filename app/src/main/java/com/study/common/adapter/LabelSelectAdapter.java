package com.study.common.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.study.common.R;
import com.study.common.entity.CheckBean;

import java.util.List;

/**
 * Author:zx on 2019/10/1917:19
 */
public class LabelSelectAdapter extends BaseQuickAdapter<CheckBean, BaseViewHolder> {

    public LabelSelectAdapter(int layoutResId, @Nullable List<CheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CheckBean item) {
        holder.setText(R.id.cb_label_select, item.getName());
        holder.setChecked(R.id.cb_label_select, item.isCheck());
        holder.addOnClickListener(R.id.cb_label_select);
    }
}
