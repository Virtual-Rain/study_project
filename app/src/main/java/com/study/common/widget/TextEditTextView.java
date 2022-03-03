package com.study.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * 监听键盘隐藏的EditText
 * Author:zx on 2019/11/1610:57
 */
public class TextEditTextView extends EditText {
    private TextEditTextView mEdit;
    private OnKeyBoardListener onKeyBoardListener;

    public TextEditTextView(Context context) {
        super(context);
        init();
    }

    public TextEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mEdit = this;
        mEdit.setClickable(true);
        mEdit.setFocusable(true);
        mEdit.setFocusableInTouchMode(false);
        initListener();
    }

    private void initListener() {
        mEdit.setOnClickListener(view -> {
            mEdit.setFocusable(true);
            mEdit.setFocusableInTouchMode(true);
            mEdit.requestFocus();
            mEdit.requestFocusFromTouch();
            InputMethodManager inputManager =
                    (InputMethodManager) mEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(mEdit, 0);
            if (null != onKeyBoardListener) {
                onKeyBoardListener.onKeyBoardShow();
            }
        });
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || event.getAction() == 1) {
            if (null != onKeyBoardListener) {
                onKeyBoardListener.onKeyBoardHide();
            }
            mEdit.setFocusable(false);
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setOnKeyBoardListener(OnKeyBoardListener listener) {
        onKeyBoardListener = listener;
    }

    public interface OnKeyBoardListener {
        void onKeyBoardShow();

        void onKeyBoardHide();
    }
}
