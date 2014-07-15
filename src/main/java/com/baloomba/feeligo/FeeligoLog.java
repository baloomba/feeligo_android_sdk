package com.baloomba.feeligo;

import android.util.Log;

public class FeeligoLog {

    // <editor-fold desc="VARIABLES">

    public static String TAG = "Volley";
    public static boolean DEBUG = BuildConfig.DEBUG;

    // </editor-fold>

    // <editor-fold desc="METHODS">

    // <editor-fold desc="VERBOSE">

    public static void v(String msg) {
        if (DEBUG)
            Log.v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    // </editor-fold>

    // <editor-fold desc="DEBUG">

    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    // </editor-fold>

    // <editor-fold desc="INFO">

    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    // </editor-fold>

    // <editor-fold desc="WARNING">

    public static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(tag, msg);
    }

    // </editor-fold>

    // <editor-fold desc="ERROR">

    public static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    // </editor-fold>

    // <editor-fold desc="WTF">

    public static void wtf(String msg) {
        if (DEBUG)
            Log.wtf(TAG, msg);
    }

    public static void wtf(String tag, String msg) {
        if (DEBUG)
            Log.wtf(tag, msg);
    }

    // </editor-fold>

    // </editor-fold>

}
