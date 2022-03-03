package com.example.agentweb_imitate.widget;

import android.content.Context;
import android.os.Handler;

/**
 * Author:zx on 2019/9/1417:13
 */
public class AgentWebUtils {
    private static final String TAG = AgentWebUtils.class.getSimpleName();
    private static Handler mHandler = null;

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
