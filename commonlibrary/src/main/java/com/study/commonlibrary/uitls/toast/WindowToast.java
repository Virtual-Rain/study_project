package com.study.commonlibrary.uitls.toast;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class WindowToast {
    private WindowManager mWindowManager;
    private View view;
    WindowManager.LayoutParams params;
    private static final int SHOW = 0;
    private static final int HIDE = 1;
    private int duration ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case SHOW:
                    handler.sendEmptyMessageDelayed(HIDE,duration);
                    break;
                case HIDE:
                    hide();

                    break;
            }
        }
    };

    public  WindowToast makeToast(Context context ,String msg ,int duration){
        mWindowManager  = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.windowAnimations = android.R.style.Animation_Toast;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.width  = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        view = new TextView(context);
        TextView textView = (TextView) view;
        textView.setText(msg);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        this.duration = duration;
        return this;
    }

    public WindowToast setGravity(int type){
        params.gravity = type;
        return this;
    }
    public WindowToast setView(View view) {
        this.view = view;
        return this;
    }

    public void show(){
        if(view == null){
            throw  new RuntimeException("setView must have been called");
        }
        mWindowManager.addView(view,params);
        handler.sendEmptyMessage(SHOW);
    }

    public void hide(){

        mWindowManager.removeView(view);
    }
}
