package com.example.agentweb_imitate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agentweb_imitate.base.BaseIndicatorSpec;
import com.example.agentweb_imitate.base.LayoutParamsOffer;

/**
 * Author:zx on 2019/8/3021:49
 */
public abstract class BaseIndicatorView extends FrameLayout implements BaseIndicatorSpec, LayoutParamsOffer {
    public BaseIndicatorView(@NonNull Context context) {
        super(context);
    }

    public BaseIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicatorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setProgress(int newProgress) {

    }

    @Override
    public LayoutParams offerLayoutParams() {
        return null;
    }
}
