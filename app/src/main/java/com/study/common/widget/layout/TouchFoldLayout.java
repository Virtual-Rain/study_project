package com.study.common.widget.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Author:zx on 2019/11/3018:13
 */
public class TouchFoldLayout extends FoldLayout {
    private GestureDetector mScrollGestureDetector;
    private int mTranslation = -1;

    public TouchFoldLayout(Context context) {
        this(context, null);
    }

    public TouchFoldLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchFoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScrollGestureDetector = new GestureDetector(context, new ScrollGestureDetector());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScrollGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mTranslation == -1) {
            mTranslation = getWidth();
        }
        super.dispatchDraw(canvas);
    }

    class ScrollGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mTranslation -= distanceX;
            if (mTranslation < 0) {
                mTranslation = 0;
            }
            if (mTranslation > getWidth()) {
                mTranslation = getWidth();
            }
            float factor = Math.abs(((float) mTranslation) / ((float) getWidth()));
            setFactor(factor);
            return true;
        }
    }
}
