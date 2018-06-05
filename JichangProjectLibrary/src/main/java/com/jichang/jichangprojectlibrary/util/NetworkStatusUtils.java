package com.jichang.jichangprojectlibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author jichang
 * @date 2018/1/19
 * email: 2218982471@qq.com
 * describ: Network status
 */

public class NetworkStatusUtils {

    /**
     * Get status of current network
     *
     * @param context context
     * @return status, if the network is available, false otherwise
     */
    public static boolean status(Context context) {
        if (null != context) {
            ConnectivityManager manager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (null != info) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
