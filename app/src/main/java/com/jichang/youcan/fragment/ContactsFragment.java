package com.jichang.youcan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jichang.jichangprojectlibrary.contract.IBaseContract;
import com.jichang.youcan.R;
import com.jichang.youcan.adapter.TabAdapter;
import com.jichang.youcan.fragment.callback.IContactsFragmentCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/20
 * email: 2218982471@qq.com
 * describ:
 */

public class ContactsFragment extends Fragment
        implements View.OnClickListener, IBaseContract {

    private static final int PAGE_COUNT = 2;

    private View mRootView;

    private Toolbar mToolbar;

    private IContactsFragmentCallback mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_youcan_contacts, container, false);
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

    public void setFragmentCallback(IContactsFragmentCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        mToolbar = mRootView.findViewById(R.id.toolbar);
        ViewPager viewPager = mRootView.findViewById(R.id.vp_contacts_pager);
        TabLayout tabLayout = mRootView.findViewById(R.id.tl_contacts_tab);
        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), getTabData());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(PAGE_COUNT);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadData() {
        mCallback.setToolbar(mToolbar, "联系人");
    }

    private List<Fragment> getTabData() {
        List<Fragment> fragments = new ArrayList<>();
        SubscribeFragment fragment1 = new SubscribeFragment();
        fragment1.subscribe = true;
        SubscribeFragment fragment2 = new SubscribeFragment();
        fragment2.subscribe = false;
        fragments.add(fragment1);
        fragments.add(fragment2);
        return fragments;
    }
}
