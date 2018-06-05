package com.jichang.youcan.notification.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jichang.youcan.activity.MainActivity;
import com.jichang.youcan.activity.ContentActivity;
import com.jichang.youcan.notification.IShowNotification;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class ShowEventNotificationImpl implements IShowNotification {
    @Override
    public void showNotice(Context context, int icon, String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_DATE, date);

        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setPriority(0)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pi);

        Notification mNotification = mBuilder.build();
        mNotification.flags = Notification.FLAG_SHOW_LIGHTS;
        manager.notify(1, mNotification);
    }
}
