package com.study.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;
import com.study.common.entity.NoticeConfigInfo;
import com.study.common.entity.PlayData;
import com.study.common.entity.TicketData;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.base.adapter.ZBaseAdapter;
import com.study.commonlibrary.base.view.BaseCustomizeView;
import com.study.commonlibrary.base.viewHolder.BindViewHolder;
import com.study.commonlibrary.widget.SpaceItemDecoration;

import java.util.List;

/**
 * Author:zx on 2019/10/2117:21
 */
public class MissingLotterySeries extends BaseCustomizeView {

    private RecyclerView mRecyclerView;
    private Button mDefaultSetting;
    private Button mCancel;
    private Button mSave;
    private View lineBottom;

    private List<NoticeConfigInfo> lotteryData;
    private ZBaseAdapter seriesAdapter;
    private FragmentManager fragmentManager;
    private ZDialog mDialog;

    public MissingLotterySeries(Context context, FragmentManager fragmentManager) {
        super(context);
        this.fragmentManager = fragmentManager;
    }

    public MissingLotterySeries(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MissingLotterySeries(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        View.inflate(getContext(), R.layout.dialog_lottery_ticket, this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mDefaultSetting = findViewById(R.id.btn_use_default_setting);
        mCancel = findViewById(R.id.btn_cancel);
        mSave = findViewById(R.id.btn_save);
        lineBottom = findViewById(R.id.line_bottom);

    }

    public void initData(List<NoticeConfigInfo> lotteryData) {
        this.lotteryData = lotteryData;
        seriesAdapter = new ZBaseAdapter<NoticeConfigInfo>(R.layout.item_lottery_series, lotteryData) {
            @Override
            protected void onBind(BindViewHolder holder, int position, NoticeConfigInfo noticeConfigInfo) {
                holder.setText(R.id.tv_lottery_name, noticeConfigInfo.seriesName);
                if (getLotteryNum(lotteryData.get(position).ticketList) < 1) {
                    holder.setText(R.id.tv_lottery_num, "未选择彩种");
                } else {
                    holder.setText(R.id.tv_lottery_num, "已选择" + getLotteryNum(lotteryData.get(position).ticketList) + "个彩种");
                }
                TextEditTextView editText = holder.itemView.findViewById(R.id.et_lottery);
                editText.setOnKeyBoardListener(new TextEditTextView.OnKeyBoardListener() {
                    @Override
                    public void onKeyBoardShow() {
                        lineBottom.setVisibility(GONE);
                        mDefaultSetting.setVisibility(GONE);
                        mCancel.setVisibility(GONE);
                        mSave.setVisibility(GONE);
                    }

                    @Override
                    public void onKeyBoardHide() {
                        lineBottom.setVisibility(VISIBLE);
                        mDefaultSetting.setVisibility(VISIBLE);
                        mCancel.setVisibility(VISIBLE);
                        mSave.setVisibility(VISIBLE);
                    }
                });
            }
        };
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(seriesAdapter);
        initListener();
    }

    public void initListener() {
        mDefaultSetting.setOnClickListener(view -> {
            lotteryDefaultSetting();
            seriesAdapter.notifyDataSetChanged();
        });
        seriesAdapter.setOnAdapterItemClickListener((ZBaseAdapter.OnAdapterItemClickListener<NoticeConfigInfo>) (holder, positon, item, zDialog) -> {
            MissingLotteryType lotteryType = new MissingLotteryType(getContext());
            lotteryType.initData(lotteryData.get(positon));
            if (null != fragmentManager) {
                mDialog = new ZDialog.Builder(fragmentManager)
                        .setDialogView(lotteryType)
                        .addOnClickListener(R.id.iv_close)
                        .setScreenWidthAspect(getContext(), 1f)
                        .setScreenHeightAspect(getContext(), 0.85f)
                        .setGravity(Gravity.BOTTOM)
                        .setOnViewClickListener(((viewHolder, view1, dialog) -> {
                            switch (view1.getId()) {
                                case R.id.iv_close:
                                    lotteryData.set(positon, lotteryType.getNoticeConfigInfo());
                                    seriesAdapter.notifyDataSetChanged();
                                    mDialog.dismiss();
                                    break;
                            }
                        }))
                        .create()
                        .show();
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom <= oldBottom) {
                    lineBottom.setVisibility(VISIBLE);
                    mDefaultSetting.setVisibility(VISIBLE);
                    mCancel.setVisibility(VISIBLE);
                    mSave.setVisibility(VISIBLE);
                }
            }
        });
    }

    public int getLotteryNum(List<TicketData> ticketData) {
        int num = 0;
        for (TicketData data : ticketData
        ) {
            if (data.isChecked()) {
                num++;
            }
        }
        return num;
    }

    public void lotteryDefaultSetting() {
        if (null != lotteryData) {
            for (NoticeConfigInfo info : lotteryData
            ) {
                for (TicketData ticketData : info.ticketList) {
                    ticketData.setChecked(true);
                }
                for (PlayData playData : info.playList) {
                    playData.setChecked(true);
                }
            }
        }
    }

    public List<NoticeConfigInfo> getLotteryData() {
        return lotteryData;
    }
}
