package com.study.common.widget.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:zx on 2019/11/3015:47
 */
public class FoldLayout extends ViewGroup {
    private static final int NUM_OF_POINT = 8;
    /**
     * 图片的折叠后的总宽度
     */
    private float mTranslateDis;
    /**
     * 折叠后的总宽度与原View宽度的比例
     */
    protected float mFactor = 1f;
    /**
     * 折叠块的个数
     */
    private int mNumOfFolds = 8;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];
    /**
     * 黑色透明区域
     */
    private Paint mSolidPaint;
    /**
     * 渐变阴影
     */
    private Paint mShadowPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;
    /**
     * 原各块宽度
     */
    private float mFoldWidth;
    /**
     * 折叠后各块宽度
     */
    private float mTranslateDisPerFold;

    private Canvas mCanvas = new Canvas();
    private Bitmap mBitmap;
    private boolean isReady;


    public FoldLayout(Context context) {
        this(context, null);
    }

    public FoldLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public FoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i] = new Matrix();
        }
        mSolidPaint = new Paint();
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
                Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowGradientMatrix = new Matrix();
        /*不执行Draw*/
        this.setWillNotDraw(false);
    }


    /**
     * 获取子布局的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        /*创建一个宽高与子布局相同的Bitmap*/
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        updateFold();
    }

    /**
     * 画子组件
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mFactor == 0) {
            return;
        }
        if (mFactor == 1) {
            super.dispatchDraw(canvas);
            return;
        }
        for (int i = 0; i < mNumOfFolds; i++) {
            canvas.save();
            canvas.concat(mMatrices[i]);
            canvas.clipRect(mFoldWidth * i, 0, mFoldWidth * i + mFoldWidth, getHeight());
            if (isReady) {
                canvas.drawBitmap(mBitmap, 0, 0, null);
            } else {
                super.dispatchDraw(mCanvas);
                canvas.drawBitmap(mBitmap, 0, 0, null);
                isReady = true;
            }
            canvas.translate(mFoldWidth * i, 0);
            if (i % 2 == 0) {
                canvas.drawRect(0, 0, mFoldWidth, getHeight(), mSolidPaint);
            } else {
                canvas.drawRect(0, 0, mFoldWidth, getHeight(), mShadowPaint);
            }
            canvas.restore();
        }

    }

    /**
     * 更新折叠数据
     */
    private void updateFold() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        mTranslateDis = w * mFactor;
        mFoldWidth = w / mNumOfFolds;
        mTranslateDisPerFold = mTranslateDis / mNumOfFolds;
        /*透明遮罩*/
        int alpha = (int) (225 * (1 - mFactor));
        mSolidPaint.setColor(Color.argb((int) (alpha * 0.8f), 0, 0, 0));
        /*阴影*/
        mShadowGradientMatrix.setScale(mFoldWidth, 1);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha(alpha);

        float depth = (float) (Math.sqrt(mFoldWidth * mFoldWidth - mTranslateDisPerFold * mTranslateDisPerFold) / 2);

        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];
        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i].reset();
            src[0] = i * mFoldWidth;
            src[1] = 0;
            src[2] = src[0] + mFoldWidth;
            src[3] = src[1];
            src[4] = src[2];
            src[5] = h;
            src[6] = src[0];
            src[7] = src[5];

            boolean isEven = i % 2 == 0;

            dst[0] = i * mTranslateDisPerFold;
            dst[1] = isEven ? 0 : depth;
            dst[2] = dst[0] + mTranslateDisPerFold;
            dst[3] = isEven ? depth : 0;
            dst[4] = dst[2];
            dst[5] = isEven ? h - depth : h;
            dst[6] = dst[0];
            dst[7] = isEven ? h : h - depth;
            /*取整*/
            for (int j = 0; j < 8; j++) {
                dst[j] = Math.round(dst[j]);
            }
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }

    }

    /**
     * 设置折叠宽度比
     *
     * @param factor
     */
    public void setFactor(float factor) {
        this.mFactor = factor;
        updateFold();
        invalidate();
    }

    public float getFactor() {
        return mFactor;
    }
}
