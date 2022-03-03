package com.study.common.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.study.commonlibrary.uitls.ScreenUtils;

/**
 * Author:zx on 2019/11/218:00
 */
public class PaintTestView extends View {

    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;
    private PathEffect mPathEffect;
    private int paintType = -1;
    private Rect mRect;
    private RectF mRectf;
    float pointX = 200;
    float mCurrentX = 0;
    float mCurrentY = 0;
    float y;
    float corner = 200;
    float length;
    int mItemWaveLength = 400;
    float[] points = {20, 20, 120, 20,
            70, 20, 70, 120,
            20, 120, 120, 120,
            150, 20, 250, 20,
            150, 20, 150, 120,
            250, 20, 250, 120,
            150, 120, 250, 120};

    public PaintTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFF7FC2D8);
        mPaint.setStrokeWidth(20);
        mRectf = new RectF(100, 200, 500, 500);
        mPathEffect = new CornerPathEffect(200);
        y = 800;
        mPath = new Path();
//        dashPathDynamic();
    }

    public PaintTestView(Context context, Paint paint) {
        super(context);
        mPaint = paint;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mCurrentX = event.getX();
                mCurrentY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (mCurrentX + event.getX()) / 2;
                float endY = (mCurrentY + event.getY()) / 2;
                mPath.quadTo(mCurrentX, mCurrentY, endX, endY);
                mCurrentX = event.getX();
                mCurrentY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        switch (paintType) {
            case 0:
                canvas.translate((1080 - 600) / 2, (1920 / 2 - 600) / 2);
                mPaint.setPathEffect(null);
                canvas.drawRoundRect(mRectf, corner, corner, mPaint);
                dashPathEffect(canvas);
                break;
            case 1:
                wave(canvas);
                break;
            case 2:
                mPath.reset();
//                pathDashSpecial(canvas);
                break;
        }
        canvas.drawLines(points, mPaint);
        drawPath(canvas);
//        pathDirection(canvas);
//        pathEffect(canvas);
    }


    /*路径圆润*/
    public void pathEffect(Canvas canvas) {
        /*绘制原始路径*/
        canvas.save();
        mPaint.setPathEffect(null);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        /*绘制圆润路径*/
        canvas.save();
        canvas.translate(0, canvas.getHeight() / 2);
        mPaint.setPathEffect(mPathEffect);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    /*动态路径虚线 start*/
    public void dashPathEffect(Canvas canvas) {
        mPath.lineTo(0, 1720);

        canvas.save();
        canvas.translate(980, 100);
        mPaint.setPathEffect(new DashPathEffect(new float[]{200, 100}, 0));
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(400, 100);
        mPaint.setPathEffect(new DashPathEffect(new float[]{200, 100}, 100));
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    public void dashPathDynamic() {
        mPath.moveTo(100, 100);
        mPath.lineTo(100, 500);
        mPath.lineTo(200, 500);
        mPath.lineTo(200, 300);
        mPath.lineTo(350, 300);
        mPath.quadTo(360, 100, 400, 310);

        PathMeasure measure = new PathMeasure(mPath, false);
        length = measure.getLength();
    }

    public void setPhase(float phase) {
        mPaint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();
    }

    private PathEffect createPathEffect(float pathLenght, float phase, float offset) {
        return new DashPathEffect(new float[]{pathLenght, pathLenght}, pathLenght - phase * pathLenght);
    }
    /*动态路径虚线 end*/

    /*特殊虚线 start*/
    private void pathDashSpecial(Canvas canvas) {
        /*方型*/
        mPath = new Path();
        mPath.addRect(mRectf, Path.Direction.CW);
        canvas.translate(0, 100);
        mPaint.setPathEffect(new PathDashPathEffect(mPath, mRectf.width() * 1.5f, 0, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawLine(0, 100, 1200, 100, mPaint);

        /*圆形椭圆*/
        mPath = new Path();
        mPath.addOval(mRectf, Path.Direction.CW);
        canvas.translate(0, 200);
        mPaint.setPathEffect(new PathDashPathEffect(mPath, mRectf.width() * 1.5f, 0, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawLine(0, 200, 1200, 200, mPaint);

        /*子弹*/
        mPath = new Path();
        mPath.lineTo(mRectf.centerX(), mRectf.top);
        mPath.addArc(mRectf, -90, 180);
        mPath.lineTo(mRectf.left, mRectf.bottom);
        mPath.lineTo(mRectf.left, mRectf.top);
        canvas.translate(0, 250);
        mPaint.setPathEffect(new PathDashPathEffect(mPath, mRectf.width() * 1.5f, 0, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawLine(0, 250, 1200, 250, mPaint);
        /*星星*/
        mPath = new Path();
        mPath.addOval(mRectf, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(mPath, false);
        float length = measure.getLength();
        float split = length / 5;
        float[] startPos = new float[10];
        float[] pos = new float[2];
        float[] tan = new float[2];
        for (int i = 0; i < 5; i++) {
            measure.getPosTan(split * i, pos, tan);
            startPos[i * 2 + 0] = pos[0];
            startPos[i * 2 + 1] = pos[1];
        }
        Path startPath = new Path();
        startPath.lineTo(startPos[0], startPos[1]);
        startPath.lineTo(startPos[4], startPos[5]);
        startPath.lineTo(startPos[8], startPos[9]);
        startPath.lineTo(startPos[2], startPos[3]);
        startPath.lineTo(startPos[6], startPos[7]);
        startPath.lineTo(startPos[0], startPos[1]);
        Matrix matrix = new Matrix();
        matrix.postRotate(-90, mRectf.centerX(), mRectf.centerY());
        startPath.transform(matrix);

        canvas.translate(0, 300);
        mPaint.setPathEffect(new PathDashPathEffect(startPath, mRectf.width() * 1.5f, 0, PathDashPathEffect.Style.ROTATE));
        canvas.drawLine(0, 300, 1200, 300, mPaint);
    }

    /*文字生成顺序*/
    private void pathDirection(Canvas canvas) {
        Path.Direction direction = paintType == -1 ? Path.Direction.CCW : Path.Direction.CW;
        float hOffset = paintType == -1 ? 0f : 80f;
        float vOffset = paintType == -1 ? 0f : 80f;
        mPath.reset();
        mPath.addCircle(420, 400, 180, direction);
        mPaint.setColor(0xFF7FC2D8);
        canvas.drawPath(mPath, mPaint);
        String str = "风萧萧兮易水寒，壮士一去兮不复还";
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(55);
        mPaint.setTextSkewX((float) -0.25);
        mPaint.setUnderlineText(true);
        canvas.drawTextOnPath(str, mPath, hOffset, vOffset, mPaint);
    }

    /*水波浪*/
    private void wave(Canvas canvas) {
        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int orignY = 300;
        int halfWaveLength = mItemWaveLength / 2;
        mPath.moveTo(-mItemWaveLength + mCurrentX, orignY + mCurrentX / 2);
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLength / 2, -100, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength / 2, 100, halfWaveLength, 0);
        }
        int verticalMiddle = getHeight() - ScreenUtils.getActionBarHeight(getContext()) - ScreenUtils.getStatusHeight(getContext()) - 100;
        mPath.lineTo(getWidth(), verticalMiddle / 2);
        mPath.lineTo(0, verticalMiddle / 2);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 值动画
     */
    private void waveAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            mCurrentX = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator.start();
    }

    /**
     * 心型路径
     */
    private void drawPath(Canvas canvas) {
        mPath.reset();
        mPath.addArc(200, 300, 400, 500, -225, 225);
        mPath.arcTo(400, 300, 600, 500, -180, 225, false);
        mPath.lineTo(400, 642);
        canvas.drawPath(mPath, mPaint);
    }

    public void paint(int type) {
        paintType = type;
        ObjectAnimator animator = ObjectAnimator.ofFloat(PaintTestView.this, "phase", 0.0f, 1.0f);
        animator.setDuration(3000);
        animator.start();
        requestLayout();
        if (paintType == 1) {
            waveAnimator();
        }
    }
}
