package com.jichang.youcan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jichang.jichangprojectlibrary.ui.dialog.IShowDialog;
import com.jichang.jichangprojectlibrary.ui.dialog.impl.AbstractTemplateSelectedDialogImpl;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.DialogConstants;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Edit content activity
 */

public class EditActivity extends AbstractAppBaseActivity {

    private static final int REQUEST_SET_ALARM_KEY = 0x00000001;

    public static final String PRIORITY_KEY = "priority";

    public static final String EDIT_RESULT = "edit_result";

    private static final String TAG = "EditActivity";

    private EditText mEditContent;

    public EditText getEditContent() {
        return mEditContent;
    }

    public void setEditContent(EditText editContent) {
        this.mEditContent = editContent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_edit_content);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle("编辑事件");
        }
        mEditContent = (EditText) findViewById(R.id.ev_content);
        Intent intent = getIntent();
        final String contentFromContentViewActivity = intent.getStringExtra(ContentActivity.CONTENT_KEY);
        if (contentFromContentViewActivity != null) {
            mEditContent.setText(contentFromContentViewActivity);
            mEditContent.setSelection(contentFromContentViewActivity.length());
        }


    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_alarm:
                Intent alarms = new Intent(AlarmClock.ACTION_SET_ALARM);
                startActivityForResult(alarms, REQUEST_SET_ALARM_KEY);
                break;
            case R.id.done:
                IShowDialog iShowDialog = new AbstractTemplateSelectedDialogImpl() {
                    @Override
                    public void expansionCallBack(String data) {
                        activityReturn(data);
                    }
                };
                String chooses = DialogConstants.PRIORITY_MIN + DialogConstants.SPLIT_CHAR +
                        DialogConstants.PRIORITY_DEFAULT + DialogConstants.SPLIT_CHAR +
                        DialogConstants.PRIORITY_MAX + DialogConstants.SPLIT_CHAR;
                iShowDialog.showDialog(EditActivity.this, chooses);

            default:
                break;
        }
        return true;
    }

    /**
     * 编辑完成 返回
     *
     * @param data Data from user edits
     */
    private void activityReturn(String data) {
        final String editContent = mEditContent.getText().toString();
        int priority = 0;
        if (data.equals(DialogConstants.PRIORITY_DEFAULT)) {
            priority = 1;
        } else if (data.equals(DialogConstants.PRIORITY_MAX)) {
            priority = 2;
        }
        Intent intent = new Intent();
        intent.putExtra(EDIT_RESULT, editContent);
        intent.putExtra(PRIORITY_KEY, priority);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SET_ALARM_KEY:
                    final String time = data.getDataString();
                    LogUtils.i(TAG, "Alarm time is : " + time);
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_youcan_toolbar_edit_content, menu);
        return true;
    }


}
