package com.example.deng.experimenteight.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by deng on 2016/11/16.
 */
public class MySQListOpenHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "my_SQLite_database";
    public static final String TABLE_NAME = "birthday";

    public MySQListOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE if not exists " +
                              TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT, birth TEXT, gift TEXT)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
