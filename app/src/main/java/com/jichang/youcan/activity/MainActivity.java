package com.jichang.youcan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.db.BaseSQLiteOpenHelper;
import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.BuildConstants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.fragment.ContactsFragment;
import com.jichang.youcan.fragment.DiscoverFragment;
import com.jichang.youcan.fragment.MainFragment;
import com.jichang.youcan.fragment.MeFragment;
import com.jichang.youcan.fragment.callback.IContactsFragmentCallback;
import com.jichang.youcan.fragment.callback.IDiscoverFragmentCallback;
import com.jichang.youcan.fragment.callback.IMainFragmentCallback;
import com.jichang.youcan.fragment.callback.IMeFragmentCallback;
import com.jichang.youcan.service.NoticeService;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ: MainActivity
 */

public class MainActivity extends AbstractAppBaseActivity
        implements IMainFragmentCallback,
        IContactsFragmentCallback,
        IDiscoverFragmentCallback,
        IMeFragmentCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String INTENT_KEY_DATE = "key_date";

    private DrawerLayout mDrawerLayout;

    private ImageView mHeaderImage;

    private TextView mHeaderText;

    private NavigationView mNavigationView;

    private RadioGroup mRadioGroup;

    private MainFragment mMainFragment;

    private ContactsFragment mContactsFragment;

    private DiscoverFragment mDiscoverFragment;

    private MeFragment mMeFragment;

    private long mIsQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_main);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void initVariables() {
        IntentUtils.startService(MainActivity.this, NoticeService.class);
    }


    @Override
    public void initViews() {
        LogUtils.i(TAG, "initViews");
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = mNavigationView.inflateHeaderView(R.layout.item_youcan_nav_header);
        mHeaderText = view.findViewById(R.id.header_text);
        mHeaderImage = view.findViewById(R.id.header_image);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_activity_main);
    }

    @Override
    public void initEvents() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.quit_app:
                        AppManager.getAppManager().finishAllActivity();
                        SpUtils.put(BaseApplication.getInstance(), SpConstants.LOGIN, true);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_search:
                        IntentUtils.startActivity(MainActivity.this, NearByActivity.class);
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                loadFragment(checkedId);
            }
        });
    }

    @Override
    public void loadData() {
        mHeaderImage.setImageResource(R.drawable.header);
        setTextTomHeaderText(BuildConstants.FAITH);

        loadFragment(R.id.rb_activity_main);
    }

    public void setTextTomHeaderText(String text) {
        mHeaderText.setText(text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.menu_youcan_add:
                IntentUtils.startActivity(MainActivity.this, SubscribePersonActivity.class);
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_youcan_fragment, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mIsQuit > 2000) {
            ToastUtils.showToast(BaseApplication.getInstance(), "再按一次退出运用");
            mIsQuit = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().appExit(MainActivity.this);
        }

    }

    @Override
    public BaseSQLiteOpenHelper getDbHelper() {
        return mDBHelper;
    }

    @Override
    public void setToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);
            actionBar.setTitle(title);
        }
    }

    private void loadFragment(int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        if (checkedId == R.id.rb_activity_main) {
            if (mMainFragment == null) {
                mMainFragment = new MainFragment();
                mMainFragment.setFragmentCallback(this);
                transaction.add(R.id.fl_main_fragment_container, mMainFragment);
            } else {
                transaction.show(mMainFragment);
            }
        } else if (checkedId == R.id.rb_activity_contacts) {
            if (mContactsFragment == null) {
                mContactsFragment = new ContactsFragment();
                mContactsFragment.setFragmentCallback(this);
                transaction.add(R.id.fl_main_fragment_container, mContactsFragment);
            } else {
                transaction.show(mContactsFragment);
            }
        } else if (checkedId == R.id.rb_activity_discover) {
            if (mDiscoverFragment == null) {
                mDiscoverFragment = new DiscoverFragment();
                mDiscoverFragment.setFragmentCallback(this);
                transaction.add(R.id.fl_main_fragment_container, mDiscoverFragment);
            } else {
                transaction.show(mDiscoverFragment);
            }
        } else if (checkedId == R.id.rb_activity_me) {
            if (mMeFragment == null) {
                mMeFragment = new MeFragment();
                mMeFragment.setFragmentCallback(this);
                transaction.add(R.id.fl_main_fragment_container, mMeFragment);
            } else {
                transaction.show(mMeFragment);
            }
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mMainFragment != null) {
            transaction.hide(mMainFragment);
        }
        if (mContactsFragment != null) {
            transaction.hide(mContactsFragment);
        }
        if (mDiscoverFragment != null) {
            transaction.hide(mDiscoverFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "ondestroy");
    }
}
