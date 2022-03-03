package com.study.common.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.study.common.R;
import com.study.common.adapter.LottreyEntityAdapter;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.base.adapter.SectionedSpanSizeLookup;
import com.study.commonlibrary.base.view.BaseFragment;
import com.study.commonlibrary.uitls.FileUtils;
import com.study.commonlibrary.uitls.ToastUtil;
import com.study.commonlibrary.widget.SpaceItemDecoration;

/**
 * Author:zx on 2019/9/2809:31
 */
public class LotterTypeFragment extends BaseFragment implements View.OnClickListener {

    private Button clear;
    private Button selectAll;
    private Button save;

    private RecyclerView recyclerView;
    private LottreyEntityAdapter adapter;
    private ZDialog dialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        clear = view.findViewById(R.id.btn_clear);
        selectAll = view.findViewById(R.id.btn_select_all);
        save = view.findViewById(R.id.btn_save);
        clear.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        save.setOnClickListener(this);
        adapter = new LottreyEntityAdapter(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(adapter, manager));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        }
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lottery_filter;
    }

    @Override
    protected void onReloadUrl() {

    }

    @Override
    protected void initData() {
        String content = FileUtils.readJsonFile(getActivity(), "lottery");
        Gson gson = new Gson();
//        LongDragoConfig entity = gson.fromJson(content, LongDragoConfig.class);
        adapter.setData(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                adapter.setClear();
                break;
            case R.id.btn_select_all:
                adapter.setAllSelect();
                break;
            case R.id.btn_save:
                if (adapter.isChange()) {
                    ToastUtil.showInfo("");
                }
                ToastUtil.showInfo(adapter.save());
                dismissDialogFragment();
                break;
        }
    }

    public void hideFragment(ZDialog zDialog) {
        if (adapter.isChange()) {
            dialog = new ZDialog.Builder(getChildFragmentManager())
                    .setLayoutRes(R.layout.dialog_click)
                    .setCancelableOutside(false)
                    .setWidth(600)
                    .setHeight(300)
                    .setOnBindViewListener(viewHolder -> {
                        viewHolder.getView(R.id.tv_title).setVisibility(View.GONE);
                        viewHolder.setText(R.id.tv_content, "有修改的内容未保存确定离开吗？");
                        ((Button) viewHolder.getView(R.id.btn_right)).setTextColor(ContextCompat.getColor(getActivity(), R.color.color_0000FE));
                    })//添加进行点击控件的id
                    .addOnClickListener(R.id.btn_left, R.id.btn_right)
                    //设置各个点击控件的事件
                    .setOnViewClickListener((viewHolder, view2, dialog1) -> {
                        switch (view2.getId()) {
                            case R.id.btn_left:
                                dialog.dismiss();
                                break;
                            case R.id.btn_right:
                                zDialog.dismiss();
                                break;
                        }
                    })
                    .create()
                    .show();
        } else {
            zDialog.dismiss();
        }
    }
}
