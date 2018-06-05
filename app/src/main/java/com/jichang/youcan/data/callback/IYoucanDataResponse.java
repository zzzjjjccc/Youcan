package com.jichang.youcan.data.callback;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ: Call back when get data from network
 */

public interface IYoucanDataResponse<T> {

    /**
     * Get data success
     *
     * @param t real data
     */
    void onSuccess(T t);

    /**
     * Get data failed
     *
     * @param message Error message
     */
    void onError(String message);

}
