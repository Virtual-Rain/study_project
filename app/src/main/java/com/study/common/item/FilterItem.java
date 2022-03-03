package com.study.common.item;

import com.pacific.adapter.SimpleRecyclerItem;
import com.pacific.adapter.ViewHolder;
import com.study.common.R;
import com.study.common.databinding.ItemFilterBinding;

/**
 * Author:zx on 2019/9/2717:52
 */
public class FilterItem extends SimpleRecyclerItem {
    public String name;
    public boolean isSpanSize;

    public FilterItem(String name,boolean isSpanSize){
        this.name = name;
        this.isSpanSize = isSpanSize;
    }

    @Override
    public int getLayout() {
        return R.layout.item_filter;
    }

    @Override
    public void bind(ViewHolder holder) {
        ItemFilterBinding binding=holder.binding();

        holder.attachOnClickListener(R.id.cb_name);
    }
}
