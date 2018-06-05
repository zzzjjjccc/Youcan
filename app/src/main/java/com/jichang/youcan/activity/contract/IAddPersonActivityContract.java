package com.jichang.youcan.activity.contract;

import com.jichang.jichangprojectlibrary.presenter.BasePresenter;
import com.jichang.jichangprojectlibrary.presenter.BaseView;
import com.jichang.youcan.db.model.User;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/26
 * email: 2218982471@qq.com
 * describ:
 */

public interface IAddPersonActivityContract {

    interface Presenter extends BasePresenter<IAddPersonActivityContract.View> {

        /**
         * Get person information from network
         */
        void getPersonFromNet();


        /**
         * Share a content to other person
         *
         * @param phone Phone number of yours
         * @param date  Date
         * @param users fans
         */
        void share(String phone, String date, List<User> users);

    }

    interface View extends BaseView {

        /**
         * Notice activity to show person
         *
         * @param users Data to show
         */
        void showPerson(List<User> users);

        /**
         * Share Done and finish this activity
         */
        void shareDone();
    }
}
