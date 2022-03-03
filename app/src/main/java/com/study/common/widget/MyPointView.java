package com.study.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.study.common.entity.Point;
import com.study.common.evaluator.PointEvaluator;

/**
 * Author:zx on 2019/10/1221:02
 */
public class MyPointView extends View {
    // 设置需要用到的变量
    public static final float RADIUS = 70f;// 圆的半径 = 70
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔

    private ValueAnimator anim;

    public MyPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置填充样式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽度
        mPaint.setStrokeWidth(30);
        //设置阴影
        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);

            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);

            Point startPoint = new Point(RADIUS, RADIUS);
            Point endPoint = new Point(700, 1000);
            anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
            anim.setDuration(5000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point) animation.getAnimatedValue();
                    invalidate();
                }
            });
            anim.start();
        } else {
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);
        }
    }

}
