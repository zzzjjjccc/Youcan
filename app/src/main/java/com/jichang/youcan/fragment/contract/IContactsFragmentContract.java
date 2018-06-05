package com.jichang.youcan.fragment.contract;

import com.jichang.jichangprojectlibrary.presenter.BasePresenter;
import com.jichang.jichangprojectlibrary.presenter.BaseView;
import com.jichang.youcan.db.model.User;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public interface IContactsFragmentContract {

    interface Presenter extends BasePresenter<IContactsFragmentContract.View> {


        /**
         * Get information from location
         *
         * @param subscribe if true, subscribe, otherwise subscribed
         */
        void getUserInfoFromLocal(boolean subscribe);

        /**
         * Get information from network
         *
         * @param subscribe if true, subscribe, otherwise subscribed
         */
        void getUserInfoFromNet(boolean subscribe);

    }

    interface View extends BaseView {

        /**
         * Notice Activity show Date
         */
        void showUser(List<User> users);

        /**
         * Refresh date
         */
        void refreshAdapter();

    }
}
