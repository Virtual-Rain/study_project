package com.study.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.study.common.R;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import io.reactivex.annotations.Nullable;

/**
 * Author:zx on 2019/10/2515:56
 */
public class KenoPlayItemView extends RelativeLayout {

    private Context context;

    private TextView tv_play_name;
    private TextView tv_odds_of_winning;
    ConstraintLayout cl_play;

    private boolean isCanBetting;
    private boolean isRadius = true;
    private EasyPopup mCiclePop;
    private int totalNum = 0;
    private TextView totalNumTextView;
    private String betNum;
    private RelativeLayout rlZhuShu;
    private TextView missTv;
    private String ticketPlayId;
    private KenoPlayItemView kenoPlayItemView;

    public KenoPlayItemView(Context context) {
        super(context);
        initView();
    }

    public KenoPlayItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KenoPlayItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        View.inflate(getContext(), R.layout.view_keno_play, this);
        tv_play_name = findViewById(R.id.tv_play_name);
        tv_odds_of_winning = findViewById(R.id.tv_odds_of_winning);
        cl_play = findViewById(R.id.cl_play);

    }

    public void setConstraintLayout() {
        ConstraintSet set = new ConstraintSet();
        set.clone(cl_play);
        set.setHorizontalChainStyle(tv_play_name.getId(), ConstraintSet.CHAIN_SPREAD_INSIDE);
        set.applyTo(cl_play);
    }

    public void setLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(300, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        tv_play_name.setLayoutParams(layoutParams);
        ConstraintSet set = new ConstraintSet();
        set.clone(cl_play);
        set.centerVertically(tv_play_name.getId(), ConstraintSet.PARENT_ID);
        set.connect(tv_play_name.getId(),ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
        set.connect(tv_play_name.getId(),ConstraintSet.END,tv_odds_of_winning.getId(),ConstraintSet.START);
        set.setHorizontalChainStyle(tv_play_name.getId(), ConstraintSet.CHAIN_SPREAD_INSIDE);
        set.applyTo(cl_play);
    }

    public void setCanBetting(boolean isCanBetting) {
        this.isCanBetting = isCanBetting;
        if (null != cl_play) {
            if (isRadius) {
                cl_play.setBackground(ContextCompat.getDrawable(getContext(), isCanBetting ? R.drawable.hall_bg_white_10dp_select : R.drawable.hall_bg_light_gray_10dp_select));
            } else {
                cl_play.setBackground(ContextCompat.getDrawable(getContext(), isCanBetting ? R.drawable.hall_bg_white_select : R.drawable.hall_bg_light_gray_select));
            }

        }
    }

    public TextView getPlayName() {
        return tv_play_name;
    }

    public TextView getOdds() {
        return tv_odds_of_winning;
    }

    public void setPlayName(String playName) {
        if (null != tv_play_name) {
            tv_play_name.setText(playName);
        }
    }

    public void setOdds(String odds) {
        if (null != tv_odds_of_winning) {
            tv_odds_of_winning.setText(odds);
        }
    }

    public void setTicketPlayId(String ticketPlayId) {
        this.ticketPlayId = ticketPlayId;
    }

    public String getTicketPlayId() {
        return ticketPlayId;
    }

    @SuppressLint("ResourceType")
    private void init(Context context) {
        this.context = context;
        this.kenoPlayItemView = this;

        mCiclePop = EasyPopup.create()
                .setContext(getContext())
                .setContentView(R.layout.layout_zhutu_clean)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        TextView arrowView = view.findViewById(R.id.tv_clean);
                        arrowView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mCiclePop.dismiss();
                                onCleanZhuShuListener.cleanZhuShuListener(kenoPlayItemView);
                                setSelected(false);
                            }
                        });
                    }
                })
                .setFocusAndOutsideEnable(true)
//                .setBackgroundDimEnable(true)
//                .setDimValue(0.5f)
//                .setDimColor(Color.RED)
//                .setDimView(mTitleBar)
                .apply();


        rlZhuShu = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        kenoPlayItemView.addView(rlZhuShu, layoutParams);


        /*添加注数视图*/
        totalNumTextView = new TextView(context);
        totalNumTextView.setBackgroundColor(context.getResources().getColor(R.color.black));
        totalNumTextView.setTextColor(context.getResources().getColor(R.color.white));
        totalNumTextView.setGravity(Gravity.CENTER);
        totalNumTextView.setTextSize(5);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        totalNumTextView.setLayoutParams(lp);
        totalNumTextView.setId(1);

        initMiss();
    }

    public void showCleanPopu() {
//        .showAsDropDown(this,0,0,Gravity.TOP);
//        mCiclePop.showAtAnchorView(this, YGravity.ALIGN_TOP, XGravity.ALIGN_LEFT, 0, 0);
        mCiclePop.showAtAnchorView(this, YGravity.ABOVE, XGravity.CENTER);
    }

    private void initMiss() {
        missTv = (TextView) LayoutInflater.from(context).inflate(R.layout.scibo_miss_tv, this, false);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        missTv.setLayoutParams(lp);
        //view.setText(miss);
        rlZhuShu.addView(missTv);
        missTv.setVisibility(INVISIBLE);
    }

    /*添加冷热遗漏视图*/
    public void showMiss(String miss) {
        missTv.setText(miss);
        missTv.setVisibility(VISIBLE);
    }

    public void hideMiss() {
        missTv.setVisibility(INVISIBLE);
    }


    /*计算注数总数*/
    public void setTotalZhuShu(String num) {
        Integer integer = Integer.valueOf(num);
        totalNum = totalNum + integer;
    }

    public void cleanZhushu() {
        totalNum = 0;
    }

    /*用于显示所有注数*/
    public TextView getTotalNumTextView() {
        return totalNumTextView;
    }

    public int getTotalNum() {
        return totalNum;
    }


    public String getBetNum() {
        return betNum;
    }

    public void setBetNum(String betNum) {
        this.betNum = betNum;
    }

    private static OnCleanZhuShuListener onCleanZhuShuListener;

    public RelativeLayout getRlZhuShu() {
        return rlZhuShu;
    }


    public interface OnCleanZhuShuListener {
        void cleanZhuShuListener(KenoPlayItemView kenoPlayItemView);
    }

    public static void setOnCleanZhuShuListener(OnCleanZhuShuListener listener) {
        onCleanZhuShuListener = listener;
    }

//    public TicketSpecialPlayInfo getTicketSpecialPlayInfo() {
//        return ticketSpecialPlayInfo;
//    }
//
//    public void setTicketSpecialPlayInfo(TicketSpecialPlayInfo ticketSpecialPlayInfo) {
//        this.ticketSpecialPlayInfo = ticketSpecialPlayInfo;
//
//    }
}
