package com.jichang.youcan.fragment.callback;

import android.support.v7.widget.Toolbar;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public interface IBaseFragmentCallback {


    /**
     * Set toolbar and title
     *
     * @param toolbar
     * @param title
     */
    void setToolbar(Toolbar toolbar, String title);

}
