package com.study.common.helper;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Author:zx on 2019/11/909:49
 */
public class HelperDraw {
    /**
     * 获得屏幕宽高
     *
     * @param context
     * @param winSize
     */
    public static void loadWinSize(Context context, Point winSize) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (null != wm) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        winSize.x = outMetrics.widthPixels;
        winSize.y = outMetrics.heightPixels;
    }

    /**
     * 绘制网格路径
     *
     * @param step    正方形边长
     * @param winSize 屏幕尺寸
     * @return
     */
    public static Path gridPath(int step, Point winSize) {
        Path path = new Path();
        for (int i = 0; i < winSize.y / step + 1; i++) {
            path.moveTo(0, step * i);
            path.lineTo(winSize.x, step * i);
        }
        for (int i = 0; i < winSize.x / step + 1; i++) {
            path.moveTo(step * i, 0);
            path.lineTo(step * i, winSize.y);
        }
        return path;
    }


    /**
     * 绘制坐标系
     * @param coo
     * @param winSize
     * @return
     */
    public static Path cooPath(Point coo, Point winSize) {
        Path path = new Path();
        //x正半轴
        path.moveTo(coo.x, coo.y);
        path.lineTo(winSize.x, coo.y);
        //x负半轴
        path.moveTo(coo.x, coo.y);
        path.lineTo(coo.x - winSize.x, coo.y);
        //y负半轴
        path.moveTo(coo.x, coo.y);
        path.lineTo(coo.x, coo.y - winSize.y);
        //y正半轴
        path.moveTo(coo.x, coo.y);
        path.lineTo(coo.x, winSize.y);
        return path;
    }

}
