package com.jichang.jichangprojectlibrary;

import android.app.Application;

/**
 * @author jichang     on 2018/1/18
 *         email:   2218982471@qq.com
 *         describ:
 */

public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
