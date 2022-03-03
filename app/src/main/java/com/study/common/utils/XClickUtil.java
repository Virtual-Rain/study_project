package com.study.common.utils;

import android.view.View;

/**
 * Author:zx on 2019/10/2917:40
 */
public final class XClickUtil {

    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTiem;
    /**
     * 最近一次点击控件ID
     */
    private static int mLastClickViewId;

    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        int viewId = v.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTiem);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTiem = time;
            mLastClickViewId = viewId;
            return false;
        }
    }
}
