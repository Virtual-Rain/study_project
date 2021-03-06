package com.example.agentweb_imitate.widget;

import android.util.Log;

import com.example.agentweb_imitate.AgentWebConfig;

public class LogUtils {

    public static final String PREFIX = "agentweb - ";

    public static boolean isDebug() {
        return AgentWebConfig.DEBUG;
    }

    public static void i(String tag, String message) {
        if (isDebug()) {
            Log.i(PREFIX.concat(tag), message);
        }
    }

    public static void v(String tag, String message) {
        if (isDebug()) {
            Log.v(PREFIX.concat(tag), message);
        }
    }

    public static void e(String tag, String message) {
        if (isDebug()) {
            Log.e(PREFIX.concat(tag), message);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    public static void safeCheckCrash(String tag, String msg, Throwable tr) {
        if (isDebug()) {
            throw new RuntimeException(PREFIX.concat(tag) + " " + msg, tr);
        } else {
            Log.e(PREFIX.concat(tag), msg, tr);
        }
    }
}
