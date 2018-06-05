package com.jichang.youcan.fragment.contract;

import com.jichang.jichangprojectlibrary.presenter.BasePresenter;
import com.jichang.jichangprojectlibrary.presenter.BaseView;
import com.jichang.youcan.data.bean.res.DiscoverInfo;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public interface IDiscoverFragmentContract {

    interface Presenter extends BasePresenter<IDiscoverFragmentContract.View> {


        /**
         * Get information from location
         *
         */
        void getUserEventFromLocal();

        /**
         * Get information from network
         *
         */
        void getUserEventFromNet();

    }

    interface View extends BaseView {

        /**
         * Notice Activity show Date
         */
        void showEvent(List<DiscoverInfo> discoverInfos);

        /**
         * Refresh date
         */
        void refreshAdapter();

    }
}
