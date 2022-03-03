package com.study.commonlibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.study.commonlibrary.widget.dialog.Loading;

import java.lang.ref.WeakReference;

/**
 * Author:zx on 2019/9/2716:53
 */
public class LoadingMaker {
    @SuppressLint("StaticFieldLeak")
    private  LoadingDialog loading;
    private WeakReference<Context> lastContext;    //
    private static LoadingMaker instance;

    public static LoadingMaker getInstance(){
        if (instance == null)
            instance = new LoadingMaker();
        return instance;
    }
    public  void showProgressDialog(Context context) {
        showProgressDialog(context, null, true, null);
    }

    public   void showProgressDialog(Context context, String message) {
        showProgressDialog(context, message, true, null);
    }


    public  void showProgressDialog(Context context, String message, boolean cancelable) {
        showProgressDialog(context,message, cancelable, null);
    }


    private  void showProgressDialog(Context context,String message, boolean cancelable, DialogInterface.OnDismissListener listener) {

        dismissProgressDialog();
        if (context == null || !(context instanceof AppCompatActivity)) {
            return;
        }
        WeakReference<Context> lastContext = new WeakReference<>(context);
        if (lastContext.get() != null) {
            loading = new Loading(lastContext.get());
            loading.setListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
//                    RequestFactory.getInstance().setRequestCount(0);//todo
                }
            });

            loading.setCanceledOnTouchOutside(cancelable);
            loading.setMessage(message);
            if (!loading.isShowing() && !((AppCompatActivity) context).isFinishing()) {
                loading.show();
            }
        }
    }

    public  void dismissProgressDialog() {
        if (null == loading) {
            return;
        }

        if (loading.isShowing()) {
            loading.dismiss();
        }

        if (loading!=null){
            Context context = loading.getContext();
            context = null;
            loading=null;
        }


//        if (lastContext != null)
//            lastContext = null;
//        loading = null;


    }

    public  void setOnCancelListener(DialogInterface.OnDismissListener listener) {
        if (null != loading) {
            loading.setListener(listener);
        }
    }
}
