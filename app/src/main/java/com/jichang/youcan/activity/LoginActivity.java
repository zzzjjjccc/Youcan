package com.jichang.youcan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.bean.req.Register;
import com.jichang.youcan.data.callback.IYoucanDataResponse;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Login Activity
 */

public class LoginActivity extends AbstractAppBaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int RETURN_SPLIT_LENGTH = 4;

    private CardView mCv;

    private EditText mEtUserPhone;

    private EditText mEtPassword;

    private Button mBtGo;

    private ImageView mGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_login);
        login();
    }

    private void login() {
        boolean login = (boolean) SpUtils.get(BaseApplication.getInstance(), SpConstants.LOGIN, true);
        if (login) {
            initVariables();
            initViews();
            initEvents();
            loadData();
        } else {
            IntentUtils.startActivity(LoginActivity.this, MainActivity.class);
            AppManager.getAppManager().finishActivity();
        }

    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        mCv = (CardView) findViewById(R.id.cv);
        mEtUserPhone = (EditText) findViewById(R.id.et_user_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtGo = (Button) findViewById(R.id.bt_go);
        mGoRegister = (ImageView) findViewById(R.id.iv_login_to_register);
    }

    @Override
    public void initEvents() {
        mBtGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btGoOnClick();
            }
        });
        mGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOnClick();
            }
        });
    }

    @Override
    public void loadData() {

        String phone = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
        String password = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PASSWORD, "");
        boolean empty = "".equals(phone) &&
                "".equals(password);
        if (!empty) {
            mEtUserPhone.setText(phone);
            mEtPassword.setText(password);
        }
    }

    private void saveUser(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] data = s.split(Constants.DIVIDER_PART);
                if (data.length >= RETURN_SPLIT_LENGTH) {
                    SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_SCHOOL, data[0]);
                    SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_MAJOR, data[1]);
                    SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_NAME, data[2]);
                    SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_SEX, data[3]);
                }
            }
        });

    }

    private void fabOnClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void btGoOnClick() {

        final String phone = mEtUserPhone.getText().toString().trim();
        final String password = mEtPassword.getText().toString().trim();

        boolean empty = "".equals(phone) &&
                "".equals(password);

        if (!empty) {
            Register register = new Register(phone, password);
            YoucanDataManager.getInstance().login(register, new IYoucanDataResponse<String>() {
                @Override
                public void onSuccess(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getString(R.string.network_return_success).equals(s)) {
                                String pn = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");

                                boolean p = "".equals(pn) ||
                                        !phone.equals(pn);
                                if (p) {
                                    SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_PHONE, phone);
                                    SpUtils.put(BaseApplication.getInstance(), SpConstants.USER_PASSWORD, password);
                                    YoucanDataManager.getInstance().getUser(phone, new IYoucanDataResponse<String>() {
                                        @Override
                                        public void onSuccess(final String s) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    saveUser(s);
                                                    IntentUtils.startActivity(LoginActivity.this, MainActivity.class);
                                                    SpUtils.put(BaseApplication.getInstance(), SpConstants.LOGIN, false);
                                                    AppManager.getAppManager().finishActivity();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(final String message) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtils.showToast(LoginActivity.this, message);
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    IntentUtils.startActivity(LoginActivity.this, MainActivity.class);
                                    SpUtils.put(BaseApplication.getInstance(), SpConstants.LOGIN, false);
                                    AppManager.getAppManager().finishActivity();
                                }
                            } else {
                                ToastUtils.showToast(BaseApplication.getInstance(), s);
                            }
                        }
                    });
                }

                @Override
                public void onError(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(BaseApplication.getInstance(), s);
                        }
                    });

                }
            });
        } else {
            ToastUtils.showToast(BaseApplication.getInstance(), getString(R.string.login_account_password_is_empty));
        }

    }

}
