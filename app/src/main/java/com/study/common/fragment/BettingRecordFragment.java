package com.study.common.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.study.common.MockData;
import com.study.common.R;
import com.study.common.adapter.BettingRecordAdapter;
import com.study.common.entity.BettingRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:zx on 2019/9/2419:47
 */
public class BettingRecordFragment extends Fragment {
    /**
     * 状态， 0:待开奖,1:未中奖,2:已中奖,3:系统撤销,4:个人撤销,5:追中撤单,6:未结注单,7:已结注单
     */
    private int status;
    private int limitNum = Integer.MAX_VALUE;

    private BettingRecordAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<BettingRecord> data = new ArrayList<>();
    private View footView;

    private int pageIndex = 1;
    private int pageSize = 5;

    private boolean isLoadMore = false;
    private Handler handler = new Handler();

    public BettingRecordFragment(int status){
        this.status=status;
    }
    public BettingRecordFragment(int status, int limitNum){
        this.status=status;
        this.limitNum = limitNum;
    }
    public static BettingRecordFragment newInstance(int status) {
        BettingRecordFragment fragment = new BettingRecordFragment(status);

        return fragment;
    }

    public static BettingRecordFragment newInstance(int status, int limitNum) {
        BettingRecordFragment fragment = new BettingRecordFragment(status,limitNum);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_refresh_recyclerview, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new BettingRecordAdapter(R.layout.item_betting_record, data, status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_decoration_white_10));
        recyclerView.addItemDecoration(dividerItemDecoration);
        footView = inflater.inflate(R.layout.recycler_foot_view, container, false);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setEmptyView(R.layout.recycler_empty_view);
        adapter.setEnableLoadMore(true);
        adapter.openLoadAnimation();
        adapter.disableLoadMoreIfNotFullPage();
        adapter.setFooterView(footView);
        initListener();
        return view;
    }

    public void initAdapter() {
        footView.setVisibility(View.GONE);
        adapter.setOnLoadMoreListener(() -> {
            isLoadMore = true;
            if (data.size() < limitNum) {
                handler.postDelayed(() -> {
                    //模拟网络请求
                    pageIndex++;
                    loadData(pageIndex);
                }, 1000);
            }
            //这个是适配器自带的上拉加载功能
        }, recyclerView);
        //整个控件点击事件
        adapter.setOnItemClickListener((adapter, view, position) -> {
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
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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
            }
        });
    }

    public void initListener() {
        //自动触发下拉刷新
        recyclerView.postDelayed(() -> {
            //这个方法是让一进入页面的时候实现网络请求，有个缓冲的效果
            swipeRefreshLayout.setRefreshing(true);
            //模拟一下网络请求
            pageIndex = 1;//page为页数
            loadData(pageIndex);
        }, 2000);
        //手动刷新 触发SwipeRefreshLayout的下拉刷新方法
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isLoadMore = false;
            handler.postDelayed(() -> {
                //模拟一下网络请求
                pageIndex = 1;
                loadData(pageIndex);
            }, 1000);
        });

    }

    public void loadData(int pageIndex) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isLoadMore) {
            List<BettingRecord> list = MockData.getBettingRecord(pageIndex, pageSize, 10);
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
            adapter.notifyDataSetChanged();
            //结束这次加载，不写的话上拉加载就会一直在那转圈
            adapter.loadMoreComplete();

        } else {
            initAdapter();
            data.clear();
            List<BettingRecord> list = MockData.getBettingRecord(pageIndex, pageSize, 10);
            if (list.size() > 0) {
                data.addAll(list);
            }
            adapter.notifyDataSetChanged();
            adapter.loadMoreComplete();
        }

    }

}
