package com.jichang.jichangprojectlibrary.presenter;

/**
 * @author jichang
 * @date 2018/1/19
 * email: 2218982471@qq.com
 * describ:
 */

public interface BaseView {

    /**
     * Call this method when error occurs to show message to your users
     *
     * @param msg Message
     */
    void showErrorMsg(String msg);

    /**
     * Call this method when error occurs to show view to your users
     *
     * @param msg
     */
    void showErrorView(String msg);

}
