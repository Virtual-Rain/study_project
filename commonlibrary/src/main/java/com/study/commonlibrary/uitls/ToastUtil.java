package com.study.commonlibrary.uitls;

import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Author:zx on 2019/9/2716:37
 */
public class ToastUtil {
    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     *
     *
     * @param text
     */
    public static void showError(String text)
    {
        Toasty.error(AppUtil.getContext(), text, Toast.LENGTH_SHORT, true).show();
    }
    public static void showSuccess(String text)
    {
        Toasty.success(AppUtil.getContext(), text, Toast.LENGTH_SHORT, true).show();
    }
    public static void showInfo(String text)
    {
        Toasty.info(AppUtil.getContext(), text, Toast.LENGTH_SHORT, true).show();
    }
    public static void showWarning(String text)
    {
        Toasty.warning(AppUtil.getContext(), text, Toast.LENGTH_SHORT, true).show();
    }
}
