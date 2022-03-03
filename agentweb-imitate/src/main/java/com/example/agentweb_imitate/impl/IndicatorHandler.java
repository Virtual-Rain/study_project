package com.example.agentweb_imitate.impl;

import android.webkit.WebView;

import com.example.agentweb_imitate.base.BaseIndicatorSpec;
import com.example.agentweb_imitate.base.IndicatorController;

/**
 * Author:zx on 2019/9/1417:38
 */
public class IndicatorHandler implements IndicatorController {

    private BaseIndicatorSpec mBaseIndicatorSpec;

    @Override
    public void progress(WebView v, int newProgress) {
        if (newProgress == 0) {
            reset();
        } else if (newProgress > 0 && newProgress <= 10) {
            showIndicator();
        } else if (newProgress > 10 && newProgress < 95) {
            setProgress(newProgress);
        } else {
            setProgress(newProgress);
            finish();
        }
    }

    public void reset() {
        if (mBaseIndicatorSpec != null) {
            mBaseIndicatorSpec.reset();
        }
    }

    @Override
    public BaseIndicatorSpec offerIndicator() {
        return this.mBaseIndicatorSpec;
    }

    @Override
    public void showIndicator() {
        if(mBaseIndicatorSpec!=null){
            mBaseIndicatorSpec.show();
        }
    }

    @Override
    public void setProgress(int newProgress) {
        if (mBaseIndicatorSpec != null) {
            mBaseIndicatorSpec.setProgress(newProgress);
        }
    }

    @Override
    public void finish() {
        if(mBaseIndicatorSpec!=null){
            mBaseIndicatorSpec.hide();
        }
    }

    public static IndicatorHandler getInstance() {
        return new IndicatorHandler();
    }

    public IndicatorHandler inJectIndicator(BaseIndicatorSpec baseIndicatorSpec) {
        this.mBaseIndicatorSpec = baseIndicatorSpec;
        return this;
    }
}
