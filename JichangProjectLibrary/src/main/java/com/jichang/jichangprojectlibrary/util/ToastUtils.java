package com.jichang.jichangprojectlibrary.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：According to your need to display the toast
 */

public class ToastUtils {

    public static void showToast(Context context, String content) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(content);
        toast.show();
    }

}
