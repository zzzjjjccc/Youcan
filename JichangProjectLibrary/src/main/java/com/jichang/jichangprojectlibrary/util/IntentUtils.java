package com.jichang.jichangprojectlibrary.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class IntentUtils {

    /**
     * Start an Activity without bundle
     *
     * @param context Current context
     * @param cls     Activity which will be created
     */
    public static void startActivity(Context context, Class<? extends Activity> cls) {
        startActivity(context, cls, null);
    }

    /**
     * Start an Activity with bundle
     *
     * @param context Current context
     * @param cls     Activity which will be created
     */
    public static void startActivity(Context context, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * Start an Activity For Result
     *
     * @param activity
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity,
                                              Class<? extends Activity> cls,
                                              Bundle bundle,
                                              int requestCode) {
        Intent intent = new Intent(activity, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startService(Context context, String action) {
        startService(context, action, null);
    }

    public static void startService(Context context, String action, Bundle bundle) {
        Intent intent = new Intent();
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.setAction(action);
        intent.setPackage(PackageUtils.getPackageName(context));
        context.startService(intent);
    }

    /**
     * Start service
     *
     * @param context context
     * @param cls     Target
     */
    public static void startService(Context context, Class<? extends Service> cls) {
        startService(context, cls, null);
    }

    /**
     * Start service
     *
     * @param context context
     * @param cls     Target
     * @param bundle  Data
     */
    private static void startService(Context context, Class<? extends Service> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startService(intent);
    }

    /**
     * Setting Network
     *
     * @param context context
     */
    public static void settingNetwork(Context context) {
        Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
        context.startActivity(intent);
    }

    /**
     * Start activity with create new Task
     *
     * @param context context
     * @param cls     Target
     */
    public static void startActivityForClearTask(Context context, Class<? extends Activity> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * Start activity with clear top
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public static void startActivityForClearTop(Context context, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
