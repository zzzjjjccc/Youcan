package com.jichang.youcan.fragment.contract.impl;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.presenter.AbstractPresenter;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.db.model.User;
import com.jichang.youcan.fragment.contract.IContactsFragmentContract;
import com.jichang.youcan.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public class ContactsFragmentPresenter extends AbstractPresenter<IContactsFragmentContract.View>
        implements IContactsFragmentContract.Presenter {


    @Override
    public void getUserInfoFromLocal(boolean subscribe) {

        if (subscribe) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                User user = new User();
                user.setName("Jiachang" + i);
                users.add(user);
            }
            view.showUser(users);
        } else {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                User user = new User();
                user.setName("Jiachang" + i);
                users.add(user);
            }
            view.showUser(users);
        }

    }

    @Override
    public void getUserInfoFromNet(boolean subscribe) {

        String phone = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
        boolean empty = "".equals(phone);
        if (!empty) {
            if (subscribe) {
                YoucanDataManager.getInstance().getIdols(phone, new IYoucanDataResponse<String>() {
                    @Override
                    public void onSuccess(String s) {
                        view.showUser(ConvertUtils.convert(s));
                    }

                    @Override
                    public void onError(String message) {
                        view.showErrorMsg(message);
                    }
                });
            } else {
                YoucanDataManager.getInstance().getFans(phone, new IYoucanDataResponse<String>() {
                    @Override
                    public void onSuccess(String s) {
                        view.showUser(ConvertUtils.convert(s));
                    }

                    @Override
                    public void onError(String message) {
                        view.showErrorMsg(message);
                    }
                });
            }
        }

    }

}
