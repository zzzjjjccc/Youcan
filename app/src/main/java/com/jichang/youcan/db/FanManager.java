package com.jichang.youcan.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.db.model.Fan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/26
 * email: 2218982471@qq.com
 * describ:
 */

public class FanManager {

    private static final String TAG = FanManager.class.getSimpleName();

    private SQLiteDatabase db;

    public FanManager(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertFan(Fan fan) {

        if (isFanExists(fan.getPhone())) {
            final String sqlInsert =
                    "INSERT INTO " + NoteContract.FeedEntry.TABLE_NAME_FAN +
                            " VALUES ( ?, ?, ?)";

            try {
                db.execSQL(sqlInsert, new String[]{null, String.valueOf(fan.getId()), fan.getPhone(), fan.getFan()});
                LogUtils.i(TAG, "Insert succeed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果传入的时间在数据库中已经有了的话返回true, 反之返回false
     *
     * @return if date exist
     */
    private boolean isFanExists(String phone) {

        List<Fan> fans = quaryFan();
        for (int i = 0; i < fans.size(); i++) {
            Fan fan = fans.get(i);
            if (phone.equals(fan.getPhone())) {
                LogUtils.i(TAG, "The Fan is exists.");
                return true;
            }
        }
        return false;
    }

    public List<Fan> quaryFan() {
        List<Fan> fans = new ArrayList<>();
        final String sqlQuery =
                "SELECT * FROM " + NoteContract.FeedEntry.TABLE_NAME_FAN;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sqlQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry._ID));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_PHONE));
                    String fan = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_FAN));
                    Fan fanC = new Fan(id, phone, fan);
                    fans.add(fanC);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fans;
    }
}
