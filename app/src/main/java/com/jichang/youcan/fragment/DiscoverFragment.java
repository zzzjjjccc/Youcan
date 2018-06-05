package com.jichang.youcan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.contract.IBaseContract;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.adapter.DiscoverRecyclerAdapter;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.bean.res.DiscoverInfo;
import com.jichang.youcan.fragment.callback.IDiscoverFragmentCallback;
import com.jichang.youcan.fragment.contract.IDiscoverFragmentContract;
import com.jichang.youcan.fragment.contract.impl.DiscoverFragmentPresenter;
import com.jichang.youcan.util.ImageUtils;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/20
 * email: 2218982471@qq.com
 * describ:
 */

public class DiscoverFragment extends Fragment
        implements View.OnClickListener, IBaseContract, IDiscoverFragmentContract.View {

    private View mRootView;

    private Toolbar mToolbar;

    private ImageView mImageView;

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefresh;

    private DiscoverFragmentPresenter mPresenter;

    private IDiscoverFragmentCallback mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_youcan_discover, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public void setFragmentCallback(IDiscoverFragmentCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initVariables() {
        mPresenter = new DiscoverFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        mToolbar = mRootView.findViewById(R.id.tb_discover_bar);
        mImageView = mRootView.findViewById(R.id.iv_discover_bar_background);
        mSwipeRefresh = mRootView.findViewById(R.id.swipe_refresh);
        mRecyclerView = mRootView.findViewById(R.id.rv_discover_recycler_view);
    }

    @Override
    public void initEvents() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ImageUtils.loadImageBingPicture();
                mPresenter.getUserEventFromNet();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadData() {
        mCallback.setToolbar(mToolbar, "发现");
        mImageView.setImageResource(R.drawable.main);
        String bingPic = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.BING_PICTURE_KEY, "");
        if (!"".equals(bingPic)) {
            Glide.with(this).load(bingPic).into(mImageView);
        } else {
            ImageUtils.loadImageBingPicture();
        }
        mPresenter.getUserEventFromNet();
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showErrorView(String msg) {

    }

    @Override
    public void showEvent(final List<DiscoverInfo> discoverInfos) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(layoutManager);
                DiscoverRecyclerAdapter adapter = new DiscoverRecyclerAdapter(discoverInfos);
                mRecyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void refreshAdapter() {

    }
}
