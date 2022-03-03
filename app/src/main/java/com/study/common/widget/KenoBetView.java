package com.study.common.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.study.common.R;
import com.study.common.entity.BettingRecord;

import java.text.DecimalFormat;

/**
 * Author:zx on 2019/10/2520:48
 */
public class KenoBetView extends LinearLayout {

    private TextView tv_betting_issue;
    private TextView tv_play_name;
    private TextView tv_odds_of_winning;
    private TextView tv_betting_detail;
    private TextView tv_bet_amount_str;
    private TextView tv_bet_amount;
    private TextView tv_balance_str;
    private TextView tv_balance;
    private TextView tv_can_winning_str;
    private TextView tv_can_winning;
    private EditText et_betting_amount;
    private RecyclerView rv_fast_betting_chips;
    private Button btn_left;
    private Button btn_right;


    private BettingRecord mData;
    private int mBetStatus;
    private String mInput;
    public static String[] fastBettingChips = {"1", "2", "5", "10", "50", "100", "500"};

    private float mMin = 3;
    private float mMax = 5000;

    public KenoBetView(Context context) {
        super(context);
        initView();
    }

    public KenoBetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KenoBetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public KenoBetView(Context context, BettingRecord data, int status) {
        super(context);
        this.mData = data;
        this.mBetStatus = status;
        initView();
    }

    public void initView() {
        View.inflate(getContext(), R.layout.view_keno_betting_input_mode, this);
        tv_betting_issue = findViewById(R.id.tv_betting_issue);
        tv_play_name = findViewById(R.id.tv_play_name);
        tv_odds_of_winning = findViewById(R.id.tv_odds_of_winning);
        tv_betting_detail = findViewById(R.id.tv_betting_detail);
        tv_bet_amount_str = findViewById(R.id.tv_bet_amount_str);
        tv_bet_amount = findViewById(R.id.tv_bet_amount);
        tv_balance_str = findViewById(R.id.tv_balance_str);
        tv_balance = findViewById(R.id.tv_balance);
        tv_can_winning_str = findViewById(R.id.tv_can_winning_str);
        tv_can_winning = findViewById(R.id.tv_can_winning);
        et_betting_amount = findViewById(R.id.et_betting_amount);
        rv_fast_betting_chips = findViewById(R.id.rv_fast_betting_chips);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        btn_right.setEnabled(false);
        initData();
        initListener();

    }

    private void initData() {

    }

    private void initListener() {
        et_betting_amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = et_betting_amount.getText().toString();
                if ("00".equals(input)) {
                    et_betting_amount.setText("0");
                } else if (".".equals(input)) {
                    et_betting_amount.setText("0.");
                } else {
                    if (Double.valueOf(input) > mMax) {
                        DecimalFormat decimalFormat = new DecimalFormat("###################.####");
                        et_betting_amount.setText(decimalFormat.format(mMax));
                    }
                    if (input.indexOf(".") > 0) {
                        if (input.length() - input.indexOf(".") > 5) {
                            et_betting_amount.setText(input.substring(0, input.indexOf(".") + 5));
                        }
                    }
                }
                et_betting_amount.setSelection(et_betting_amount.getText().length());
                if (et_betting_amount.getText().length() > 0) {
                    btn_right.setEnabled(true);
                }
            }
        });
        btn_right.setOnClickListener(view -> {
            mInput = et_betting_amount.getText().toString();
            tv_betting_detail.setVisibility(VISIBLE);
            tv_bet_amount_str.setVisibility(VISIBLE);
            tv_bet_amount.setVisibility(VISIBLE);
            tv_bet_amount.setText(mInput+"元");
            tv_balance_str.setText("余        额");
            tv_can_winning_str.setText("可        中");
            tv_can_winning_str.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
            tv_can_winning.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
            et_betting_amount.setVisibility(GONE);

        });
    }
}
