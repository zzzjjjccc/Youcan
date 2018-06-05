package com.jichang.jichangprojectlibrary.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class PermissionUtils {

    public static final int REQUEST_PERMISSION = 0x1111;

    /**
     * Check permission
     *
     * @param activity   Current activity
     * @param permission Permission will be checked
     * @return
     */
    private static boolean checkPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request permission from given
     *
     * @param activity    Current activity
     * @param permissions Permission will be requested
     */
    public static void requestPermission(Activity activity, String[] permissions) {
        String[] rPermissions = new String[permissions.length];
        int j = 0;
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (!checkPermission(activity, permission)) {
                rPermissions[j++] = permission;
            }
        }
        rPermissions = Arrays.copyOf(rPermissions, j);
        ActivityCompat.requestPermissions(activity, rPermissions, REQUEST_PERMISSION);
    }
}
