package com.example.agentweb_imitate.handler;

import android.view.KeyEvent;

/**
 * Author:zx on 2019/9/520:37
 */
public interface IEventHandler {
    boolean onKeyDown(int keyCode, KeyEvent event);

    boolean back();
}
