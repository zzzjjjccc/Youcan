package com.jichang.youcan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.contract.IBaseContract;
import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.activity.SubscribePersonActivity;
import com.jichang.youcan.adapter.DateRecyclerAdapter;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.db.model.Date;
import com.jichang.youcan.db.model.User;
import com.jichang.youcan.fragment.callback.IMainFragmentCallback;
import com.jichang.youcan.fragment.contract.IMainFragmentContract;
import com.jichang.youcan.fragment.contract.impl.MainFragmentPresenter;
import com.jichang.youcan.util.ImageUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/20
 * email: 2218982471@qq.com
 * describ:
 */

public class MainFragment extends Fragment
        implements View.OnClickListener, IBaseContract, IMainFragmentContract.View {

    private View mRootView;

    private ImageView mImageView;

    private RecyclerView mRecyclerView;

    private FloatingActionButton mFab;

    private Toolbar mToolbar;

    private DateRecyclerAdapter mAdapter;

    private MainFragmentPresenter mPresenter;

    private IMainFragmentCallback mCallback;

    private List<Date> mDates;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.item_youcan_toobar_recycler_without_scroll, container, false);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_fragment_delete) {
            mPresenter.delDate(getActivity(), mAdapter.getDelDates());
        } else if (i == R.id.menu_fragment_share) {
            mPresenter.share(getActivity(), mAdapter.getDelDates());
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_youcan_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    public void setFragmentCallback(IMainFragmentCallback callback) {
        mCallback = callback;
    }

    @Override
    public void initVariables() {
        mPresenter = new MainFragmentPresenter();
        mPresenter.attachView(this);
        mPresenter.setDateManager(mCallback.getDbHelper());
        mDates = new ArrayList<>();
    }

    @Override
    public void initViews() {
        mRecyclerView = mRootView.findViewById(R.id.recyler_view);
        mFab = mRootView.findViewById(R.id.fab);
        mImageView = mRootView.findViewById(R.id.img_background);
        mToolbar = mRootView.findViewById(R.id.toolbar);
    }

    @Override
    public void initEvents() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "添加今日纪录", Snackbar.LENGTH_SHORT)
                        .setAction("添加", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.addDate(getActivity());
                            }
                        }).show();
            }
        });
    }

    @Override
    public void loadData() {
        mCallback.setToolbar(mToolbar, "编辑");
        mImageView.setImageResource(R.drawable.main);
        String bingPic = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.BING_PICTURE_KEY, "");
        if (!"".equals(bingPic)) {
            Glide.with(this).load(bingPic).into(mImageView);
        } else {
            ImageUtils.loadImageBingPicture();
        }
        mPresenter.getDateInfoFromLocal();
    }

    @Override
    public void refreshAdapter() {
        mDates.clear();
        mDates.addAll(mPresenter.queryDates());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUsers(final List<User> users, final String date) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_DATE, date);
                bundle.putSerializable(Constants.INTENT_USER, (Serializable) users);
                IntentUtils.startActivity(getActivity(), SubscribePersonActivity.class, bundle);
            }
        });
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
    public void showDate(List<Date> dates) {
        mDates.addAll(dates);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DateRecyclerAdapter(mDates);
        mRecyclerView.setAdapter(mAdapter);
    }


}
