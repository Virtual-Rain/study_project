package com.study.common.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pacific.adapter.AdapterUtil;
import com.pacific.adapter.RecyclerAdapter;
import com.study.common.R;
import com.study.common.entity.BettingRecord;
import com.study.common.item.FilterItem;
import com.study.common.item.RecordItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author:zx on 2019/9/2716:34
 */
public class BettingRecordRecentFragment1 extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerAdapter adapter;
    private List<RecordItem> list = new ArrayList<RecordItem>();

    private int status;
    private int limitNum = Integer.MAX_VALUE;
    private int pageSize = 5;
    private List<BettingRecord> data = new ArrayList<>();

    public BettingRecordRecentFragment1(int status) {
        this.status = status;
    }

    public BettingRecordRecentFragment1(int status, int limitNum) {
        this.status = status;
        this.limitNum = limitNum;
    }

    public static BettingRecordRecentFragment1 newInstance(int status) {
        BettingRecordRecentFragment1 fragment = new BettingRecordRecentFragment1(status);

        return fragment;
    }

    public static BettingRecordRecentFragment1 newInstance(int status, int limitNum) {
        BettingRecordRecentFragment1 fragment = new BettingRecordRecentFragment1(status, limitNum);
        return fragment;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                FilterItem filterItem = adapter.get(position);
                if (filterItem.isSpanSize) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        if (null == list || list.size() == 0) {
            list.add(new RecordItem());
            list.add(new RecordItem());

        }
        adapter.addAll(AdapterUtil.toRecyclerItems(list));
    }


}
