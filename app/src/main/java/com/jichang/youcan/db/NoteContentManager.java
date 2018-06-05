package com.jichang.youcan.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jichang.jichangprojectlibrary.db.BaseSQLiteOpenHelper;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.db.model.Content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class NoteContentManager {

    private static final String TAG = "NoteContentManager";

    private SQLiteDatabase db;

    private String mDate;

    public NoteContentManager(BaseSQLiteOpenHelper helper, String date) {
        db = helper.getReadableDatabase();
        this.mDate = date;
    }

    /**
     * 根据给定的日期，查询当天所有的事件。
     * @return
     */
    public List<Content> quaryNote() {
        List<Content> contents = new ArrayList<>();
        Cursor cursor = null;
        final String sqlQuery =
                "SELECT *" +
                        " FROM " + NoteContract.FeedEntry.TABLE_NAME_CONTENT +
                        " WHERE " + NoteContract.FeedEntry.COLUMN_DATE + " = ?";
        final String sqlQueryParm = mDate;
        try {
            cursor = db.rawQuery(sqlQuery, new String[]{sqlQueryParm});
            if (cursor.moveToFirst()) {
                do {
                    int contentId =
                            cursor.getInt(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry._ID));
                    String contentContent =
                            cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_CONTENT));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_TIME));
                    int priority = cursor.getInt(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_PRIORITY));
                    int done = cursor.getInt(cursor.getColumnIndexOrThrow(NoteContract.FeedEntry.COLUMN_DONE));
                    Content content = new Content(contentId, mDate, time, contentContent, priority, done);
                    LogUtils.i(TAG, "Content's id is : " + content.getId() +
                            "Content's time is : " + content.getTime() +
                            "Content's content is : " + content.getContent() +
                            "Content's priority is : " + priority +
                            "Content's done is : " + done);
                    contents.add(content);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contents;
    }

    public boolean insertNote(Content content) {
        final String contentTime = content.getTime();
        final String contentContent = content.getContent();
        final int priority = content.getPriority();
        final int done = content.getDone();
        final String sqlInsert =
                "INSERT INTO " + NoteContract.FeedEntry.TABLE_NAME_CONTENT +
                        " VALUES ( ?, ?, ?, ?, ?, ? )";

        try {
            db.execSQL(sqlInsert, new String[]{null, mDate, contentTime, contentContent, String.valueOf(priority), String.valueOf(done)});
            LogUtils.i(TAG, "Insert successed");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param content
     * @return
     */
    public boolean deleteNote(Content content) {
        final int id = content.getId();
        final String sqlDelete =
                " DELETE FROM " + NoteContract.FeedEntry.TABLE_NAME_CONTENT +
                        " WHERE " + NoteContract.FeedEntry._ID +
                        " = ?";
        try {
            db.execSQL(sqlDelete, new String[]{String.valueOf(id)});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 根据具体的日期和时间更新数据库
     *
     * @param time
     * @param content
     * @return
     */
    public boolean updateNote(String time, String content) {

        final String sqlUpdate =
                "UPDATE " + NoteContract.FeedEntry.TABLE_NAME_CONTENT +
                        " SET " + NoteContract.FeedEntry.COLUMN_CONTENT + " = '" +
                        content + "'" + " WHERE " + NoteContract.FeedEntry.COLUMN_DATE +
                        " = ? AND " + NoteContract.FeedEntry.COLUMN_TIME + " = ?";
        try {
            db.execSQL(sqlUpdate, new String[]{mDate, time});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 根据给定的日期和时间 对content中的done字段进行更新
     *
     * @param time
     * @param done
     * @return
     */
    public boolean updateNote(String time, int done) {
        final String sqlUpdate =
                " UPDATE " + NoteContract.FeedEntry.TABLE_NAME_CONTENT +
                        " SET " + NoteContract.FeedEntry.COLUMN_DONE +
                        " = ? " + " WHERE " + NoteContract.FeedEntry.COLUMN_DATE +
                        " = ? AND " + NoteContract.FeedEntry.COLUMN_TIME +
                        " = ?";
        try {
            db.execSQL(sqlUpdate, new String[]{String.valueOf(done), mDate, time});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 通过给定的一条事件，在sqlite中创建一条记录。
     * 时间为当前时间
     *
     * @param content  创建note的内容
     * @param priority note的优先级
     * @param done     note是否完成
     * @return
     */
    public boolean setOneContent(String content, int priority, int done) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        final String time = format.format(new Date());
        Content ct = new Content(0, mDate, time, content, priority, done);
        if (insertNote(ct)) {
            return true;
        }
        return false;
    }

}
