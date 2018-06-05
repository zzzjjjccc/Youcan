package com.jichang.youcan.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.util.HttpUtils;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.ApiConstants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.notification.IShowNotification;
import com.jichang.youcan.notification.impl.ShowEventNotificationImpl;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class NoticeService extends Service {

    private static final String TAG = "NoticeService";

    private boolean notice = false;

    public NoticeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (notice) {
            noticeEvent();
        }
        updateBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 12 * 60 * 60 * 1000; //8个小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, NoticeService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtTime,
                pi);
        notice = true;
        LogUtils.i(TAG, "NoticeService's onStartCommand method be called");

        return super.onStartCommand(intent, flags, startId);
    }

    private void noticeEvent() {
        IShowNotification notificationInterface = new ShowEventNotificationImpl();
        notificationInterface.showNotice(this,
                R.drawable.header,
                "查看今日事件完成进度",
                "千里之行 始于足下    点击查看");
    }

    private void updateBingPic() {
        final String requestBingPic = ApiConstants.BING_PICTURE_URL;
        HttpUtils.get(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string();
                SpUtils.put(BaseApplication.getInstance(), SpConstants.BING_PICTURE_KEY, bingPic);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "更新图片服务停止");
    }
}
