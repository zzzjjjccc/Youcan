package com.jichang.youcan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class TabAdapter extends FragmentPagerAdapter {

    private final static String[] CONTACTS_CATEGORY = new String[] {"关注了谁", "被谁关注"};

    private List<Fragment> mFragments;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
        if (fragments == null) {
            throw new IllegalArgumentException("联系人分类不能为空");
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments.size() > position) {
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return CONTACTS_CATEGORY[0];
        } else if (position == 1) {
            return CONTACTS_CATEGORY[1];
        }
        return CONTACTS_CATEGORY[0];
    }
}
