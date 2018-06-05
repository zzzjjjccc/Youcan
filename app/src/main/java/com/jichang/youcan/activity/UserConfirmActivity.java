package com.jichang.youcan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.util.IntentUtils;
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

public class UserConfirmActivity extends AbstractAppBaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private EditText mSchool;

    private EditText mMajor;

    private EditText mName;

    private EditText mSex;

    private EditText mPhoneE;

    private EditText mPasswordE;

    private TextView mAdd;

    private String mPhone;

    private String mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_person_info_confirm);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }


    @Override
    public void initVariables() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPhone = bundle.getString(SpConstants.USER_PHONE);
            mPassword = bundle.getString(SpConstants.USER_PASSWORD);
        }
    }

    @Override
    public void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSchool = (EditText) findViewById(R.id.tv_person_detail_school);
        mMajor = (EditText) findViewById(R.id.tv_person_detail_major);
        mName = (EditText) findViewById(R.id.tv_person_detail_name);
        mSex = (EditText) findViewById(R.id.tv_person_detail_sex);
        mAdd = (TextView) findViewById(R.id.tv_person_detail_add);
        mPhoneE = (EditText) findViewById(R.id.tv_person_detail_phone);
        mPasswordE = (EditText) findViewById(R.id.tv_person_detail_password);
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
            actionBar.setTitle("用户信息填写");
        }

        if (mPassword != null) {
            mPhoneE.setVisibility(View.GONE);
            mPasswordE.setVisibility(View.GONE);
        }
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
        int i = v.getId();
        if (i == R.id.tv_person_detail_add) {
            final String school = mSchool.getText().toString();
            final String major = mMajor.getText().toString();
            final String name = mName.getText().toString();
            final String sex = mSex.getText().toString();

            boolean empty = "".equals(school) ||
                    "".equals(major) ||
                    "".equals(name) ||
                    "".equals(sex) ||
                    mPhone == null ||
                    mPassword == null;

            if (!empty) {
                YoucanDataManager.getInstance().addUserDetail(mPhone,
                        school,
                        major,
                        name,
                        sex,
                        new IYoucanDataResponse<String>() {
                            @Override
                            public void onSuccess(final String s) {
                                if (Constants.NET_RETURN_SUCCESS.equals(s)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showToast(UserConfirmActivity.this, s);
                                            SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_PHONE, mPhone);
                                            SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_PASSWORD, mPassword);
                                            SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_SCHOOL, school);
                                            SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_MAJOR, major);
                                            SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_NAME, name);
                                            SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_SEX, sex);
                                            IntentUtils.startActivity(UserConfirmActivity.this, MainActivity.class);
                                            AppManager.getAppManager().finishActivity();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showToast(UserConfirmActivity.this, s);
                                        }
                                    });
                                }


                            }

                            @Override
                            public void onError(final String message) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast(UserConfirmActivity.this, message);
                                    }
                                });
                            }
                        });
            } else {
                ToastUtils.showToast(BaseApplication.getInstance(), getString(R.string.check_your_information_carefully));
            }
        }
    }
}
