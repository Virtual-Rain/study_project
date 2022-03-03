package com.study.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.gcssloop.view.utils.CanvasAidUtils;
import com.study.common.R;

/**
 * Author:zx on 2019/11/2910:36
 */
public class MatrixPolyView extends View {
    private static final String TAG = "SetPolyToPoly";
    private static final int NUM_OF_POINT = 8;

    private int testPoint = 0;
    /* 触发半径为180px*/
    private int triggerRadius = 180;
    /*要绘制的图片*/
    private Bitmap mBitmap;
    /* 测试setPolyToPoly用的Matrix*/
    private Matrix mPolyMatrix;

    private float[] src = new float[NUM_OF_POINT];
    private float[] dst = new float[NUM_OF_POINT];

    private Paint pointPaint;

    /*线性渐变画阴影*/
    private LinearGradient mShardowGradient;
    private Paint mShardowPaint;
    private Matrix mShardowMatrix;

    /**
     * 图片的折叠后的总宽度
     */
    private int mTranslateDis;
    /**
     * 折叠后的总宽度与原图宽度的比例
     */
    private float mFactor = 0.8f;
    /**
     * 折叠块的个数
     */
    private int mNumOfFolds = 8;
    private Matrix[] mMatrices = new Matrix[mNumOfFolds];
    /**
     * 绘制黑色透明区域
     */
    private Paint mSolidPaint;

    /***
     * 原图每块的宽度
     */
    private int mFoldWidth;
    /**
     * 折叠时，每块的宽度
     */
    private int mTranslateDisPerFold;

    public MatrixPolyView(Context context) {
        this(context, null);
    }

    public MatrixPolyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixPolyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmapAndMatrix();
    }

    private void initBitmapAndMatrix() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_home_banner);
        float[] temp = {0, 0,
                mBitmap.getWidth(), 0,
                mBitmap.getWidth(), mBitmap.getHeight(),
                0, mBitmap.getHeight()
        };
        src = temp.clone();
        dst = temp.clone();

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(50);
        pointPaint.setColor(0xffd19165);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

        mPolyMatrix = new Matrix();
        mPolyMatrix.setPolyToPoly(src, 0, dst, 0, 4);


        /*折叠*/
        mTranslateDis = (int) (mBitmap.getWidth() * mFactor);
        mFoldWidth = mBitmap.getWidth() / mNumOfFolds;
        mTranslateDisPerFold = mTranslateDis / mNumOfFolds;

        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i] = new Matrix();
        }
        mSolidPaint = new Paint();
        int alpha = (int) (225 * mFactor * 0.8f);
        mSolidPaint.setColor(Color.argb((int) (alpha * 0.8f), 0, 0, 0));
        /*阴影*/
        mShardowPaint = new Paint();
        mShardowPaint.setStyle(Paint.Style.FILL);
        mShardowGradient = new LinearGradient(0, 0, 0.5f, 0,
                Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShardowPaint.setShader(mShardowGradient);
        mShardowMatrix = new Matrix();
        mShardowMatrix.setScale(mFoldWidth, 1);
        mShardowGradient.setLocalMatrix(mShardowMatrix);
        mShardowPaint.setAlpha(alpha);
        /*纵轴减小的高度，用勾股定理计算*/
        int depth = (int) Math.sqrt(mFoldWidth * mFoldWidth - mTranslateDisPerFold * mTranslateDisPerFold) / 2;
        /*转换点*/
        for (int i = 0; i < mNumOfFolds; i++) {
            src[0] = i * mFoldWidth;
            src[1] = 0;
            src[2] = src[0] + mFoldWidth;
            src[3] = 0;
            src[4] = src[2];
            src[5] = mBitmap.getHeight();
            src[6] = src[0];
            src[7] = src[5];
            boolean isEven = i % 2 == 0;
            dst[0] = i * mTranslateDisPerFold;
            dst[1] = isEven ? 0 : depth;
            dst[2] = dst[0] + mTranslateDisPerFold;
            dst[3] = isEven ? depth : 0;
            dst[4] = dst[2];
            dst[5] = isEven ? mBitmap.getHeight() - depth : mBitmap.getHeight();
            dst[6] = dst[0];
            dst[7] = isEven ? mBitmap.getHeight() : mBitmap.getHeight() - depth;

            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float tempX = event.getX();
                float tempY = event.getY();

                for (int i = 0; i < testPoint * 2; i += 2) {
                    if (Math.abs(tempX - dst[i]) <= triggerRadius && Math.abs(tempY - dst[i + 1]) <= triggerRadius) {
                        dst[i] = tempX - 100;
                        dst[i + 1] = tempY - 100;
                        break;
                    }
                }
                resetPolyMatrix(testPoint);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        drawDragPoint(canvas);
        /*折叠 绘制mNumOfFolds次*/
        for (int i = 0; i < mNumOfFolds; i++) {
            canvas.save();
            /*将matrix应用到canvas*/
            canvas.concat(mMatrices[i]);
            /*控制显示的大小*/
            canvas.clipRect(mFoldWidth * i, 0, mFoldWidth * i + mFoldWidth, mBitmap.getHeight());
            /*绘制图片*/
            canvas.drawBitmap(mBitmap, 0, 0, null);
            /*移动绘制阴影*/
            canvas.translate(mFoldWidth * i, 0);
            if (i % 2 == 0) {
                /*绘制黑色遮盖*/
                canvas.drawRect(0, 0, mFoldWidth, mBitmap.getHeight(), mSolidPaint);
            } else {
                /*绘制阴影*/
                canvas.drawRect(0, 0, mFoldWidth, mBitmap.getHeight(), mShardowPaint);
            }
            canvas.restore();
        }
    }

    /**
     * 触控点拖动变形
     *
     * @param canvas
     */
    private void drawDragPoint(Canvas canvas) {
        canvas.translate(100, 100);
        /*绘制坐标系*/
        CanvasAidUtils.setLineColor(Color.WHITE);
        CanvasAidUtils.set2DAxisLength(900, 0, 1200, 0);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);

        /*根据Matrix绘制一个变换后的图片*/
        canvas.drawBitmap(mBitmap, mPolyMatrix, null);
        float[] dst = new float[8];
        mPolyMatrix.mapPoints(dst, src);
        /*绘制触控点*/
        for (int i = 0; i < testPoint * 2; i += 2) {
            canvas.drawPoint(dst[i], dst[i + 1], pointPaint);
        }
        /*绘制阴影*/
        canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mShardowPaint);

    }


    public void resetPolyMatrix(int pointCount) {
        mPolyMatrix.reset();
        mPolyMatrix.setPolyToPoly(src, 0, dst, 0, pointCount);
    }

    public void setPoly(int testPoint) {
        this.testPoint = testPoint > 4 || testPoint < 0 ? 4 : testPoint;
        dst = src.clone();
        this.mNumOfFolds = testPoint > 8 || testPoint < 1 ? 8 : testPoint << 1;
        initBitmapAndMatrix();
        resetPolyMatrix(this.testPoint);
        invalidate();
    }
}
