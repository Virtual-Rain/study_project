package com.study.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.study.common.R;
import com.study.common.entity.CheckBean;
import com.study.common.entity.NoticeConfigInfo;
import com.study.common.entity.PlayData;
import com.study.common.entity.TicketData;
import com.study.commonlibrary.base.view.BaseCustomizeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Author:zx on 2019/10/1820:42
 */
public class MissingLotteryType extends BaseCustomizeView {

    TextView mSettingLottery;
    LabelSelectView mSelectTicket;
    LabelSelectView mSelectPlay;

    private NoticeConfigInfo noticeConfigInfo;

    private AppCompatActivity mActivty;

    public MissingLotteryType(Context context) {
        super(context);
    }

    public MissingLotteryType(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MissingLotteryType(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void initView() {
        View.inflate(getContext(), R.layout.view_missing_lottery_type, this);
        ButterKnife.bind(this);
        mSelectTicket = findViewById(R.id.lsv_select_ticket);
        mSelectPlay = findViewById(R.id.lsv_select_play);
        mSettingLottery = findViewById(R.id.tv_setting_lottery);
    }

    public void initData(NoticeConfigInfo noticeConfigInfo) {
        this.noticeConfigInfo = noticeConfigInfo;
        mSelectTicket.initData(getTicketList(noticeConfigInfo.ticketList));
        mSelectPlay.initData(getPlayList(noticeConfigInfo.playList));
        mSettingLottery.setText(noticeConfigInfo.seriesName);

    }

    public List<CheckBean> getTicketList(List<TicketData> ticketData) {
        List<CheckBean> list = new ArrayList<>();
        for (TicketData data : ticketData
        ) {
            list.add(new CheckBean(data.getTicketName(), data.isChecked()));
        }
        return list;
    }

    public List<CheckBean> getPlayList(List<PlayData> playData) {
        List<CheckBean> list = new ArrayList<>();
        for (PlayData data : playData
        ) {
            list.add(new CheckBean(data.getPlayName(), data.isChecked()));
        }
        return list;
    }

    public void lotterySet() {
        if (null != noticeConfigInfo) {
            if (null != mSelectTicket) {
                for (int i = 0; i < noticeConfigInfo.ticketList.size(); i++) {
                    noticeConfigInfo.ticketList.get(i).setChecked(mSelectTicket.getData().get(i).isCheck());
                }
            }
            if (null != mSelectPlay) {
                for (int i = 0; i < noticeConfigInfo.playList.size(); i++) {
                    noticeConfigInfo.playList.get(i).setChecked(mSelectPlay.getData().get(i).isCheck());
                }
            }

        }
    }

    public NoticeConfigInfo getNoticeConfigInfo() {
        lotterySet();
        return this.noticeConfigInfo;
    }
}
