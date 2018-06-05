package com.jichang.youcan.fragment.contract;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.BluetoothLeAdvertiser;

import com.jichang.jichangprojectlibrary.presenter.BasePresenter;
import com.jichang.jichangprojectlibrary.presenter.BaseView;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public interface IMeFragmentContract {

    interface Presenter extends BasePresenter<IMeFragmentContract.View> {

        /**
         * Create a calback for Advertise
         */
        AdvertiseCallback createAdvertiseCallback();


        /**
         * Start advertising, broadcast yourself to others
         *
         * @param data Broadcast for other people
         */
        void startAdvertise(BluetoothLeAdvertiser blea, String data);

        /**
         * Stop advertising
         */
        void stopAdvertise(BluetoothLeAdvertiser blea);

    }

    interface View extends BaseView {

    }
}
