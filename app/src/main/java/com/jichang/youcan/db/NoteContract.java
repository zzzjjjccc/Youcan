package com.jichang.youcan.db;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public final class NoteContract {

    public static final String DATABASE_NAME = "note.db";

    public static final int DATABASE_VERSION = 1;

    private static List<String> mCreateTables;

    static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME_DATE = "date";
        static final String TABLE_NAME_CONTENT = "content";
        static final String TABLE_NAME_NOTICE = "notice";
        static final String TABLE_NAME_FAN = "fan";
        static final String TABLE_NAME_IDOL = "idol";
        static final String TABLE_NAME_OPEN = "open";
        static final String TABLE_NAME_USER = "user";

        static final String COLUMN_DATE = "date";
        static final String COLUMN_TIME = "time";
        static final String COLUMN_CONTENT = "content";
        static final String COLUMN_PRIORITY = "priority";
        static final String COLUMN_DONE = "done";
        static final String COLUMN_NOTICE_TIME = "notice";
        static final String COLUMN_FAN = "fan";
        static final String COLUMN_IDOL = "idol";
        static final String COLUMN_OPEN = "open";
        static final String COLUMN_PHONE = "phone";
        static final String COLUMN_PASSWORD = "password";
        static final String COLUMN_SCHOOL = "school";
        static final String COLUMN_MAJOR = "major";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_SEX = "sex";
    }

    private static final String CREATE_TABLE_DATE =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_DATE + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_DATE + " TEXT " + "UNIQUE" +
                    " ) ";

    private static final String CREATE_TABLE_CONTENT =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_CONTENT + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_DATE + " TEXT," +
                    FeedEntry.COLUMN_TIME + " TEXT," +
                    FeedEntry.COLUMN_CONTENT + " TEXT," +
                    FeedEntry.COLUMN_PRIORITY + " INTEGER," +
                    FeedEntry.COLUMN_DONE + " INTEGER" +
                    " ) ";

    private static final String CREATE_TABLE_NOTICE =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_NOTICE + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_DATE + " TEXT," +
                    FeedEntry.COLUMN_TIME + " TEXT," +
                    FeedEntry.COLUMN_NOTICE_TIME + " TEXT" +
                    " ) ";

    private static final String CREATE_TABLE_FAN =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_IDOL + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_PHONE + " TEXT " + "UNIQUE," +
                    FeedEntry.COLUMN_FAN + " TEXT " + "UNIQUE" +
                    " ) ";

    private static final String CREATE_TABLE_IDOL =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_FAN + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_PHONE + " TEXT " + "UNIQUE," +
                    FeedEntry.COLUMN_IDOL + " TEXT " + "UNIQUE" +
                    " ) ";

    private static final String CREATE_TABLE_OPEN =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_OPEN + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_DATE + " TEXT " + "UNIQUE" +
                    FeedEntry.COLUMN_OPEN + " TEXT " + "UNIQUE" +
                    " ) ";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_USER + " ( " +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_PHONE + " TEXT " + "UNIQUE" +
                    FeedEntry.COLUMN_PASSWORD + " TEXT " + "UNIQUE" +
                    FeedEntry.COLUMN_SCHOOL + " TEXT " + "UNIQUE" +
                    FeedEntry.COLUMN_MAJOR + " TEXT " + "UNIQUE" +
                    FeedEntry.COLUMN_NAME + " TEXT " + "UNIQUE" +
                    FeedEntry.COLUMN_SEX + " TEXT " + "UNIQUE" +
                    " ) ";


    /**
     * Create table
     *
     * @return A list of string for create table
     */
    public static List<String> getCreateTables() {
        if (mCreateTables == null) {
            mCreateTables = new ArrayList<>();
        } else {
            mCreateTables.clear();
        }
        mCreateTables.add(CREATE_TABLE_DATE);
        mCreateTables.add(CREATE_TABLE_CONTENT);
        mCreateTables.add(CREATE_TABLE_NOTICE);
        mCreateTables.add(CREATE_TABLE_FAN);
        mCreateTables.add(CREATE_TABLE_IDOL);
        mCreateTables.add(CREATE_TABLE_OPEN);
        mCreateTables.add(CREATE_TABLE_USER);
        return mCreateTables;
    }
}
