package com.study.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;
import com.study.common.adapter.LabelSelectAdapter;
import com.study.common.entity.CheckBean;
import com.study.commonlibrary.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    private List<CheckBean> mData = new ArrayList<>();

    public ContentFragment() {
    }

    public static Fragment newInstance(String title, int position) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initData();
        initToolbar(view);
        initRecyclerView(view);
        return view;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.content_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        recyclerView.setAdapter(new LabelSelectAdapter(R.layout.item_label_select, mData));
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            if (mData.size() > 30) {
                break;
            }
            mData.add(new CheckBean("Test", true));
        }
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }
}
