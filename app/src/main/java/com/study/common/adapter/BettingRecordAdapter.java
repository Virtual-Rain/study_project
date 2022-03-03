package com.study.common.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.study.common.R;
import com.study.common.entity.BettingRecord;

import java.util.List;

/**
 * Author:zx on 2019/9/2509:57
 */
public class BettingRecordAdapter extends BaseQuickAdapter<BettingRecord, BaseViewHolder> {

    /**
     * 状态， 0:待开奖,1:未中奖,2:已中奖,3:系统撤销,4:个人撤销,5:追中撤单,6:未结注单,7:已结注单
     */
    private int status = 0;

    public BettingRecordAdapter(@Nullable List<BettingRecord> data) {
        super(data);
    }

    public BettingRecordAdapter(int layoutResid, @Nullable List<BettingRecord> data) {
        super(layoutResid, data);
    }

    public BettingRecordAdapter(int layoutResid, @Nullable List<BettingRecord> data, int status) {
        super(layoutResid, data);
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, BettingRecord item) {
        helper.setText(R.id.tv_lottery_tickets, item.getTicketName());
        helper.setText(R.id.tv_lottery_issue_number, item.getIssue());
        helper.setText(R.id.tv_lottery_play, item.getPlayId());
        helper.setText(R.id.tv_betting_amount, item.getBetAmount());
        helper.setVisible(R.id.ll_win_the_prize, status == 2);
        if (status == 2) {
            helper.setText(R.id.tv_winning_amount, item.getPrize());
        }
        //todo 撤单
        if (status == 6 && item.isCanCancel()) {
            helper.setVisible(R.id.rl_cancel_the_order, true);
        }
        //添加点击事件
        helper.addOnClickListener(R.id.rl_to_detail);
        helper.addOnClickListener(R.id.rl_cancel_the_order);
    }
}
