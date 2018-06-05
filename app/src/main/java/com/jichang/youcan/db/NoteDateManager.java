package com.jichang.youcan.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jichang.jichangprojectlibrary.db.BaseSQLiteOpenHelper;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.db.model.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class NoteDateManager {

    private static final String TAG = "NoteDateManager";

    private SQLiteDatabase db;

    public NoteDateManager(BaseSQLiteOpenHelper helper) {
        db = helper.getReadableDatabase();
    }

    public List<Date> quaryDate() {
        List<Date> dates = new ArrayList<>();
        final String sqlQuery =
                "SELECT * FROM " + NoteContract.FeedEntry.TABLE_NAME_DATE +
                        " ORDER BY " + NoteContract.FeedEntry.COLUMN_DATE;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sqlQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry._ID));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_DATE));
                    Date tmpDate = new Date(id, date);
                    LogUtils.i(TAG, "the quaryTime is : " + tmpDate.getDate());
                    LogUtils.i(TAG, "the quaryId is : " + tmpDate.getId());
                    dates.add(tmpDate);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dates;
    }

    /**
     * Insert one data into the sqlite according to given date.
     *
     * @param date Date
     * @return  if insert success
     */
    private boolean insertDate(Date date) {
        final String insertDate = date.getDate();
        final String sqlInsert =
                "INSERT INTO " + NoteContract.FeedEntry.TABLE_NAME_DATE +
                        " VALUES ( ?, ? )";
        try {
            LogUtils.i(TAG, "The insertDate is : " + insertDate);
            db.execSQL(sqlInsert, new String[]{null, insertDate});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.i(TAG, "Insert date failed");
            return false;
        }
    }

    /**
     * What date for delete, first delete the note, and then delete the date which have been given.
     *
     * @param date Date
     * @return if delete success
     */
    public boolean deleteDate(Date date) {
        final String dateForDelete = date.getDate();
        final String sqlDeleteContent =
                "DELETE FROM " + NoteContract.FeedEntry.TABLE_NAME_CONTENT +
                        " WHERE " + NoteContract.FeedEntry.COLUMN_DATE +
                        " = ?";
        final String sqlDeleteDate =
                "DELETE FROM " + NoteContract.FeedEntry.TABLE_NAME_DATE +
                        " WHERE " + NoteContract.FeedEntry.COLUMN_DATE +
                        " = ?";
        try {
            db.execSQL(sqlDeleteContent, new String[]{dateForDelete});
            db.execSQL(sqlDeleteDate, new String[]{dateForDelete});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.i(TAG, "delete faild");
            return false;
        }
    }

    /**
     * 新建一天纪录，如果数据库中已经有了则返回false, 反之返回true
     *
     * @param date Date
     * @return if set success
     */
    public boolean setOneDate(String date) {

        final String willBeCreateDate;
        if (date == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            willBeCreateDate = format.format(new java.util.Date());
        } else {
            willBeCreateDate = date;
        }

        if (!isDateExists(willBeCreateDate)) {
            Date tmpDate = new Date(0, willBeCreateDate);
            if (insertDate(tmpDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果传入的时间在数据库中已经有了的话返回true, 反之返回false
     *
     * @param time Time
     * @return if date exist
     */
    private boolean isDateExists(String time) {

        List<Date> dates = quaryDate();
        for (int i = 0; i < dates.size(); i++) {
            Date ti = dates.get(i);
            if (time.equals(ti.getDate())) {
                LogUtils.i(TAG, "The Date is exists.");
                return true;
            }
        }
        return false;
    }
}
