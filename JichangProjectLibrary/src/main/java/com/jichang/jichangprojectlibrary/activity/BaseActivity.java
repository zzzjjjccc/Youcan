package com.jichang.jichangprojectlibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jichang.jichangprojectlibrary.contract.IBaseContract;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Base Activity, all activity must extend it
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseContract {

    public int officeType = 1;

    protected Activity mContext = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        // Todo Close the network request when activity destroy
    }

    /**
     * Set the result of startActivityForResult
     *
     * @param bundle result
     */
    public void rSetResult(Bundle bundle) {
        Intent localIntent = new Intent();
        if (null != bundle) {
            localIntent.putExtras(bundle);
        }
        setResult(RESULT_OK, localIntent);
        BaseActivity.this.finish();
    }

    /**
     * Fragment return
     *
     * @param count The number of plies of fragment. 1, represent first fragment; 2, second fragment
     */
    public void popBackStackForFragment(int count) {
        int total = getSupportFragmentManager().getBackStackEntryCount();
        if (total > count) {
            getSupportFragmentManager().popBackStack(count, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            finish();
        }
    }
}
