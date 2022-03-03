package com.study.commonlibrary.uitls;

import android.util.Log;

/**
 * Author:zx on 2019/9/2716:40
 */
public class MLog {
    public static boolean isPrint = true;//是否要打印日志

    public static void v(String msg){
        print(Log.VERBOSE,msg);
    }
    public static void e(String msg){
        print(Log.ERROR,msg);
    }
    public static void d(String msg){
        print(Log.DEBUG,msg);
    }
    public static void w(String msg){
        print(Log.WARN,msg);
    }
    public static void i(String msg){
        print(Log.INFO,msg);
    }
    public static void a(String msg){
        print(Log.ASSERT,msg);
    }
    private static void print(int level,String msg){
        if (isPrint)
        {
            String tag = getClassName();
            String line = getLineIndicator();
            Log.println(level, tag, line + msg);
        }
    }

    private static String getClassName()
    {
        StackTraceElement element = ((new Exception()).getStackTrace())[3];

        return element.getFileName();
    }

    //获取行所在的方法指示

    private static String getLineIndicator() {
        //3代表方法的调用深度：0-getLineIndicator，1-performPrint，2-print，3-调用该工具类的方法位置
        StackTraceElement element = ((new Exception()).getStackTrace())[3];
        return "(" +
                element.getFileName() + ":" +
                element.getLineNumber() + ")." +
                element.getMethodName() + ":";
    }

    public static void e(String tag,String title, String s) {
        if (isPrint) {
            Log.e(tag, tag+":"+title+"---------------------"+ s);

        }
    }
    public static void e(String tag, String s) {
        if (isPrint) {
            Log.e(tag, tag+":---------------------"+ s);

        }
    }
}
