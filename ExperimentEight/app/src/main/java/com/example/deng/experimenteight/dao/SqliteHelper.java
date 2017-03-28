package com.example.deng.experimenteight.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.deng.experimenteight.entity.BirthInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by deng on 2016/11/16.
 */
public class SqliteHelper {
    private SQLiteDatabase db;
    private String table;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BIRTH = "birth";
    private static final String GIFT = "gift";

    public SqliteHelper(MySQListOpenHelper SQListOpenHelper) {
        db = SQListOpenHelper.getWritableDatabase();
        table = MySQListOpenHelper.TABLE_NAME;
    }

    public long insert(BirthInfo birthInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, birthInfo.getName());
        contentValues.put(BIRTH, birthInfo.getBirth());
        contentValues.put(GIFT, birthInfo.getGift());
        return db.insert(table, null, contentValues);
    }

    public int delete(long id) {
        return db.delete(table, ID + "=" + id, null);
    }

    public List<BirthInfo> queryAllData() {
        Cursor cursor = db.query(table, new String[] {ID, NAME, BIRTH, GIFT}, null, null, null, null, null);
        return convertToBirthInfo(cursor);
    }

    public boolean queryNameRepeat(String name) {
        Cursor cursor = db.query(table, null, "name = ?", new String[] {name},
                        null, null, null, null);
        return cursor.getCount() > 0 ? true : false;
    }

    public int update(BirthInfo birthinfo) {
        ContentValues updateValue = new ContentValues();
        updateValue.put(BIRTH, birthinfo.getBirth());
        updateValue.put(GIFT, birthinfo.getGift());
        return db.update(table, updateValue, ID + "=" + birthinfo.getId(), null);
    }

    private List<BirthInfo> convertToBirthInfo(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0||!cursor.moveToFirst()) {
            return null;
        }
        List<BirthInfo> birthInfos = new ArrayList<BirthInfo>();
        for (int i = 0; i < resultCounts; i++) {
            birthInfos.add(new BirthInfo(cursor.getInt(cursor.getColumnIndex(ID)),
                                         cursor.getString(cursor.getColumnIndex(NAME)),
                                         cursor.getString(cursor.getColumnIndex(BIRTH)),
                                         cursor.getString(cursor.getColumnIndex(GIFT))));
            cursor.moveToNext();
        }
        return birthInfos;
    }
}



















