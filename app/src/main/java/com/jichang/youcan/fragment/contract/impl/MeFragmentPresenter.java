package com.jichang.youcan.fragment.contract.impl;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.presenter.AbstractPresenter;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.constant.BeaconConstants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.fragment.contract.IMeFragmentContract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.jichang.youcan.constant.BeaconConstants.SERVICE_UUID;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public class MeFragmentPresenter extends AbstractPresenter<IMeFragmentContract.View>
        implements IMeFragmentContract.Presenter {

    private String mNameSpace = "00000000000000000000";

    private AdvertiseCallback mCallback;

    @Override
    public AdvertiseCallback createAdvertiseCallback() {
        return new AdvertiseCallback() {
            @Override
            public void onStartFailure(int errorCode) {
                if (errorCode == ADVERTISE_FAILED_DATA_TOO_LARGE) {
                    ToastUtils.showToast(BaseApplication.getInstance(), "ADVERTISE_FAILED_DATA_TOO_LARGE");
                } else if (errorCode == ADVERTISE_FAILED_TOO_MANY_ADVERTISERS) {
                    ToastUtils.showToast(BaseApplication.getInstance(), "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS");
                } else if (errorCode == ADVERTISE_FAILED_ALREADY_STARTED) {
                    ToastUtils.showToast(BaseApplication.getInstance(), "ADVERTISE_FAILED_ALREADY_STARTED");
                } else if (errorCode == ADVERTISE_FAILED_INTERNAL_ERROR) {
                    ToastUtils.showToast(BaseApplication.getInstance(), "ADVERTISE_FAILED_INTERNAL_ERROR");
                } else if (errorCode == ADVERTISE_FAILED_FEATURE_UNSUPPORTED) {
                    ToastUtils.showToast(BaseApplication.getInstance(), "ADVERTISE_FAILED_FEATURE_UNSUPPORTED");
                } else {
                    ToastUtils.showToast(BaseApplication.getInstance(), "ADVERTISE_FAILED_WITH_UNKNOWN_ERROR");
                }
            }
        };
    }

    @Override
    public void startAdvertise(BluetoothLeAdvertiser blea, String data) {
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(true)
                .build();

        byte[] serviceData = null;
        try {
            serviceData = buildServiceData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdvertiseData advertiseData = new AdvertiseData.Builder()
                .addServiceData(SERVICE_UUID, serviceData)
                .addServiceUuid(SERVICE_UUID)
                .setIncludeTxPowerLevel(false)
                .setIncludeDeviceName(false)
                .build();

        mCallback = createAdvertiseCallback();
        blea.startAdvertising(settings, advertiseData, mCallback);
    }

    @Override
    public void stopAdvertise(BluetoothLeAdvertiser blea) {
        if (mCallback != null) {
            blea.stopAdvertising(mCallback);
        }
    }

    private boolean isValidHex(String s, int len) {
        return !(s == null || s.isEmpty()) &&
                s.length() == len * 2 &&
                s.matches("[0-9A-F]+");
    }

    private byte[] toByteArray(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) +
                    Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    private byte[] buildServiceData(String data) throws IOException {
        String instance = "a" + SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
        byte txPower = -16;
        byte[] nameSpaceBytes = toByteArray(mNameSpace);
        byte[] instanceBytes = toByteArray(instance);

        // 13B not used

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(new byte[]{BeaconConstants.UID_FRAME_TYPE, txPower});
        os.write(nameSpaceBytes);
        os.write(instanceBytes);

        // 13B not used

        return os.toByteArray();
    }
}
