package fr.baloomba.feeligo.helper;

import android.util.Log;

import com.baloomba.feeligo.BuildConfig;

public class FeeligoLog {

    //<editor-fold desc="VARIABLES">

//    private static final Boolean SUPER_DEBUG_ENABLED = false;
//    public static final int SUPER_DEBUG = 1;

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;

    //</editor-fold>

    // <editor-fold desc="METHODS">

    public static void log(int logMode, String message) {
        log(logMode, "", message);
    }

    public static void log(int logMode, String app, String tag, String message) {
        log(logMode, app + ":" + tag, message);
    }

    public static void log(int logMode, String tag, String message) {
        if (BuildConfig.DEBUG) {
            tag = BuildConfig.PACKAGE_NAME + ":" + tag;
            message = ((message == null) ? "[LOGGER MESSAGE] - null" : ((message.length() > 0) ?
                    message : "[LOGGER MESSAGE] - Empty log string"));
            switch (logMode) {
//                case SUPER_DEBUG:
//                    if (SUPER_DEBUG_ENABLED)
//                        Log.d(tag, message);
//                    break;
                case DEBUG:
                    Log.d(tag, message);
                    break;
                case ERROR:
                    Log.e(tag, message);
                    break;
                case INFO:
                    Log.i(tag, message);
                    break;
                case VERBOSE:
                    Log.v(tag, message);
                    break;
                case WARN:
                    Log.w(tag, message);
                    break;
                default:
                    break;
            }
        }
    }

    // </editor-fold>

}
