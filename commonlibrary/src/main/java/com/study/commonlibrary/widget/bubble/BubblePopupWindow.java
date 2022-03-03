package com.study.commonlibrary.widget.bubble;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.study.commonlibrary.R;
import com.zyyoona7.popup.EasyPopup;

/**
 * 气泡弹出框
 * Author:zx on 2019/11/1820:55
 */
public class BubblePopupWindow extends PopupWindow {
    private BubbleRelativeLayout bubbleView;
    private Context context;
    private View mParent;
    private static onCancelActionClickListener onCancelActionClickListener;
    private static popupDismissListener popupDismissListener;
    private TextView textView;
    private TextView tvContent;

    public BubblePopupWindow(Context context) {
        this.context = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //聚焦
        setFocusable(false);
        setOutsideTouchable(false);
        setClippingEnabled(false);
        setTouchable(true);

        ColorDrawable dw = new ColorDrawable(0);
        setBackgroundDrawable(dw);

    }

    public BubblePopupWindow isOutsideTouchable(boolean isTouch) {
        setOutsideTouchable(isTouch);
        return this;
    }

    public BubblePopupWindow setBubbleView(View view) {
        bubbleView = new BubbleRelativeLayout(context);
        bubbleView.setBackgroundColor(Color.TRANSPARENT);
        bubbleView.addView(view);
        setContentView(bubbleView);
        textView = bubbleView.findViewById(R.id.cancel_action);
        tvContent = bubbleView.findViewById(R.id.tv_bubble_content);
        //点击事件
        textView.setOnClickListener(v -> {
            dismiss();
            mParent.setClickable(true);
            if (null != onCancelActionClickListener) {
                onCancelActionClickListener.cancelAction();
            }
        });
        return this;
    }

    public BubblePopupWindow setBubbleContent(String content) {
        if (null != tvContent) {
            tvContent.setText(content);
        }
        return this;
    }

    public BubblePopupWindow setTriangleColor(int color) {
        if (null != bubbleView) {
            bubbleView.setShadowColor(color);
        }
        return this;
    }

    public void setContext(String context) {
        textView.setText(context);
    }

    public BubblePopupWindow setDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
        return this;
    }

    public void setParam(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void show(View parent) {
        show(parent, Gravity.TOP, 0.5f, 0, 0);
    }

    public void show(View parent, int gravity) {
        show(parent, gravity, 0.5f, 0, 0);
    }

    public void show(View parent, int gravity, float bubbleOffset) {
        show(parent, gravity, bubbleOffset, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param parent
     * @param gravity
     * @param bubbleOffset 气泡尖角位置偏移量。默认位于中间 1
     */
    public void show(View parent, int gravity, float bubbleOffset, int offsetX, int offsetY) {
        mParent = parent;
        parent.setClickable(false);
        BubbleRelativeLayout.BubbleLegOrientation orientation = BubbleRelativeLayout.BubbleLegOrientation.LEFT;
        if (!this.isShowing()) {
            switch (gravity) {
                case Gravity.BOTTOM:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
                    break;
                case Gravity.TOP:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.BOTTOM;
                    break;
                case Gravity.RIGHT:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.LEFT;
                    break;
                case Gravity.LEFT:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.RIGHT;
                    break;
                default:
                    break;
            }
            float Offset = getMeasuredWidth() * bubbleOffset;
            // 设置气泡布局方向及尖角偏移
            bubbleView.setBubbleParams(orientation, Offset);

            int[] location = new int[2];
            parent.getLocationOnScreen(location);

            switch (gravity) {
                case Gravity.BOTTOM:
                    ((Activity) context).runOnUiThread(() -> {
                        showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + offsetX - (int) (getMeasuredWidth() * bubbleOffset) + parent.getWidth() / 2, location[1] + parent.getHeight() + offsetY);
                    });
                    break;
                case Gravity.TOP:
                    ((Activity) context).runOnUiThread(() -> {
                        showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + offsetX - (int) (getMeasuredWidth() * bubbleOffset) + parent.getWidth() / 2, location[1] - getMeasureHeight() + offsetY);
                    });
                    break;
                case Gravity.RIGHT:
                    ((Activity) context).runOnUiThread(() -> {
                        showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth() + offsetX, location[1] - (parent.getHeight() / 2) + offsetY);
                    });
                    break;
                case Gravity.LEFT:
                    ((Activity) context).runOnUiThread(() -> {
                        showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - getMeasuredWidth() + offsetX, location[1] - (parent.getHeight() / 2) + offsetY);
                    });
                    break;
                default:
                    break;

            }
        } else {
            this.dismiss();
        }
    }

    /**
     * 测量高度
     *
     * @return
     */
    public int getMeasureHeight() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popHeight = getContentView().getMeasuredHeight();
        return popHeight;
    }

    /**
     * 测量宽度
     *
     * @return
     */
    public int getMeasuredWidth() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popWidth = getContentView().getMeasuredWidth();
        return popWidth;
    }

    public interface onCancelActionClickListener {
        void cancelAction();
    }

    public static void setonCancelActionClick(onCancelActionClickListener listener) {
        onCancelActionClickListener = listener;
    }

    public interface popupDismissListener {
        void popupDimiss();
    }

    public static void setPopupDismiss(popupDismissListener listener) {
        popupDismissListener = listener;
    }
}
