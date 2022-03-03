package com.study.commonlibrary.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.study.commonlibrary.R;

/**
 * Author:zx on 2019/10/2619:40
 */
public class EditTextWithIcon extends AppCompatEditText implements View.OnTouchListener, TextWatcher, View.OnFocusChangeListener {

    /**
     * 删除符号
     */
    Drawable rightImage = ContextCompat.getDrawable(getContext(), R.mipmap.icon_clear);

    Drawable icon;
    private OnIconClick onIconClick;

    public EditTextWithIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextWithIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithIcon(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this);
        this.setOnTouchListener(this);
        addTextChangedListener(this);
        rightImage.setBounds(0, 0, rightImage.getIntrinsicWidth(), rightImage.getIntrinsicHeight());
        manageClearButton();
    }

    /**
     * 传入显示的图标资源id
     *
     * @param id
     */
    public void setIconResource(int id) {
        icon = getResources().getDrawable(id);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        manageClearButton();
    }

    /**
     * 传入右边图标资源id
     *
     * @param id
     */
    public void setRightImage(int id) {
        rightImage = getResources().getDrawable(id);
        rightImage.setBounds(0, 0, rightImage.getIntrinsicWidth(), rightImage.getIntrinsicHeight());
        manageClearButton();
    }

    public void manageClearButton() {
        if (this.getText().toString().equals(""))
            removeClearButton();
        else
            addClearButton();
    }

    public void removeClearButton() {
        if (icon == null)
            icon = this.getCompoundDrawables()[0];
        this.setCompoundDrawables(this.icon, this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
    }

    void addClearButton() {
        if (icon == null)
            icon = this.getCompoundDrawables()[0];
        this.setCompoundDrawables(this.icon, this.getCompoundDrawables()[1], rightImage,
                this.getCompoundDrawables()[3]);
        setCompoundDrawablePadding(10);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        EditTextWithIcon et = EditTextWithIcon.this;

        if (et.getCompoundDrawables()[2] == null)
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onIconClick != null) {
                    onIconClick.onIconClick(event.getAction());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() > et.getWidth() - et.getPaddingRight() - rightImage.getIntrinsicWidth()) {
                    if (onIconClick == null) {
                        et.setText("");
                        this.removeClearButton();
                    } else {
                        onIconClick.onIconClick(event.getAction());
                    }
                }
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.manageClearButton();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setOnIconClick(OnIconClick onIconClick) {
        this.onIconClick = onIconClick;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            manageClearButton();
        } else removeClearButton();
    }

    public interface OnIconClick {
        void onIconClick(int action);
    }

}

