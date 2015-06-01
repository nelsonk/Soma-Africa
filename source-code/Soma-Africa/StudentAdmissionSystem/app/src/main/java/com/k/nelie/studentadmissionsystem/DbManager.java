package com.k.nelie.studentadmissionsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nelie K on 3/1/2015.
 */
public class DbManager {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "topSchools";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_STATUS = "status";



    public DbManager(Context context) {
        DbClass mydatabase = new DbClass(context);
        database = mydatabase.getWritableDatabase();
    }

    public void saveRecords(String name, String rank, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_RANK, rank);
        contentValues.put(COLUMN_STATUS, status);
        database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllTimeRecords() {
        return database.rawQuery(
                "select * from " + TABLE_NAME,
                null
        );
    }




}
