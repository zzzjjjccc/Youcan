package com.jichang.youcan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jichang.jichangprojectlibrary.contract.IBaseContract;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.adapter.SubscribeRecyclerAdapter;
import com.jichang.youcan.db.model.User;
import com.jichang.youcan.fragment.contract.IContactsFragmentContract;
import com.jichang.youcan.fragment.contract.impl.ContactsFragmentPresenter;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class SubscribeFragment extends Fragment implements IBaseContract, IContactsFragmentContract.View {

    private View mRootView;

    private RecyclerView mRecyclerView;

    private ContactsFragmentPresenter mPresenter;

    public boolean subscribe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_youcan_subscribe, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVariables();
        initViews();
        initEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void initVariables() {
        mPresenter = new ContactsFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        mRecyclerView = mRootView.findViewById(R.id.rv_subscribe);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadData() {
        mPresenter.getUserInfoFromNet(subscribe);
    }

    @Override
    public void showErrorMsg(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(getActivity(), msg);
            }
        });
    }

    @Override
    public void showErrorView(String msg) {

    }

    @Override
    public void showUser(final List<User> users) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(layoutManager);
                SubscribeRecyclerAdapter adapter = new SubscribeRecyclerAdapter(users);
                mRecyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void refreshAdapter() {

    }

}
