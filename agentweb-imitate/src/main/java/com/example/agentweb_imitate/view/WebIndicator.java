package com.example.agentweb_imitate.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agentweb_imitate.base.BaseIndicatorSpec;
import com.example.agentweb_imitate.widget.AgentWebUtils;

import java.util.jar.Attributes;

/**
 * Author:zx on 2019/9/1416:14
 */
public class WebIndicator extends BaseIndicatorView implements BaseIndicatorSpec {

    /*进度条颜色*/
    private int mColor;
    /*进度条画笔*/
    private Paint mPaint;
    /*进度条动画*/
    private Animator mAnimator;
    /*控件的宽度*/
    private int mTargetWidth = 0;
    /*默认匀速动画最大时长*/
    public static final int MAX_UNIFORM_SPEED_DURATION = 8 * 1000;
    /*默认加速后减速动画最大时长*/
    public static final int MAX_DECELERATE_SPEED_DURATION = 450;
    /*结束动画时长*/
    public static final int DO_END_ANIMATION_DURATION = 600;
    /*当前匀速运动动画最大时长*/
    private static int CURRENT_MAX_UNIFORM_SPPED_DURATION = MAX_UNIFORM_SPEED_DURATION;
    /*当前加速后减速动画最大时长*/
    private static int CURRENT_MAX_DECELERATE_SPEED_DURATION = MAX_DECELERATE_SPEED_DURATION;

    /*当前进度条的状态*/
    private int TAG = 0;
    public static final int UN_START = 0;
    public static final int STARTED = 1;
    public static final int FINISH = 2;
    private float mCurrentProgress = 0F;

    /*默认高度*/
    public static int WEB_INDICATOR_DEFAULT_HEIGHT = 3;

    public WebIndicator(@NonNull Context context) {
        this(context, null);
    }

    public WebIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mColor = Color.parseColor("#1aad19");
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mTargetWidth = context.getResources().getDisplayMetrics().widthPixels;
        WEB_INDICATOR_DEFAULT_HEIGHT = AgentWebUtils.dp2px(context, 3);

    }

    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
    }

    public void setColor(String color) {
        this.setColor(Color.parseColor(color));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        if (wMode == MeasureSpec.AT_MOST) {
            w = w <= getContext().getResources().getDisplayMetrics().widthPixels ? w : getContext().getResources().getDisplayMetrics().widthPixels;
        }
        if (hMode == MeasureSpec.AT_MOST) {
            h = WEB_INDICATOR_DEFAULT_HEIGHT;
        }


        this.setMeasuredDimension(w, h);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

    }

    @Override
    public void show() {
       if(getVisibility()==GONE){
           this.setVisibility(VISIBLE);
           mCurrentProgress=0f;
           startAnim(false);
       }
    }

    public void setProgress(float progress) {
        if (getVisibility() == GONE) {
            setVisibility(View.VISIBLE);
        }
        if (progress < 95) {
            return;
        }
        if (TAG != FINISH) {
            startAnim(true);
        }
    }

    private void startAnim(boolean isFinish) {

    }

    @Override
    public void setProgress(int newProgress) {
        setProgress(Float.valueOf(newProgress));
    }

    @Override
    public void hide() {
        TAG = FINISH;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /**
         * animator cause leak , if not cancel;
         */
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float t = (float) valueAnimator.getAnimatedValue();
            WebIndicator.this.mCurrentProgress = t;
            WebIndicator.this.invalidate();
        }
    };

    private AnimatorListenerAdapter mAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            doEnd();
        }
    };

    private void doEnd() {
        if (TAG == FINISH && mCurrentProgress == 100f) {
            setVisibility(GONE);
            mCurrentProgress = 0f;
            this.setAlpha(1f);
        }
        TAG = UN_START;
    }

}
