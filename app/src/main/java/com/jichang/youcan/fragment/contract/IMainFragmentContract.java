package com.jichang.youcan.fragment.contract;

import com.jichang.jichangprojectlibrary.presenter.BasePresenter;
import com.jichang.jichangprojectlibrary.presenter.BaseView;
import com.jichang.youcan.db.model.Date;
import com.jichang.youcan.db.model.User;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public interface IMainFragmentContract {

    interface Presenter extends BasePresenter<IMainFragmentContract.View> {

        /**
         * Get information from local sql
         */
        void getDateInfoFromLocal();

        /**
         * Get information from network
         */
        void getDateInfoFromNet();

    }

    interface View extends BaseView {

        /**
         * Notice Activity show Date
         */
        void showDate(List<Date> dates);

        /**
         * Refresh date
         */
        void refreshAdapter();

        /**
         * Show choose dialog for user to select which man can vision his content
         *
         * @param users users
         * @param date  Date of you want to share
         */
        void showUsers(final List<User> users, String date);

    }
}
