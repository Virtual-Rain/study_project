package com.study.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.study.common.R;
import com.study.common.helper.HelperDraw;
import com.study.commonlibrary.uitls.ScreenUtils;

/**
 * Author:zx on 2019/11/910:46
 */
public class CanvasView extends View {

    private Paint mGridPaint;
    private Point mWinSize;
    private Point mCoo;

    private Paint mRedPaint;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mWinSize = new Point();
        mCoo = new Point(500, 500);
        HelperDraw.loadWinSize(getContext(), mWinSize);
        mWinSize.y = mWinSize.y - ScreenUtils.getStatusHeight(getContext());
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStrokeWidth(20);
        mRedPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(224, 247, 245);
        drawGrid(canvas, mWinSize, mGridPaint);
        drawCoo(canvas, mCoo, mWinSize, mGridPaint);
        drawText4Coo(canvas, mCoo, mWinSize, mGridPaint);

        clip(canvas);
    }

    /**
     * 绘制网格
     *
     * @param canvas
     * @param winSize
     * @param paint
     */
    public void drawGrid(Canvas canvas, Point winSize, Paint paint) {
        paint.setStrokeWidth(2);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        canvas.drawPath(HelperDraw.gridPath(50, winSize), paint);
    }

    /**
     * 绘制坐标系
     *
     * @param canvas
     * @param coo
     * @param winSize
     */
    public void drawCoo(Canvas canvas, Point coo, Point winSize, Paint paint) {
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
        canvas.drawPath(HelperDraw.cooPath(coo, winSize), paint);
        //左箭头
        canvas.drawLine(winSize.x, coo.y, winSize.x - 40, coo.y - 20, paint);
        canvas.drawLine(winSize.x, coo.y, winSize.x - 40, coo.y + 20, paint);
        //下箭头
        canvas.drawLine(coo.x, winSize.y, coo.x - 20, winSize.y - 40, paint);
        canvas.drawLine(coo.x, winSize.y, coo.x + 20, winSize.y - 40, paint);
    }

    /**
     * 为坐标系绘制文字
     *
     * @param canvas
     * @param coo
     * @param winSize
     * @param paint
     */
    private void drawText4Coo(Canvas canvas, Point coo, Point winSize, Paint paint) {
        paint.setTextSize(50);
        canvas.drawText("x", winSize.x - 60, coo.y - 40, paint);
        canvas.drawText("y", coo.x - 40, winSize.y - 60, paint);
        paint.setTextSize(25);
        //x正轴文字
        for (int i = 1; i < (winSize.x - coo.x) / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(100 * i + "", coo.x - 20 + 100 * i, coo.y + 40, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x + 100 * i, coo.y, coo.x + 100 * i, coo.y - 10, paint);
        }
        //x负轴文字
        for (int i = 1; i < coo.x / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(-100 * i + "", coo.x - 20 - 100 * i, coo.y + 40, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x - 100 * i, coo.y, coo.x - 100 * i, coo.y - 10, paint);
        }
        //y正轴文字
        for (int i = 1; i < (winSize.y - coo.y) / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(100 * i + "", coo.x + 40, coo.y + 100 * i, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x, coo.y + 100 * i, coo.x + 20, coo.y + 100 * i, paint);
        }
        //y负轴文字
        for (int i = 1; i < coo.y / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(-100 * i + "", coo.x + 40, coo.y - 100 * i, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x, coo.y - 100 * i, coo.x + 20, coo.y - 100 * i, paint);
        }
    }

    /**
     * 画点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        canvas.drawPoint(100, 100, mRedPaint);
        canvas.drawPoints(new float[]{
                400, 400, 500, 500,
                600, 400, 700, 350,
                800, 300, 900, 300
        }, mRedPaint);
    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawLine(500, 300, 900, 400, mRedPaint);
        canvas.drawLines(new float[]{
                600, 600, 900, 600,
                900, 600, 600, 800,
                600, 800, 900, 800
        }, mRedPaint);
    }

    /**
     * 画矩形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        canvas.drawRect(100, 600, 400, 800, mRedPaint);
        canvas.drawRoundRect(600, 600, 900, 800, 50, 50, mRedPaint);
    }

    /**
     * 类圆
     *
     * @param canvas
     */
    private void drawLikeCircle(Canvas canvas) {
        canvas.drawCircle(600, 700, 100, mRedPaint);
        RectF rectF = new RectF(100, 600, 400, 800);
        canvas.drawOval(rectF, mRedPaint);

        RectF rectArc = new RectF(700, 700, 900, 800);
        canvas.drawArc(rectArc, 0, 90, true, mRedPaint);


        RectF rectArc2 = new RectF(900, 700, 1100, 800);
        canvas.drawArc(rectArc2, 0, 90, false, mRedPaint);

    }

    /**
     * 画图片
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_naruto);
        canvas.drawBitmap(bitmap, 100, 200, mRedPaint);
//        Matrix matrix = new Matrix();
//        matrix.setValues(new float[]{
//                1, 0, 300 * 0.5f,
//                0, 1, 600 * 0.5f,
//                0, 0, 0.5f
//        });
//        canvas.drawBitmap(bitmap, matrix, mRedPaint);
//
//        RectF rectF=new RectF(700,600,800,700);
//        canvas.drawBitmap(bitmap,null,rectF,mRedPaint);

        Rect rect = new Rect(0, 0, 200, 300);
        RectF rectF1 = new RectF(500,500 , 1200, 900);
        canvas.drawBitmap(bitmap, rect, rectF1, mRedPaint);

    }

    /**
     * 画Picture
     * @param canvas
     */
    private void drawPicture(Canvas canvas){
        Picture picture=new Picture();
        Canvas recodingCanvas=picture.beginRecording(canvas.getWidth(),canvas.getHeight());
        recodingCanvas.drawRect(100,400,200,500,mRedPaint);
        recodingCanvas.drawRect(0,500,100,600,mRedPaint);
        recodingCanvas.drawRect(200,500,300,600,mRedPaint);
        picture.endRecording();
        canvas.save();
        canvas.drawPicture(picture);
        canvas.translate(0,300);
        picture.draw(canvas);
        canvas.drawPicture(picture);
        canvas.translate(350,0);
        picture.draw(canvas);
        canvas.drawPicture(picture);
        canvas.restore();
    }

    /**
     * Canvas State
     * @param canvas
     */
    private void stateTest(Canvas canvas){
        canvas.save();
        canvas.rotate(45,mCoo.x+100,mCoo.y+100);
        canvas.drawRect(mCoo.x+100,mCoo.y+100,mCoo.x+300,mCoo.y+200,mRedPaint);
        canvas.restore();
        canvas.save();
        canvas.translate(mCoo.x,mCoo.y);
        canvas.scale(2,2,100,100);
        mRedPaint.setColor(Color.parseColor("#880FB5FD"));
        canvas.drawRect(100,100,300,200,mRedPaint);
        canvas.restore();
        canvas.save();
        canvas.translate(mCoo.x,mCoo.y);
        mRedPaint.setColor(Color.parseColor("#880F3330"));
        canvas.skew(0.3f,0.3f);
        canvas.drawRect(300,100,500,200,mRedPaint);
        canvas.restore();
    }

    /**
     * 裁剪
     * @param canvas
     */
    private void clip(Canvas canvas){
        Rect rect = new Rect(mCoo.x+20, mCoo.y+100, mCoo.x+250, mCoo.y+300);
        canvas.clipRect(rect);
        canvas.translate(mCoo.x,mCoo.y);
        canvas.drawRect(0, 0, 200, 300, mRedPaint);
    }
}
