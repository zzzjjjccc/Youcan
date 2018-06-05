package com.jichang.youcan.activity.contract.impl;

import com.jichang.jichangprojectlibrary.presenter.AbstractPresenter;
import com.jichang.youcan.activity.contract.IAddPersonActivityContract;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.db.model.User;
import com.jichang.youcan.util.ConvertUtils;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/26
 * email: 2218982471@qq.com
 * describ:
 */

public class AddPersonActivityPresenter extends AbstractPresenter<IAddPersonActivityContract.View>
        implements IAddPersonActivityContract.Presenter {


    @Override
    public void getPersonFromNet() {
        YoucanDataManager.getInstance().getUsers(new IYoucanDataResponse<String>() {
            @Override
            public void onSuccess(String s) {
                if (s.contains(Constants.DIVIDER_PART)) {
                    view.showPerson(ConvertUtils.convert(s));
                }
            }

            @Override
            public void onError(String message) {

                // Request net with syn, and sometime activity be finished, if this, view will be null
                if (view != null) {
                    view.showErrorView(message);
                }

            }
        });
    }

    @Override
    public void share(String phone, String date, List<User> users) {
        StringBuilder s = new StringBuilder();
        for (User u : users) {
            s.append(u.getPhone());
            s.append(Constants.DIVIDER_PART);
        }
        share(phone, date, s.toString());
    }

    private void share(String phone, String date,  String s) {
        YoucanDataManager.getInstance().share(phone, date, s, new IYoucanDataResponse<String>() {
            @Override
            public void onSuccess(String s) {
                if (Constants.NET_RETURN_SUCCESS.equals(s)) {
                    view.shareDone();
                }
            }

            @Override
            public void onError(String message) {

                // Request net with syn, and sometime activity be finished, if this, view will be null
                if (view != null) {
                    view.showErrorView(message);
                }

            }
        });

    }

}
