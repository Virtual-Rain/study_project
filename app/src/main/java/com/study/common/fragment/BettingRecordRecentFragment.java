package com.study.common.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.study.common.MockData;
import com.study.common.R;
import com.study.common.adapter.BettingRecordAdapter;
import com.study.common.entity.BettingRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:zx on 2019/9/2610:47
 */
public class BettingRecordRecentFragment extends BaseBeanFragment<BettingRecordAdapter> {

    private int status;
    private int limitNum = Integer.MAX_VALUE;
    private int pageSize = 5;
    private List<BettingRecord> data = new ArrayList<>();


    public BettingRecordRecentFragment(int status) {
        this.status = status;
    }

    public BettingRecordRecentFragment(int status, int limitNum) {
        this.status = status;
        this.limitNum = limitNum;
    }

    public static BettingRecordRecentFragment newInstance(int status) {
        BettingRecordRecentFragment fragment = new BettingRecordRecentFragment(status);

        return fragment;
    }

    public static BettingRecordRecentFragment newInstance(int status, int limitNum) {
        BettingRecordRecentFragment fragment = new BettingRecordRecentFragment(status, limitNum);
        return fragment;
    }

    @Override
    protected BettingRecordAdapter getAdapter() {
        return new BettingRecordAdapter(R.layout.item_betting_record, data, status);
    }

    @Override
    protected void setAdapter() {
        footView.setVisibility(View.GONE);
        //整个控件点击事件
        adapter.setOnItemClickListener((adapter, view, position) -> {
            //Todo 整个被点击
        });
        //某控件点击事件
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.rl_to_detail:
                    //todo:记录详情
                    Toast.makeText(getActivity(), "记录详情", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rl_cancel_the_order:
                    //todo 撤单
                    Toast.makeText(getActivity(), "撤单", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    protected void addMoreData() {
        List<BettingRecord> list = MockData.getBettingRecord(pageIndex, pageSize, limitNum);
        if (list.size() > 0) {
            data.addAll(list);
            if (data.size() >= limitNum) {
                data.subList(0, limitNum - 1);
                footView.setVisibility(View.VISIBLE);
                TextView textView = footView.findViewById(R.id.tv_recycler_foot);
                textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_shap_gray));
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
                textView.setText(R.string.tv_total_record);
                textView.setOnClickListener(view -> {
                    Toast.makeText(getActivity(), "跳到记录页面", Toast.LENGTH_SHORT).show();
                });
                adapter.loadMoreEnd(true);
            }
        } else {
            adapter.loadMoreEnd(true);
            if (data.size() > 0) {
                footView.setVisibility(View.VISIBLE);
            }
        }
        data.addAll(list);
        footView.setVisibility(View.VISIBLE);
        adapter.loadMoreEnd(true);
    }

    @Override
    protected void addData() {
        data.clear();
        List<BettingRecord> list = MockData.getBettingRecord(pageIndex, pageSize, limitNum);
        if (list.size() > 0) {
            data.addAll(list);
        }
    }

    @Override
    protected DividerItemDecoration getItemDecoration() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_decoration_white_10));
        return dividerItemDecoration;
    }

    @Override
    protected int getFootViewRes() {
        return R.layout.recycler_foot_view;
    }


}
