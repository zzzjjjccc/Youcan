package com.jichang.jichangprojectlibrary.ui.dialog.impl;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.jichang.jichangprojectlibrary.ui.dialog.IShowDialog;
import com.jichang.jichangprojectlibrary.util.LogUtils;

import java.text.SimpleDateFormat;

/**
 * @author jichang     on 2018/1/18.
 *         descib: Use the template method to design this class for show date picker dialog
 */

public abstract class AbstractTemplateDatePickerDialogImpl implements IShowDialog {

    private static final String TAG = "AbstractTemplateDatePickerDialogImpl";

    @Override
    public void showDialog(Context context, String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String yyMMdd = format.format(new java.util.Date());
        final int year = Integer.parseInt(yyMMdd.split("-")[0]);
        final int month = Integer.parseInt(yyMMdd.split("-")[1]) - 1;
        final int day = Integer.parseInt(yyMMdd.split("-")[2]) + 1;
        DatePickerDialog dialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // 小于10的在前面添加0.
                        final int month = monthOfYear + 1;
                        final String date = year + "-" +
                                (month >= 10 ? month : "0" + month) + "-" +
                                (dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                        LogUtils.i(TAG, "DatePickerDialog will be created with year : " + year +
                                " month : " + month + " day : " + dayOfMonth);
                        expansionCallBack(date);
                    }
                },
                year, month, day
        );
        dialog.setCancelable(false);
        dialog.show();
    }

}
