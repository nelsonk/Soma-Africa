package com.k.nelie.studentadmissionsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nelie K on 3/17/2015.
 */
public class LoginDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "topSchools";
    public static final String DATABASE_NAME = "admission.db";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_STATUS = "status";


    public LoginDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_RANK + " TEXT, " + COLUMN_STATUS + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
