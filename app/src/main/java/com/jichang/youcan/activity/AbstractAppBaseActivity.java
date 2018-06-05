package com.jichang.youcan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.db.BaseSQLiteOpenHelper;
import com.jichang.jichangprojectlibrary.activity.BaseActivity;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.db.NoteContract;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Base Activity, all activity must extend it
 */

public abstract class AbstractAppBaseActivity extends BaseActivity {

    private static final String TAG = "AbstractAppBaseActivity";

    protected BaseSQLiteOpenHelper mDBHelper = new BaseSQLiteOpenHelper(BaseApplication.getInstance(),
            NoteContract.DATABASE_NAME,
            NoteContract.DATABASE_VERSION,
            NoteContract.getCreateTables());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "The current activity is : " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            mDBHelper.close();
            LogUtils.i(TAG, "sqlite closing");
        }
    }

}
