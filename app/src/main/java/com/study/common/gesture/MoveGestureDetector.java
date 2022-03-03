package com.study.common.gesture;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.study.commonlibrary.base.BaseGestureDetector;

/**
 * Author:zx on 2019/11/1414:55
 */
public class MoveGestureDetector extends BaseGestureDetector {

    private PointF mCurrentPointer;
    private PointF mPrePointer;

    private PointF mDeltaPointer = new PointF();
    private PointF mExtenalPointer = new PointF();

    private OnMoveGestureListener mListener;

    public MoveGestureDetector(Context context, OnMoveGestureListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mListener.onMoveEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                boolean update = mListener.onMove(this);
                if (update) {
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                resetState();//防止没有接收到CANCEL or UP ,保险起见
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureInProgress = mListener.onMoveBegin(this);
                break;
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        final MotionEvent prev = mPreMotionEvent;
        mPrePointer = caculateFocalPointF(prev);
        mCurrentPointer = caculateFocalPointF(event);
        boolean isSkipThisEvent = prev.getPointerCount() != event.getPointerCount();
        mExtenalPointer.x = isSkipThisEvent ? 0 : mCurrentPointer.x - mPrePointer.x;
        mExtenalPointer.y = isSkipThisEvent ? 0 : mCurrentPointer.y - mPrePointer.y;
    }

    /**
     * 计算多触控点中心
     *
     * @param event
     * @return
     */
    private PointF caculateFocalPointF(MotionEvent event) {
        float x = 0, y = 0;
        final int count = event.getPointerCount();
        for (int i = 0; i < count; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= count;
        y /= count;
        return new PointF(x, y);
    }

    public float getMoveX() {
        return mExtenalPointer.x;
    }

    public float getMoveY() {
        return mExtenalPointer.y;
    }

    public interface OnMoveGestureListener {
         boolean onMoveBegin(MoveGestureDetector detector);

         boolean onMove(MoveGestureDetector detector);

         void onMoveEnd(MoveGestureDetector detector);
    }

    public static class SimpleMoveGestureDetector implements OnMoveGestureListener {

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {

        }
    }
}
