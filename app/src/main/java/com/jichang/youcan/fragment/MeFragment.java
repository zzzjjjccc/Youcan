package com.jichang.youcan.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.contract.IBaseContract;
import com.jichang.jichangprojectlibrary.ui.dialog.IShowDialog;
import com.jichang.jichangprojectlibrary.ui.dialog.impl.AbstractTemplateShowCommonDialogImpl;
import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.activity.LoginActivity;
import com.jichang.youcan.constant.BuildConstants;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.fragment.callback.IMeFragmentCallback;
import com.jichang.youcan.fragment.contract.IMeFragmentContract;
import com.jichang.youcan.fragment.contract.impl.MeFragmentPresenter;

/**
 * @author jichang
 * @date 2018/1/20
 * email: 2218982471@qq.com
 * describ:
 */

public class MeFragment extends Fragment
        implements View.OnClickListener, IBaseContract, IMeFragmentContract.View {

    private final static int MIN_LINES = 6;

    private final static int MAX_LINES = 100;

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    private View mRootView;

    private Toolbar mToolbar;

    private TextView mFaith;

    private TextView mLoadMore;

    private TextView mVersion;

    private TextView mName;

    private TextView mExit;

    private Switch mSwitchBroadcast;

    private Switch mSwitchNotification;

    private IMeFragmentCallback mCallback;

    private MeFragmentPresenter mPresenter;

    private BluetoothLeAdvertiser mAdvertiser;

    private String mBroadcastData;

    private BluetoothAdapter mBluAdapter;

    private int mMaxLine = MIN_LINES;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_youcan_me, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public void setFragmentCallback(IMeFragmentCallback callback) {
        mCallback = callback;
    }

    @Override
    public void initVariables() {
        mPresenter = new MeFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        mToolbar = mRootView.findViewById(R.id.toolbar);
        mFaith = mRootView.findViewById(R.id.tv_fragment_faith);
        mLoadMore = mRootView.findViewById(R.id.tv_load_more);
        mVersion = mRootView.findViewById(R.id.tv_youcan_version);
        mName = mRootView.findViewById(R.id.tv_me_name);
        mExit = mRootView.findViewById(R.id.tv_me_exit);
        mSwitchBroadcast = mRootView.findViewById(R.id.s_me_broadcast);
        mSwitchNotification = mRootView.findViewById(R.id.s_me_notice);
    }

    @Override
    public void initEvents() {
        mLoadMore.setOnClickListener(this);
        mExit.setOnClickListener(this);

        mSwitchBroadcast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mAdvertiser != null && mBluAdapter.isEnabled()) {
                        mPresenter.startAdvertise(mAdvertiser, mBroadcastData);
                    } else {
                        initAdvertise();
                    }
                } else {
                    if (mAdvertiser != null && mBluAdapter.isEnabled()) {
                        mPresenter.stopAdvertise(mAdvertiser);
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        mCallback.setToolbar(mToolbar, "个人中心");
        mFaith.setText(R.string.fragment_me_faith);
        mVersion.setText(getActivity().getResources().getString(R.string.youcan_version) + Constants.TAB + BuildConstants.VERSION);
        mName.setText((String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_NAME, ""));
        mBroadcastData = mFaith.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_load_more) {
            loadMore();
        } else if (i == R.id.tv_me_exit) {
            AppManager.getAppManager().finishAllActivity();
            SpUtils.put(BaseApplication.getInstance(), SpConstants.LOGIN, true);
            IntentUtils.startActivity(getContext(), LoginActivity.class);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                initAdvertise();
            } else {
                ToastUtils.showToast(BaseApplication.getInstance(), getString(R.string.refuse_open_bluetooth));
                mSwitchBroadcast.setChecked(false);
            }
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showErrorView(String msg) {

    }

    /**
     * 计算文字是否沾满maxlines。
     *
     * @return true 代表沾满了，false 没沾满
     */
    private boolean judgeFull() {
        return mFaith.getPaint()
                .measureText(mFaith.getText().toString()) > mMaxLine * (mFaith.getWidth() -
                mFaith.getPaddingRight() - mFaith.getPaddingLeft());
    }

    private void initAdvertise() {
        BluetoothManager manager = (BluetoothManager) getActivity()
                .getApplicationContext()
                .getSystemService(Context.BLUETOOTH_SERVICE);
        mBluAdapter = manager.getAdapter();
        IShowDialog iShowDialog = new AbstractTemplateShowCommonDialogImpl() {
            @Override
            public void expansionCallBack(String data) {
                AppManager.getAppManager().finishActivity();
            }
        };
        if (mBluAdapter == null) {
            iShowDialog.showDialog(getContext(), "该设备上没有检测到蓝牙");
        } else if (!mBluAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        } else if (!mBluAdapter.isMultipleAdvertisementSupported()) {
            iShowDialog.showDialog(getContext(), "该设备蓝牙不支持BLE advertising");
        } else {
            mAdvertiser = mBluAdapter.getBluetoothLeAdvertiser();
            if (mAdvertiser != null && mBluAdapter.isEnabled()) {
                mPresenter.startAdvertise(mAdvertiser, mBroadcastData);
            }
        }
    }

    private void loadMore() {
        mFaith.post(new Runnable() {
            @Override
            public void run() {
                if (judgeFull()) {
                    mMaxLine = MAX_LINES;
                    mFaith.setMaxLines(mMaxLine);
                    mLoadMore.setText(R.string.load_less);
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_text_vision_fold);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mLoadMore.setCompoundDrawables(null, null, drawable, null);
                } else {
                    mMaxLine = MIN_LINES;
                    mFaith.setMaxLines(mMaxLine);
                    mLoadMore.setText(R.string.load_more);
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_text_vision_more);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mLoadMore.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
    }

}
