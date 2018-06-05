package com.jichang.youcan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.adapter.ContentRecyclerAdapter;
import com.jichang.youcan.adapter.RecyclerViewDivider;
import com.jichang.youcan.db.NoteContentManager;
import com.jichang.youcan.db.model.Content;
import com.jichang.youcan.ui.OnItemClickListener;
import com.jichang.youcan.ui.ScrollDeleteRecyclerView;

import java.util.List;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Content View Activity
 */

public class ContentActivity extends AbstractAppBaseActivity implements ContentRecyclerAdapter.ContentViewActivityCallBackInterface {

    private static final String TAG = "ContentActivity";

    public static final int REQUEST_EDIT_CONTENT_ACTIVITY_CODE_WITHOUT_KEY = 0x00000001;

    public static final int REQUEST_EDIT_CONTENT_ACTIVITY_CODE_WITH_KEY = 0x00000001 << 1;

    public static final String CONTENT_KEY = "content_key";

    private String mUpdateTime;

    private List<Content> mContents;

    List<Content> mContentsFromSqlite;

    private ContentRecyclerAdapter mAdapter;

    private NoteContentManager mNoteContentManager;

    private ScrollDeleteRecyclerView mScrollDelRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_youcan_toolbar_recycler);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void initVariables() {
        Intent intent = getIntent();
        String date = intent.getStringExtra(MainActivity.INTENT_KEY_DATE);
        mNoteContentManager = new NoteContentManager(mDBHelper, date);
        mContents = mNoteContentManager.quaryNote();
        mAdapter = new ContentRecyclerAdapter(mContents, this);
    }

    @Override
    public void initViews() {
        mScrollDelRecyclerView = (ScrollDeleteRecyclerView) findViewById(R.id.recyler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mScrollDelRecyclerView.setLayoutManager(layoutManager);
        mScrollDelRecyclerView.addItemDecoration(new RecyclerViewDivider());
        mScrollDelRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onDoneClick(int position) {
                mAdapter.changeDone(mNoteContentManager, position);
                changeContents();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDeleteClick(int position) {
                mAdapter.deleteContent(mNoteContentManager, position);
                changeContents();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEditClick(int position) {
                mAdapter.startActivity(position);
            }
        });
        mScrollDelRecyclerView.setAdapter(mAdapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle("事件列表");
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "添加一条纪录", Snackbar.LENGTH_SHORT)
                        .setAction("添加", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startToEditContentActivity(null, null);
                            }
                        }).show();
            }
        });
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadData() {
        //loadImageIntoMainActivityBackGround();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_EDIT_CONTENT_ACTIVITY_CODE_WITHOUT_KEY:
                if (resultCode == RESULT_OK) {
                    final String resultContent = data.getStringExtra(EditActivity.EDIT_RESULT);
                    final int resultPriority = data.getIntExtra(EditActivity.PRIORITY_KEY, 0);
                    LogUtils.i(TAG, "RESULT_CONTENT and RESULT_PRIORITY: " + resultContent + " " + resultPriority);
                    mNoteContentManager.setOneContent(
                            resultContent,
                            resultPriority,
                            0);
                    changeContents();
                    mAdapter.notifyDataSetChanged();
                    mScrollDelRecyclerView.scrollToPosition(mContents.size() - 1);
                }
                break;
            case REQUEST_EDIT_CONTENT_ACTIVITY_CODE_WITH_KEY:
                if (resultCode == RESULT_OK) {
                    final String RESULT_CONTENT = data.getStringExtra(EditActivity.EDIT_RESULT);
                    LogUtils.i(TAG, "RESULT_CONTENT : " + RESULT_CONTENT);
                    mNoteContentManager.updateNote(mUpdateTime, RESULT_CONTENT);
                    changeContents();
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Change times of adapter, can't just change the reference.
     * If do that, it'll not work.
     */
    public void changeContents() {
        // 先清空，不然会累计。
        mContents.clear();
        mContentsFromSqlite = mNoteContentManager.quaryNote();
        for (Content content : mContentsFromSqlite) {
            mContents.add(content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_youcan_fragment, menu);
        return true;
    }

    /**
     * 其他Activity通过调用这个方法，传递正确的参数，启动这个Activity.
     *
     * @param context
     * @param start_key 传数据，取数据所用的key.
     * @param date
     */
    public static void actionStart(Context context, String start_key, String date) {

        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(start_key, date);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


    /**
     * 通过不同方式跳进EditContentActivity,没有参数代表新建，有参数代表修改。
     *
     * @param content
     */
    @Override
    public void startToEditContentActivity(String time, String content) {

        Intent intent = new Intent(ContentActivity.this, EditActivity.class);

        if (intent.resolveActivity(getPackageManager()) != null) {
            LogUtils.i(TAG, "startToEditContentActivity");
            if (time != null && content != null) {
                this.mUpdateTime = time;
                intent.putExtra(CONTENT_KEY, content);
                startActivityForResult(intent, REQUEST_EDIT_CONTENT_ACTIVITY_CODE_WITH_KEY);
            } else {
                startActivityForResult(intent, REQUEST_EDIT_CONTENT_ACTIVITY_CODE_WITHOUT_KEY);
            }
        }

    }
}
