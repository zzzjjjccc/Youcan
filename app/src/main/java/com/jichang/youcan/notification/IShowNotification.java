package com.jichang.youcan.notification;

import android.content.Context;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public interface IShowNotification {

    /**Show notification
     * @param context context
     * @param icon Icon of notification
     * @param title Title of notification
     * @param content Content of notification
     */
    void showNotice(Context context, int icon, String title, String content);
}
