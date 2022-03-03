package com.study.common.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.study.common.R;
import com.study.commonlibrary.base.view.BaseFragment;

/**
 * Author:zx on 2019/9/2609:36
 */
public abstract class BaseBeanFragment<A extends BaseQuickAdapter> extends BaseFragment {

    public A adapter;
    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public View footView;

    public int pageIndex = 1;

    private boolean isLoadMore = false;
    private Handler handler = new Handler();

    protected abstract A getAdapter();

    protected abstract void setAdapter();

    protected abstract void addMoreData();

    protected abstract void addData();

    protected abstract DividerItemDecoration getItemDecoration();

    protected abstract int getFootViewRes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_refresh_recyclerview, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(getAdapter());
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //分割线
        if (null != getItemDecoration()) {
            recyclerView.addItemDecoration(getItemDecoration());
        }
        if (null != getAdapter()) {
            adapter = getAdapter();
            adapter.bindToRecyclerView(recyclerView);
            adapter.setEmptyView(R.layout.recycler_empty_view);
            adapter.setEnableLoadMore(true);
            adapter.openLoadAnimation();
            adapter.disableLoadMoreIfNotFullPage();
            if (getFootViewRes() > 0) {
                footView = inflater.inflate(getFootViewRes(), container, false);
                adapter.setFooterView(footView);
            }
            initData();
        }
        return view;
    }


    @Override
    public void initData() {
        //自动触发下拉刷新
        recyclerView.postDelayed(() -> {
            //这个方法是让一进入页面的时候实现网络请求，有个缓冲的效果
            swipeRefreshLayout.setRefreshing(true);
            //模拟一下网络请求
            pageIndex = 1;//page为页数
            loadData(pageIndex);
        }, 100);
    }

    @Override
    public void initListener() {
        //手动刷新 触发SwipeRefreshLayout的下拉刷新方法
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isLoadMore = false;
            handler.postDelayed(() -> {
                //模拟一下网络请求
                pageIndex = 1;
                loadData(pageIndex);
            }, 200);
        });
        adapter.setOnLoadMoreListener(() -> {
            isLoadMore = true;
            handler.postDelayed(() -> {
                //模拟网络请求
                pageIndex++;
                loadData(pageIndex);
            }, 200);
            //这个是适配器自带的上拉加载功能
        }, recyclerView);
    }

    public void loadData(int pageIndex) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isLoadMore) {
            addMoreData();
            adapter.notifyDataSetChanged();
            //结束这次加载，不写的话上拉加载就会一直在那转圈
            adapter.loadMoreComplete();
        } else {
            initListener();
            setAdapter();
            addData();
            adapter.notifyDataSetChanged();
            adapter.loadMoreComplete();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    @Override
    protected void onReloadUrl() {

    }
}
