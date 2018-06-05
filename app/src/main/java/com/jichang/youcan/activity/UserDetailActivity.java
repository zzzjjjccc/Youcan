package com.jichang.youcan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.callback.IYoucanDataResponse;

/**
 * @author jichang
 * @date 2018/1/26
 * email: 2218982471@qq.com
 * describ:
 */

public class UserDetailActivity extends AbstractAppBaseActivity
        implements View.OnClickListener {

    private Toolbar mToolbar;

    /**
     * Could be subscribe
     */
    private String mIdol;

    private TextView mSchool;

    private TextView mMajor;

    private TextView mName;

    private TextView mSex;

    /**
     * Enabled is false, and if idol's phone number not be subscribed, it will change to enable
     */
    private TextView mAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_person_detail);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void initVariables() {
        mIdol = getIntent().getStringExtra(SpConstants.USER_PHONE);
    }

    @Override
    public void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSchool = (TextView) findViewById(R.id.tv_person_detail_school);
        mMajor = (TextView) findViewById(R.id.tv_person_detail_major);
        mName = (TextView) findViewById(R.id.tv_person_detail_name);
        mSex = (TextView) findViewById(R.id.tv_person_detail_sex);
        mAdd = (TextView) findViewById(R.id.tv_person_detail_add);
    }

    @Override
    public void initEvents() {
        mAdd.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle("用户详情");
        }

        if (mIdol != null) {
            YoucanDataManager.getInstance().getUser(mIdol, new IYoucanDataResponse<String>() {
                @Override
                public void onSuccess(final String s) {
                    if (s.contains(Constants.DIVIDER_PART)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setViewRes(s);
                            }
                        });
                    }
                }

                @Override
                public void onError(final String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(UserDetailActivity.this, message);
                        }
                    });
                }
            });
        } else {
            ToastUtils.showToast(UserDetailActivity.this, getString(R.string.do_not_know_error));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String phone = (String) SpUtils.get(UserDetailActivity.this, SpConstants.USER_PHONE, "");
        if (!"".equals(phone)) {
            YoucanDataManager.getInstance().isIdol(phone, mIdol, new IYoucanDataResponse<String>() {
                @Override
                public void onSuccess(String s) {
                    if (getString(R.string.yes_net_response).equals(s)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdd.setText(R.string.has_been_subscribe);
                            }
                        });
                    } else if(getString(R.string.no_net_response).equals(s)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdd.setEnabled(true);
                            }
                        });
                    }
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdd.setText(R.string.can_add_idol);
                        }
                    });
                }
            });
        }

    }

    private void setViewRes(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] data = s.split(Constants.DIVIDER_PART);
                if (data.length >= 4) {
                    mSchool.setText(data[0]);
                    mMajor.setText(data[1]);
                    mName.setText(data[2]);
                    mSex.setText(data[3]);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        final int i = v.getId();
        if (i == R.id.tv_person_detail_add) {
            ToastUtils.showToast(UserDetailActivity.this, "Add");
            String phone = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
            boolean empty = "".equals(phone) ||
                    "".equals(mIdol);
            if (!empty) {
                YoucanDataManager.getInstance().addSubscribe(phone, mIdol, new IYoucanDataResponse<String>() {
                    @Override
                    public void onSuccess(final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getString(R.string.network_return_success).equals(s)) {
                                    mAdd.setText(R.string.has_been_subscribe);
                                } else {
                                    ToastUtils.showToast(UserDetailActivity.this, s);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final String message) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(UserDetailActivity.this, message);
                            }
                        });
                    }
                });
            }

        }
    }
}
