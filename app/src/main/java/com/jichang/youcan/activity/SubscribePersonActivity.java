package com.jichang.youcan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.activity.contract.IAddPersonActivityContract;
import com.jichang.youcan.activity.contract.impl.AddPersonActivityPresenter;
import com.jichang.youcan.adapter.SubscribeRecyclerAdapter;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.db.model.User;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/26
 * email: 2218982471@qq.com
 * describ:
 */

public class SubscribePersonActivity extends AbstractAppBaseActivity
        implements IAddPersonActivityContract.View {

    private RecyclerView mRecyclerView;

    AddPersonActivityPresenter mPresenter;

    private Toolbar mToolbar;

    private List<User> mUsers;

    private LinearLayoutManager mLayoutManager;

    /**
     * Which date you want to share
     */
    private String mDate;

    private SubscribeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_add_person);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void initVariables() {
        mPresenter = new AddPersonActivityPresenter();
        mPresenter.attachView(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                mUsers = (List<User>) bundle.getSerializable(Constants.INTENT_USER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDate = bundle.getString(Constants.INTENT_DATE);
        }
    }

    @Override
    public void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_subscribe);
        mLayoutManager = new LinearLayoutManager(SubscribePersonActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadData() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        if (mUsers == null) {
            actionBar.setTitle("添加关注");
            mPresenter.getPersonFromNet();
        } else {
            actionBar.setTitle("分享选择");
            mAdapter = new SubscribeRecyclerAdapter(mUsers);
            mAdapter.setCheck(true);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.menu_youcan_add_person_share:
                ToastUtils.showToast(SubscribePersonActivity.this, "分享成功");
                String phone = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
                mPresenter.share(phone, mDate, mAdapter.getShareUser());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mUsers != null) {
            getMenuInflater().inflate(R.menu.menu_youcan_add_person_share, menu);
        }
        return true;
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showErrorView(String msg) {

    }

    @Override
    public void showPerson(final List<User> users) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new SubscribeRecyclerAdapter(users);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

    }

    @Override
    public void shareDone() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
}
