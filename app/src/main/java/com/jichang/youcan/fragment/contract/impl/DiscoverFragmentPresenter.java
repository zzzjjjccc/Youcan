package com.jichang.youcan.fragment.contract.impl;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.presenter.AbstractPresenter;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.fragment.contract.IDiscoverFragmentContract;
import com.jichang.youcan.data.bean.res.DiscoverInfo;
import com.jichang.youcan.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class DiscoverFragmentPresenter extends AbstractPresenter<IDiscoverFragmentContract.View>
        implements IDiscoverFragmentContract.Presenter {

    @Override
    public void getUserEventFromLocal() {
        List<DiscoverInfo> discoverInfos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DiscoverInfo discoverInfo = new DiscoverInfo();
            discoverInfo.setName("jiChang" + i);
            discoverInfos.add(discoverInfo);
        }
        view.showEvent(discoverInfos);
    }

    @Override
    public void getUserEventFromNet() {
        String phone = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
        YoucanDataManager.getInstance().discover(phone, new IYoucanDataResponse<String>() {
            @Override
            public void onSuccess(String s) {
                view.showEvent(ConvertUtils.convertDiscover(s));
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
