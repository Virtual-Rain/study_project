package com.study.common.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author:zx on 2019/10/2615:36
 */
public class FastBettingChipsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FastBettingChipsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
