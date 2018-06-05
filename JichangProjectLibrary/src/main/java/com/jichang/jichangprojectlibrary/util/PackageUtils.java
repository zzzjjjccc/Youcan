package com.jichang.jichangprojectlibrary.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public final class PackageUtils {

    /**
     * According to you have given context, return the package name of this app
     *
     * @param context context
     * @return Package name
     */
    public static String getPackageName(Context context) {
        if (null != context) {
            return context.getPackageName();
        }
        return "com.jichang";
    }

    /**
     * @param context context
     * @return Version code
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(getPackageName(context), 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param context context
     * @return Version name
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(getPackageName(context), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Detect App
     *
     * @param context     context
     * @param packageName The package name of App
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        }
        return true;
    }

    public static int isAppRunning(ActivityManager am, String packageName) {
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return -1;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return info.id;
            }
        }
        return -1;
    }

}
