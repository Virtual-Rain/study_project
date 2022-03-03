package com.example.agentweb_imitate.base;

/**
 * Author:zx on 2019/8/3020:31
 */
public interface BaseIndicatorSpec {
    void show();

    void hide();

    void reset();

    void setProgress(int newProgress);
}
