package com.jichang.jichangprojectlibrary.ui.dialog;

import android.content.Context;

/**
 * @author jichang     on 2018/1/18.
 *         descib: Define tow method to use dialog
 */

public interface IShowDialog {

    /**
     * Show dialog
     *
     * @param context Activity or other
     * @param data    What you want to show
     */
    void showDialog(Context context, String data);

    /**
     * CallBack
     *
     * @param data
     */
    void expansionCallBack(String data);

}
