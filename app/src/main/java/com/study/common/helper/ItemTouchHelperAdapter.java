package com.study.common.helper;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:zx on 2019/10/2514:49
 */
public interface ItemTouchHelperAdapter {
    /**
     * 数据交换
     */
    void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target);

    /**
     * 数据删除
     */
    void onItemDismiss(RecyclerView.ViewHolder source);

    /**
     * 数据选择
     */
    void onItemSelect(RecyclerView.ViewHolder source);

    /**
     * 数据还原
     */
    void onItemClear(RecyclerView.ViewHolder source);
}
