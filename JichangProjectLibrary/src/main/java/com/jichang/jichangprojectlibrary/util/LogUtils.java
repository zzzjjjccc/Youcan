package com.jichang.jichangprojectlibrary.util;

import android.util.Log;

import com.jichang.jichangprojectlibrary.BuildConfig;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ: Manage all log message of this project
 */

public class LogUtils {

    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    private static int level = VERBOSE;

    private static boolean SHOW_LOG = BuildConfig.SHOW_LOG;

    public static void v(String tag, String msg) {
        if (SHOW_LOG && level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG && level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG && level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (SHOW_LOG && level <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG && level <= ERROR) {
            Log.e(tag, msg);
        }
    }
}