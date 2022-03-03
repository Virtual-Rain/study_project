package com.study.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;
import com.study.common.entity.NoticeConfigInfo;
import com.study.common.entity.TicketData;
import com.study.common.holder.ContentHolder;
import com.study.common.holder.HeaderHolder;
import com.study.commonlibrary.base.adapter.SectionedRecyclerViewAdapter;
import com.study.commonlibrary.uitls.CommonUtils;
import com.study.commonlibrary.uitls.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:zx on 2019/9/2714:08
 */
public class LottreyEntityAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, ContentHolder, RecyclerView.ViewHolder> {
    public ArrayList<NoticeConfigInfo> allTagList;
    private Context mContext;
    private LayoutInflater mInflater;

    private SparseBooleanArray mBooleanMap;

    private boolean isChange = false;
    private StringBuilder currentSelected = new StringBuilder();

    public LottreyEntityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<NoticeConfigInfo> allTagList) {
        this.allTagList = (ArrayList<NoticeConfigInfo>) allTagList;
        isChange = false;
        setCurrentSelected();
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return CommonUtils.isEmpty(allTagList) ? 0 : allTagList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = allTagList.get(section).ticketList.size();
        if (count >= 8 && !mBooleanMap.get(section)) {
            count = 8;
        }

        return CommonUtils.isEmpty(allTagList.get(section).ticketList) ? 0 : count;
    }


    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.item_title, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected ContentHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(mInflater.inflate(R.layout.item_content, parent, false));
    }


    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        checkIsAll(section);
        holder.titleView.setText(allTagList.get(section).seriesName);
        holder.titleView.setChecked(allTagList.get(section).checked);
        holder.titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTagList.get(section).checked = (holder.titleView.isChecked());
                if (holder.titleView.isChecked()) {
                    holder.titleView.setShadowLayer(4, 0, 5, Color.parseColor("#e50011"));
                }
                for (TicketData tagInfo : allTagList.get(section).ticketList) {
                    tagInfo.setChecked(holder.titleView.isChecked());
                }
                changeData();
            }
        });

    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(final ContentHolder holder, final int section, final int position) {
        holder.contentView.setText(allTagList.get(section).ticketList.get(position).getTicketName());
        holder.contentView.setChecked(allTagList.get(section).ticketList.get(position).isChecked());
        holder.contentView.setOnClickListener(view -> {
            allTagList.get(section).ticketList.get(position).setChecked(holder.contentView.isChecked());
            checkIsAll(section);
        });
    }

    private void checkIsAll(final int section) {
        boolean isAll = true;
        for (TicketData bean : allTagList.get(section).ticketList) {
            if (!bean.isChecked()) {
                isAll = false;
                break;
            }
        }
        allTagList.get(section).checked = isAll;
        changeData();
    }

    public void setAllSelect() {
        setAllTagList(true);
        setCurrentSelected();
    }

    public void setClear() {
        setAllTagList(false);
        currentSelected = new StringBuilder();
    }

    public void setAllTagList(boolean select) {
        for (int i = 0; i < allTagList.size(); i++) {
            allTagList.get(i).checked = select;
            for (TicketData bean : allTagList.get(i).ticketList) {
                bean.setChecked(select);
            }
        }
        changeData();
    }

    public String save() {
        setCurrentSelected();
        isChange = false;
        return currentSelected.toString();
    }

    private void setCurrentSelected() {
        if (null != allTagList) {
            for (int i = 0; i < allTagList.size(); i++) {
                for (TicketData bean : allTagList.get(i).ticketList) {
                    if (bean.isChecked()) {
                        currentSelected.append(bean.getTicketId());
                        currentSelected.append(",");
                    }
                }
            }
            if (!StringUtils.isEmpty(currentSelected)) {
                currentSelected.setLength(currentSelected.length() - 1);
            }
        }
    }

    public boolean isChange() {
        return isChange;
    }

    public void changeData() {
        isChange = true;
        updateData();
    }

    public void updateData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // 刷新操作
                notifyDataSetChanged();
            }
        });
    }
}
