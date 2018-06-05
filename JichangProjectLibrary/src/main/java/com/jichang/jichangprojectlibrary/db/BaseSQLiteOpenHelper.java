package com.jichang.jichangprojectlibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = BaseSQLiteOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "default.db";

    private final List<String> tables;

    public BaseSQLiteOpenHelper(Context context, List<String> table) {
        this(context, DATABASE_NAME, table);
    }

    public BaseSQLiteOpenHelper(Context context, String databaseName, List<String> table) {
        this(context, databaseName, DATABASE_VERSION, table);
    }

    public BaseSQLiteOpenHelper(Context context, String databaseName, int databaseVersion, List<String> table) {
        super(context, databaseName, null, databaseVersion);
        this.tables = table;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String table : tables) {
            db.execSQL(table);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Todo sqlite override update
    }

}
