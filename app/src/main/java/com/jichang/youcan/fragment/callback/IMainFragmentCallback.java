package com.jichang.youcan.fragment.callback;

import com.jichang.jichangprojectlibrary.db.BaseSQLiteOpenHelper;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public interface IMainFragmentCallback extends IBaseFragmentCallback{

    /**
     * Call this method to get DbHelper for fragment
     */
    BaseSQLiteOpenHelper getDbHelper();

    // todo If continue
}
