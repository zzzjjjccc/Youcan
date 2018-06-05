package com.jichang.youcan.fragment.contract.impl;

import android.content.Context;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.db.BaseSQLiteOpenHelper;
import com.jichang.jichangprojectlibrary.presenter.AbstractPresenter;
import com.jichang.jichangprojectlibrary.ui.dialog.IShowDialog;
import com.jichang.jichangprojectlibrary.ui.dialog.impl.AbstractTemplateDatePickerDialogImpl;
import com.jichang.jichangprojectlibrary.ui.dialog.impl.AbstractTemplateShowCommonDialogImpl;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.data.YoucanDataManager;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.db.NoteContentManager;
import com.jichang.youcan.db.NoteDateManager;
import com.jichang.youcan.db.model.Content;
import com.jichang.youcan.db.model.Date;
import com.jichang.youcan.fragment.contract.IMainFragmentContract;
import com.jichang.youcan.util.ConvertUtils;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/21
 * email: 2218982471@qq.com
 * describ:
 */

public class MainFragmentPresenter extends AbstractPresenter<IMainFragmentContract.View>
        implements IMainFragmentContract.Presenter {

    private NoteDateManager mDateManager;

    private NoteContentManager mContentManager;

    BaseSQLiteOpenHelper mHelper;

    public void setDateManager(BaseSQLiteOpenHelper helper) {
        mHelper = helper;
        this.mDateManager = new NoteDateManager(helper);
    }

    @Override
    public void getDateInfoFromLocal() {
        view.showDate(queryDates());
    }

    @Override
    public void getDateInfoFromNet() {

    }

    public void addDate(Context context) {
        if (mDateManager.setOneDate(null)) {
            //刷新UI.
            view.refreshAdapter();
        } else {
            IShowDialog iShowDialog = new AbstractTemplateDatePickerDialogImpl() {
                @Override
                public void expansionCallBack(String data) {
                    mDateManager.setOneDate(data);
                    //刷新UI.
                    view.refreshAdapter();
                }
            };
            iShowDialog.showDialog(context, "请选择要将来计划日");
        }
    }

    public void delDate(Context context, final List<Date> dates) {
        int size = dates.size();
        IShowDialog iShowDialog = new AbstractTemplateShowCommonDialogImpl() {
            @Override
            public void expansionCallBack(String data) {
                for (Date d : dates) {
                    mDateManager.deleteDate(d);
                }
                dates.clear();
                view.refreshAdapter();
            }
        };
        iShowDialog.showDialog(context, "确定删除选中的" + size + "天记录");

    }

    public void share(Context context, final List<Date> dates) {
        if (dates != null && dates.size() > 0) {
            postContent(context, dates.get(0).getDate());
        }
    }

    private void postContent(final Context context, final String date) {
        mContentManager = new NoteContentManager(mHelper, date);
        List<Content> contents = mContentManager.quaryNote();
        final StringBuffer content = new StringBuffer();
        for (Content c : contents) {
            content.append(c.getContent());
            content.append(Constants.DIVIDER_PART);
        }
        Content c = new Content();
        c.setDate(date);
        c.setContent(content.toString());

        final String phone = (String) SpUtils.get(BaseApplication.getInstance(), SpConstants.USER_PHONE, "");
        YoucanDataManager.getInstance().addContent(phone, c, new IYoucanDataResponse<String>() {
            @Override
            public void onSuccess(String s) {
                if (context.getString(R.string.network_return_success).equals(s)) {
                    chooseUsers(phone, date);
                }
            }

            @Override
            public void onError(String message) {
                view.showErrorMsg(message);
            }
        });
    }

    private void chooseUsers(final String phone, final String date) {
        YoucanDataManager.getInstance().getFans(phone, new IYoucanDataResponse<String>() {
            @Override
            public void onSuccess(String s) {
                view.showUsers(ConvertUtils.convert(s), date);
            }

            @Override
            public void onError(String message) {
                view.showErrorMsg(message);
            }
        });
    }

    /**
     * Change mDates of adapter, can't just change the reference.
     * If do that, it'll not work.
     * return data and then add it all.
     */
    public List<Date> queryDates() {
        return mDateManager.quaryDate();
    }

}
