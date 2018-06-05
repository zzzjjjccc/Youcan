package com.jichang.jichangprojectlibrary.presenter;

/**
 * @author jichang
 * @date 2018/1/19
 * email: 2218982471@qq.com
 * describ:
 */

public interface BasePresenter<V extends BaseView> {

    /**
     * Bind view for your consume presenter
     *
     * @param view
     */
    void attachView(V view);

    /**
     * unBind view
     */
    void detachView();

    /**
     * On create
     */
    void onCreate();

    /**
     * On resume
     */
    void onResume();

    /**
     * On destroy
     */
    void onDestroy();

}
