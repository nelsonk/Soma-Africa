package com.k.nelie.studentadmissionsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nelie K on 3/1/2015.
 */
public class DbClass extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;
    public static final String TABLE_SCHOOLS = "topSchools";
    public static final String DATABASE_NAME = "admission.db";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_STATUS = "status";


    //login table variables
    public static final String TABLE_LOGIN = "login";
    public static final String LOGIN_ID = "_id";
    public static final String LOGIN_NAME = "username";
    public static final String LOGIN_PASSWORD = "password";


    //courses table variables
    public static final String TABLE_COURSE = "courses";
    public static final String COURSE_ID = "_id";
    public static final String COURSE_SCHNAME = "schName";
    public static final String COURSE_COURSETYPE = "courseType";
    public static final String COURSE_COMBINATION = "combination";
    public static final String COURSE_CUTOFF = "cutoff";


    //Registration table variables
    public static final String TABLE_REGISTRATION = "registration";
    public static final String REGISTRATION_ID = "_id";
    public static final String REGISTRATION_SCHNAME = "schName";
    public static final String REGISTRATION_COURSETYPE = "courseType";
    public static final String REGISTRATION_COMBINATION = "combination";
    public static final String REGISTRATION_USERNAME = "username";
    public static final String REGISTRATION_REF_ID = "ref_id";
    public static final String REGISTRATION_STATUS = "status";



    public static final String TABLE_SIGNUP = "signUp";
    public static final String SIGNUP_ID = "_id";
    public static final String SIGNUP_LNAME = "lname";
    public static final String SIGNUP_FNAME = "fname";
    public static final String SIGNUP_GENDER = "gender";
    public static final String SIGNUP_COUNTRY = "country";
    public static final String SIGNUP_CITY = "city";
    public static final String SIGNUP_AGE = "age";
    public static final String SIGNUP_TEL = "tel";
    public static final String SIGNUP_EMAIL = "email";


    private static final String CREATE_TABLE_LOGIN ="CREATE TABLE " + TABLE_LOGIN + "(" + LOGIN_ID + " INTEGER PRIMARY KEY, " + LOGIN_NAME + " TEXT, " + LOGIN_PASSWORD + " TEXT)";
    private static final String CREATE_TABLE_SCHOOLS = "CREATE TABLE " + TABLE_SCHOOLS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_RANK + " TEXT, " + COLUMN_STATUS + " TEXT)";
    private static final String CREATE_TABLE_SIGNUP = "CREATE TABLE " + TABLE_SIGNUP + "(" + SIGNUP_ID + " INTEGER PRIMARY KEY, " + SIGNUP_FNAME + " TEXT, " + SIGNUP_LNAME + " TEXT, " + SIGNUP_GENDER + " TEXT, " + SIGNUP_COUNTRY + " TEXT, " + SIGNUP_CITY + " TEXT, " + SIGNUP_AGE + " TEXT, " + SIGNUP_TEL + " TEXT, " + SIGNUP_EMAIL + " TEXT)";
    private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE + "(" + COURSE_ID + " INTEGER PRIMARY KEY, " + COURSE_SCHNAME + " TEXT, " + COURSE_COURSETYPE + " TEXT, " + COURSE_COMBINATION + " TEXT, " + COURSE_CUTOFF + " TEXT)";
    private static final String CREATE_TABLE_REGISTRATION = "CREATE TABLE " + TABLE_REGISTRATION + "(" + REGISTRATION_ID + " INTEGER PRIMARY KEY, " + REGISTRATION_SCHNAME + " TEXT, " + REGISTRATION_COURSETYPE + " TEXT, " + REGISTRATION_COMBINATION + " TEXT, " + REGISTRATION_USERNAME + " TEXT, " + REGISTRATION_REF_ID + " TEXT, " + REGISTRATION_STATUS + " TEXT)";




    public DbClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_SCHOOLS);
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_SIGNUP);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_REGISTRATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        onCreate(db);

    }
}
