package com.jichang.youcan.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class NoticeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case "jerry.com.note.broadcast.EVERY_NOTICE":

                break;
            case "jerry.com.note.broadcast.NOTE_NOTICE":

                break;

            case "jerry.com.note.broadcast.START_SERVICE":

                break;
            default:
                break;
        }
    }
}
