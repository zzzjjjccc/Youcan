package com.jichang.jichangprojectlibrary.presenter;

/**
 * @author jichang
 * @date 2018/1/19
 * email: 2218982471@qq.com
 * describ:
 */

public abstract class AbstractPresenter<V extends BaseView> implements BasePresenter<V> {

    protected V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (null != view) {
            view = null;
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        detachView();
    }
}
