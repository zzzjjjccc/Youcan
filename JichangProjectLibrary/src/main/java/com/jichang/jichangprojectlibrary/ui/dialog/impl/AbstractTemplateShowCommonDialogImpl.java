package com.jichang.jichangprojectlibrary.ui.dialog.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jichang.jichangprojectlibrary.R;
import com.jichang.jichangprojectlibrary.ui.dialog.IShowDialog;

/**
 * @author jichang     on 2018/1/18.
 *         descib: Use the template method to design this class for show common dialog
 */

public abstract class AbstractTemplateShowCommonDialogImpl implements IShowDialog {

    @Override
    public void showDialog(Context context, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(data)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expansionCallBack(null);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

}
