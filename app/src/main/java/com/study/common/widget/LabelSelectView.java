package com.study.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;
import com.study.common.adapter.LabelSelectAdapter;
import com.study.common.entity.CheckBean;
import com.study.commonlibrary.base.view.BaseCustomizeView;
import com.study.commonlibrary.widget.SpaceItemDecoration;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Author:zx on 2019/10/1916:51
 */
public class LabelSelectView extends BaseCustomizeView {

    TextView mSelectAll;
    TextView mInverseSelect;
    TextView mSelectClear;
    TextView mSelectedNum;
    RecyclerView mRecyclerView;

    private LabelSelectAdapter adapter;
    private List<CheckBean> data;

    public LabelSelectView(Context context) {
        super(context);
    }

    public LabelSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        View.inflate(getContext(), R.layout.view_lottery_select, this);
        ButterKnife.bind(this);
        mRecyclerView = findViewById(R.id.recycler_view_label_select);
        mSelectAll = findViewById(R.id.tv_common_select_all);
        mInverseSelect = findViewById(R.id.tv_common_inverse_select);
        mSelectClear = findViewById(R.id.tv_common_select_clear);
        mSelectedNum = findViewById(R.id.tv_selected_num);
    }

    public void initData(List<CheckBean> data) {
        adapter = new LabelSelectAdapter(R.layout.item_label_select, data);
//        adapter = new ZBaseAdapter<CheckBean>(R.layout.item_label_select, data) {
//            @Override
//            protected void onBind(BindViewHolder holder, int position, CheckBean item) {
//                holder.setText(R.id.cb_label_select, item.getName());
//                holder.setChecked(R.id.cb_label_select, item.isCheck());
//                holder.addOnClickListener(R.id.cb_label_select);
//            }
//        };
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        this.data = data;
        setSelectNum();
        initListener();
    }

    public void initListener() {
        mSelectAll.setOnClickListener(view -> {
            selectAll(true);
        });
        mInverseSelect.setOnClickListener(view -> {
            inverseSelect();
        });
        mSelectClear.setOnClickListener(view -> {
            selectAll(false);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (null != data) {
                CheckBox checkBox = (CheckBox) view;
                data.get(position).setCheck(checkBox.isChecked());
                setSelectNum();
            }
        });
    }

    public void selectAll(boolean isCheck) {
        if (null != data && data.size() > 0) {
            for (CheckBean bean : data
            ) {
                bean.setCheck(isCheck);
            }
            adapter.notifyDataSetChanged();
            setSelectNum();
        }
    }

    public void inverseSelect() {
        if (null != data && data.size() > 0) {
            for (CheckBean bean : data
            ) {
                bean.setCheck(!bean.isCheck());
            }
            adapter.notifyDataSetChanged();
            setSelectNum();
        }
    }


    public void setSelectNum() {
        if (null != data && null != mSelectedNum) {
            int num = 0;
            for (CheckBean bean : data
            ) {
                if (bean.isCheck()) {
                    num++;
                }
            }
            mSelectedNum.setText(num + "/" + data.size());
        }
    }

    public List<CheckBean> getData() {
        return this.data;
    }

}
