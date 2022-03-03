package com.study.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.study.commonlibrary.uitls.ScreenUtils;
import com.study.commonlibrary.uitls.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author:zx on 2019/11/608:58
 */
public class SampleBubbleView extends View {

    /*气泡最大半径 px*/
    private int mBubbleMaxRadius = 30;
    private int mBubbleMinRadius = 5;
    /*气泡最大数量*/
    private int mBubbleMaxSize = 30;
    /*刷新间隔*/
    private int mBubbleRefreshTime = 20;
    /*气泡速度*/
    private int mBubbleMaxSpeedY = 5;
    /*气泡透明度*/
    private int mBubbleAlpha = 128;

    /*瓶子*/
    private float mBottleWidth;
    private float mBottleHeight;
    private float mBottleRadius;
    private float mBottleBorder;
    private float mBottleCapRadius;
    private float mWaterHeight;

    private RectF mContentRectF;
    private RectF mWaterRectF;

    /*路径*/
    private Path mBottlePath;
    private Path mWaterPath;

    /*画笔*/
    private Paint mBottlePaint;
    private Paint mWaterPaint;
    private Paint mBubblePaint;

    private boolean isAddBubble;


    public SampleBubbleView(Context context) {
        this(context, null);
    }

    public SampleBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWaterRectF = new RectF();

        mBottleWidth = ScreenUtils.dip2px(130);
        mBottleHeight = ScreenUtils.dip2px(260);
        mBottleBorder = ScreenUtils.dip2px(8);
        mBottleRadius = ScreenUtils.dip2px(15);
        mBottleCapRadius = ScreenUtils.dip2px(5);

        mWaterHeight = ScreenUtils.dip2px(240);

        mBottlePath = new Path();
        mWaterPath = new Path();

        mBottlePaint = new Paint();
        mBottlePaint.setAntiAlias(true);
        mBottlePaint.setStyle(Paint.Style.STROKE);
        mBottlePaint.setStrokeCap(Paint.Cap.ROUND);
        mBottlePaint.setColor(Color.GREEN);
        mBottlePaint.setStrokeWidth(mBottleBorder);

        mWaterPaint = new Paint();
        mWaterPaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mContentRectF = new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());

        float bl = mContentRectF.centerX() - mBottleWidth / 2;
        float bt = mContentRectF.centerY() - mBottleHeight / 2;
        float br = mContentRectF.centerX() + mBottleWidth / 2;
        float bb = mContentRectF.centerY() + mBottleHeight / 2;
        mBottlePath.reset();
        mBottlePath.moveTo(bl - mBottleCapRadius, bt - mBottleCapRadius);
        mBottlePath.quadTo(bl, bt - mBottleCapRadius, bl, bt);
        mBottlePath.lineTo(bl, bb - mBottleRadius);
        mBottlePath.quadTo(bl, bb, bl + mBottleRadius, bb);
        mBottlePath.lineTo(br - mBottleRadius, bb);
        mBottlePath.quadTo(br, bb, br, bb - mBottleRadius);
        mBottlePath.lineTo(br, bt);
        mBottlePath.quadTo(br, bt - mBottleCapRadius, br + mBottleCapRadius, bt - mBottleCapRadius);

        mWaterPath.reset();
        mWaterPath.moveTo(bl, bb - mWaterHeight);
        mWaterPath.lineTo(bl, bb - mBottleRadius);
        mWaterPath.quadTo(bl, bb, bl + mBottleRadius, bb);
        mWaterPath.lineTo(br - mBottleRadius, bb);
        mWaterPath.quadTo(br, bb, br, bb - mBottleRadius);
        mWaterPath.lineTo(br, bb - mWaterHeight);
        mWaterPath.close();

        mWaterRectF.set(bl, bb - mWaterHeight, br, bb);
        /*渐变*/
        LinearGradient gradient = new LinearGradient(mWaterRectF.centerX(), mWaterRectF.top, mWaterRectF.centerX(), mWaterRectF.bottom,
                0xFF4286f4, 0xFF373B44, Shader.TileMode.CLAMP);
        mWaterPaint.setShader(gradient);

        initBubble();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mWaterPath, mWaterPaint);
        canvas.drawPath(mBottlePath, mBottlePaint);
        drawBubble(canvas);
    }

    /*- 气泡 --*/

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        startBubbleSync();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopBubbleSync();
    }

    private class Bubble {
        /*气泡半径*/
        int radius;
        /*上升速度*/
        float speedY;
        /*平移速度*/
        float speedX;
        /*气泡坐标*/
        float x;
        float y;
        int color;
    }

    private ArrayList<Bubble> mBubbles = new ArrayList<>();

    private Random random = new Random();
    private Thread mBubbleThread;

    /*初始化气泡*/
    private void initBubble() {
        mBubblePaint = new Paint();
        mBubblePaint.setColor(Color.WHITE);
        mBubblePaint.setAlpha(mBubbleAlpha);
    }

    /*开始气泡线程*/
    public void startBubbleSync() {
        stopBubbleSync();
        isAddBubble = true;
        mBubbleThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(mBubbleRefreshTime);
                        tryCreateBubble();
                        refreshBubbles();
                        postInvalidate();
                    } catch (InterruptedException e) {
                        System.out.println("Bubble线程结束");
                        break;
                    }
                }
            }
        };
        mBubbleThread.start();
    }

    /*停止气泡线程*/
    public void stopBubbleSync() {
        if (null == mBubbleThread) {
            return;
        }
        mBubbleThread.interrupt();
        mBubbleThread = null;
    }

    public void stopAddBubble() {
        isAddBubble = false;
    }

    /*绘制气泡*/
    private void drawBubble(Canvas canvas) {
        //复制，避免ConcurrentModificationException 异常（迭代时被修改）
        List<Bubble> list = new ArrayList<>(mBubbles);
        for (Bubble bubble : list) {
            if (null == bubble) {
                continue;
            }
            mBubblePaint.setColor(bubble.color);
            canvas.drawCircle(bubble.x, bubble.y, bubble.radius, mBubblePaint);
        }
    }

    //尝试创建气泡
    private void tryCreateBubble() {
        if (null == mContentRectF) {
            return;
        }
        if (mBubbles.size() >= mBubbleMaxSize) {
            return;
        }
        if (random.nextFloat() < 0.95) {
            return;
        }
        if (!isAddBubble) {
            return;
        }
        Bubble bubble = new Bubble();
        int radius = random.nextInt(mBubbleMaxRadius - mBubbleMinRadius);
        radius += mBubbleMinRadius;
        float speedY = random.nextFloat() * mBubbleMaxSpeedY;
        while (speedY < 1) {
            speedY = random.nextFloat() * mBubbleMaxSpeedY;
        }
        bubble.radius = radius;
        bubble.speedY = speedY;
        bubble.x = mWaterRectF.centerX();
        bubble.y = mWaterRectF.bottom - radius - mBottleBorder / 2;
        float speedX = random.nextFloat() - 0.5f;
        while (speedX == 0) {
            speedX = random.nextFloat() - 0.5f;
        }
        bubble.speedX = speedX * 2;
        bubble.color=getColor();
        mBubbles.add(bubble);
    }

    /*刷新气泡位置，对于超出区域的气泡进行移除*/
    private void refreshBubbles() {
        List<Bubble> list = new ArrayList<>(mBubbles);
        for (Bubble bubble : list) {
            if (null != bubble) {
                if (bubble.y - bubble.speedY <= mWaterRectF.top + bubble.radius) {
                    mBubbles.remove(bubble);
                } else {
                    int i = mBubbles.indexOf(bubble);
                    if (bubble.x + bubble.speedX <= mWaterRectF.left + bubble.radius + mBottleBorder / 2) {
                        bubble.x = mWaterRectF.left + bubble.radius + mBottleBorder / 2;
                    } else if (bubble.x + bubble.speedX >= mWaterRectF.right - bubble.radius - mBottleBorder / 2) {
                        bubble.x = mWaterRectF.right - bubble.radius - mBottleBorder / 2;
                    } else {
                        bubble.x += bubble.speedX;
                    }
                    bubble.y -= bubble.speedY;
                    if (i >= 0 && i < mBubbles.size()) {
                        mBubbles.set(i, bubble);
                    }
                }
            } else {
                ToastUtil.showError("bubble is null:" + bubble);
            }
        }
    }

    /*产生随机颜色*/
    private int getColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }
}
