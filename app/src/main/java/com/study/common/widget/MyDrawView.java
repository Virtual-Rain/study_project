package com.study.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Author:zx on 2019/10/2409:22
 */
public class MyDrawView extends View {
    private Context mContext;
    private Paint mPaint;

    public MyDrawView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setShadowLayer(10,15,15,Color.GREEN);

        canvas.drawRGB(225,225,225);
        canvas.drawCircle(100,100,150,mPaint);

    }
}
