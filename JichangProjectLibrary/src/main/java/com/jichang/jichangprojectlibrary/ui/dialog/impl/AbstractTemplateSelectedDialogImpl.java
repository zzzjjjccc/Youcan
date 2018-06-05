package com.jichang.jichangprojectlibrary.ui.dialog.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.jichang.jichangprojectlibrary.Constants;
import com.jichang.jichangprojectlibrary.R;
import com.jichang.jichangprojectlibrary.ui.dialog.IShowDialog;

/**
 * @author jichang     on 2018/1/18.
 *         descib: Use the template method to design this class for show select dialog
 */

public abstract class AbstractTemplateSelectedDialogImpl implements IShowDialog {

    @Override
    public void showDialog(Context context, final String data) {
        final String[] priority = new String[1];
        final boolean[] isSelected = new boolean[1];
        final String[] choices = data.split(Constants.DIVIDER);
        isSelected[0] = false;

        if (choices.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle(R.string.dialog_choose)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!isSelected[0]) {
                                priority[0] = choices[0];
                            }
                            expansionCallBack(priority[0]);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.setSingleChoiceItems(
                    choices,
                    0,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isSelected[0] = true;
                            priority[0] = choices[which];
                        }
                    }
            ).show();
        }
    }
}
