package com.study.commonlibrary.base.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Author:zx on 2019/9/2711:14
 */
public class SectionedSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    protected SectionedRecyclerViewAdapter<?, ?, ?> adapter = null;
    protected GridLayoutManager layoutManager = null;

    public SectionedSpanSizeLookup(SectionedRecyclerViewAdapter<?, ?, ?> adapter, GridLayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {

        if (adapter.isSectionHeaderPosition(position) || adapter.isSectionFooterPosition(position)) {
            return layoutManager.getSpanCount();
        } else {
            return 1;
        }

    }
}
