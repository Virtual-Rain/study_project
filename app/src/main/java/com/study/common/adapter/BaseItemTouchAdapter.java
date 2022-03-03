package com.study.common.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.study.common.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Author:zx on 2019/10/2515:03
 */
public abstract class BaseItemTouchAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> implements ItemTouchHelperAdapter {


    public BaseItemTouchAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int fromPosition = source.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < mData.size() && toPosition < mData.size()) {
            Collections.swap(mData, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder source) {
        int position = source.getAdapterPosition();
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
       viewHolder.itemView.setScaleX(1.01f);
        viewHolder.itemView.setTranslationZ(20);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
       viewHolder.itemView.setScaleX(1.0f);
       viewHolder.itemView.setTranslationZ(0);
    }
}
