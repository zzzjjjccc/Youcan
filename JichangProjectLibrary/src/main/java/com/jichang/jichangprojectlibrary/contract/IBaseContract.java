package com.jichang.jichangprojectlibrary.contract;

/**
 * @author jichang     on 2018/1/18
 *         email:   2218982471@qq.com
 *         describ: All activity contract
 */

public interface IBaseContract {

    /**
     * Init current activity's variables
     */
    void initVariables();

    /**
     * Init current activity's views, just find View By Id
     */
    void initViews();

    /**
     * Init current activity's events
     */
    void initEvents();

    /**
     * Load data to current activity
     */
    void loadData();

}
