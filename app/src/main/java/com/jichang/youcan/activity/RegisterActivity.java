package com.jichang.youcan.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.bean.req.Register;
import com.jichang.youcan.data.callback.IYoucanDataResponse;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Base Activity, all activity must extend it
 */

public class RegisterActivity extends AbstractAppBaseActivity {

    private ImageView mBack;

    private CardView mCvAdd;

    private EditText mEtUserPhone;

    private EditText mEtPassword;

    private EditText mRepeatEtPassword;

    private Button mBtGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_register);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        mBack = (ImageView) findViewById(R.id.iv_register_back);
        mCvAdd = (CardView) findViewById(R.id.cv_add);
        mBtGo = (Button) findViewById(R.id.bt_go);
        mEtUserPhone = (EditText) findViewById(R.id.et_user_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mRepeatEtPassword = (EditText) findViewById(R.id.et_repeat_password);
    }

    @Override
    public void initEvents() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
        mBtGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btGoOnClick();
            }
        });
    }

    @Override
    public void loadData() {
    }


    @Override
    public void onBackPressed() {

    }

    private void btGoOnClick() {

        final String phone = mEtUserPhone.getText().toString().trim();
        final String password = mEtPassword.getText().toString().trim();
        final String repPassword = mRepeatEtPassword.getText().toString().trim();

        boolean nullEt = (!"".equals(phone)) &&
                (!"".equals(password)) &&
                (!"".equals(repPassword));

        boolean equEt = password.equals(repPassword);

        if (nullEt) {
            if (equEt) {
                Register register = new Register(phone, password);
                YoucanDataManager.getInstance().register(register, new IYoucanDataResponse<String>() {
                    @Override
                    public void onSuccess(final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getString(R.string.network_return_success).equals(s)) {
                                    ToastUtils.showToast(BaseApplication.getInstance(), s);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(SpConstants.USER_PHONE, phone);
                                    bundle.putString(SpConstants.USER_PASSWORD, password);
                                    IntentUtils.startActivity(RegisterActivity.this,
                                            UserConfirmActivity.class,
                                            bundle);
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
                ToastUtils.showToast(BaseApplication.getInstance(), getString(R.string.register_password_different));
            }

        } else {
            ToastUtils.showToast(BaseApplication.getInstance(), getString(R.string.login_account_password_is_empty));
        }

    }
}
