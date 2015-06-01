package com.neliek.database.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.k.nelie.studentadmissionsystem.DbClass;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Nelie K on 3/7/2015.
 */
public class MyDBContentProvider extends ContentProvider {
    private DbClass database;
    // used for the UriMacher
    private static final int SCHOOLS = 10;
    private static final int SCHOOL_ID = 20;

    private static final int LOGINS = 30;
    private static final int LOGIN_ID = 40;

    private static final int SIGNUP = 50;
    private static final int SIGNUP_ID = 60;

    private static final int COURSE = 15;
    private static final int COURSE_ID = 25;

    private static final int REGISTRATION = 35;
    private static final int REGISTRATION_ID = 45;

    private static final String AUTHORITY = "com.neliek.database.provider";

    private static final String BASE_PATH = "schools";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    private static final String LOGIN_PATH = "logins";
    public static final Uri LOGIN_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + LOGIN_PATH);


    private static final String SIGNUP_PATH = "signup";
    public static final Uri SIGNUP_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + SIGNUP_PATH);

    private static final String COURSE_PATH = "course";
    public static final Uri COURSE_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + COURSE_PATH);


    private static final String REGISTRATION_PATH = "registration";
    public static final Uri REGISTRATION_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + REGISTRATION_PATH);




    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/schools";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/school";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, SCHOOLS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SCHOOL_ID);

        sURIMatcher.addURI(AUTHORITY, LOGIN_PATH, LOGINS);
        sURIMatcher.addURI(AUTHORITY, LOGIN_PATH + "/#", LOGIN_ID);

        sURIMatcher.addURI(AUTHORITY, SIGNUP_PATH, SIGNUP);
        sURIMatcher.addURI(AUTHORITY, SIGNUP_PATH + "/#", SIGNUP_ID);

        sURIMatcher.addURI(AUTHORITY, COURSE_PATH, COURSE);
        sURIMatcher.addURI(AUTHORITY, COURSE_PATH + "/#", COURSE_ID);

        sURIMatcher.addURI(AUTHORITY, REGISTRATION_PATH, REGISTRATION);
        sURIMatcher.addURI(AUTHORITY, REGISTRATION_PATH + "/#", REGISTRATION_ID);
    }

    @Override
    public boolean onCreate() {
          database = new DbClass(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);



        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case SCHOOLS:
                // Set the table
                queryBuilder.setTables(DbClass.TABLE_SCHOOLS);
                break;
            case SCHOOL_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DbClass.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;

            case LOGINS:
                // Set the table
                queryBuilder.setTables(DbClass.TABLE_LOGIN);
                break;
            case LOGIN_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DbClass.LOGIN_ID + "="
                        + uri.getLastPathSegment());
                break;

            case SIGNUP:
                // Set the table
                queryBuilder.setTables(DbClass.TABLE_SIGNUP);
                break;
            case SIGNUP_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DbClass.SIGNUP_ID + "="
                        + uri.getLastPathSegment());
                break;

            case COURSE:
                // Set the table
                queryBuilder.setTables(DbClass.TABLE_COURSE);
                break;
            case COURSE_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DbClass.COURSE_ID + "="
                        + uri.getLastPathSegment());
                break;

            case REGISTRATION:
                // Set the table
                queryBuilder.setTables(DbClass.TABLE_REGISTRATION);
                break;
            case REGISTRATION_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DbClass.REGISTRATION_ID + "="
                        + uri.getLastPathSegment());
                break;


            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;


    }

    private void checkColumns(String[] projection) {
        String[] available = { DbClass.COLUMN_NAME,
                DbClass.COLUMN_STATUS, DbClass.COLUMN_RANK, DbClass.SIGNUP_ID, DbClass.SIGNUP_COUNTRY, DbClass.SIGNUP_GENDER, DbClass.SIGNUP_CITY, DbClass.SIGNUP_AGE, DbClass.SIGNUP_FNAME, DbClass.SIGNUP_LNAME, DbClass.SIGNUP_EMAIL, DbClass.SIGNUP_TEL,
                DbClass.COLUMN_ID, DbClass.LOGIN_ID, DbClass.LOGIN_NAME, DbClass.LOGIN_PASSWORD, DbClass.COURSE_CUTOFF, DbClass.COURSE_COMBINATION, DbClass.COURSE_COURSETYPE,
         DbClass.COURSE_ID, DbClass.COURSE_SCHNAME, DbClass.REGISTRATION_STATUS, DbClass.REGISTRATION_REF_ID, DbClass.REGISTRATION_USERNAME, DbClass.REGISTRATION_COMBINATION, DbClass.REGISTRATION_COURSETYPE, DbClass.REGISTRATION_ID, DbClass.REGISTRATION_SCHNAME
        };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri results= Uri.parse("");

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case SCHOOLS:
                id = sqlDB.insert(DbClass.TABLE_SCHOOLS, null, values);
                results = Uri.parse(BASE_PATH + "/" + id);
                break;
            case LOGINS:
                id = sqlDB.insert(DbClass.TABLE_LOGIN, null, values);
                results = Uri.parse(LOGIN_PATH + "/" + id);
                break;
            case SIGNUP:
                id = sqlDB.insert(DbClass.TABLE_SIGNUP, null, values);
                results = Uri.parse(SIGNUP_PATH + "/" + id);
                break;
            case COURSE:
                id = sqlDB.insert(DbClass.TABLE_COURSE, null, values);
                results = Uri.parse(COURSE_PATH + "/" + id);
                break;
            case REGISTRATION:
                id = sqlDB.insert(DbClass.TABLE_REGISTRATION, null, values);
                results = Uri.parse(REGISTRATION_PATH + "/" + id);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return results;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case SCHOOLS:
                rowsDeleted = sqlDB.delete(DbClass.TABLE_SCHOOLS, selection,
                        selectionArgs);
                break;
            case SCHOOL_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_SCHOOLS,
                            DbClass.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_SCHOOLS,
                            DbClass.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case LOGINS:
                rowsDeleted = sqlDB.delete(DbClass.TABLE_LOGIN, selection,
                        selectionArgs);
                break;
            case LOGIN_ID:
                String ids = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_LOGIN,
                            DbClass.LOGIN_ID + "=" + ids,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_LOGIN,
                            DbClass.LOGIN_ID + "=" + ids
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case SIGNUP:
                rowsDeleted = sqlDB.delete(DbClass.TABLE_SIGNUP, selection,
                        selectionArgs);
                break;
            case SIGNUP_ID:
                String Sid = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_SIGNUP,
                            DbClass.SIGNUP_ID + "=" + Sid,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_SIGNUP,
                            DbClass.SIGNUP_ID + "=" + Sid
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case COURSE:
                rowsDeleted = sqlDB.delete(DbClass.TABLE_COURSE, selection,
                        selectionArgs);
                break;
            case COURSE_ID:
                String cid = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_COURSE,
                            DbClass.COURSE_ID + "=" + cid,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_COURSE,
                            DbClass.COURSE_ID + "=" + cid
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case REGISTRATION:
                rowsDeleted = sqlDB.delete(DbClass.TABLE_REGISTRATION, selection,
                        selectionArgs);
                break;
            case REGISTRATION_ID:
                String rid = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_REGISTRATION,
                            DbClass.REGISTRATION_ID + "=" + rid,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(DbClass.TABLE_REGISTRATION,
                            DbClass.REGISTRATION_ID + "=" + rid
                                    + " and " + selection,
                            selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case SCHOOLS:
                rowsUpdated = sqlDB.update(DbClass.TABLE_SCHOOLS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case SCHOOL_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_SCHOOLS,
                            values,
                            DbClass.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_SCHOOLS,
                            values,
                            DbClass.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case LOGINS:
                rowsUpdated = sqlDB.update(DbClass.TABLE_LOGIN,
                        values,
                        selection,
                        selectionArgs);
                break;
            case LOGIN_ID:
                String ids = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_LOGIN,
                            values,
                            DbClass.LOGIN_ID + "=" + ids,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_LOGIN,
                            values,
                            DbClass.LOGIN_ID + "=" + ids
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;

            case SIGNUP:
                rowsUpdated = sqlDB.update(DbClass.TABLE_SIGNUP,
                        values,
                        selection,
                        selectionArgs);
                break;
            case SIGNUP_ID:
                String sid = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_SIGNUP,
                            values,
                            DbClass.SIGNUP_ID + "=" + sid,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_SIGNUP,
                            values,
                            DbClass.SIGNUP_ID + "=" + sid
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case COURSE:
                rowsUpdated = sqlDB.update(DbClass.TABLE_COURSE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case COURSE_ID:
                String cid = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_COURSE,
                            values,
                            DbClass.COURSE_ID + "=" + cid,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_COURSE,
                            values,
                            DbClass.COURSE_ID + "=" + cid
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case REGISTRATION:
                rowsUpdated = sqlDB.update(DbClass.TABLE_REGISTRATION,
                        values,
                        selection,
                        selectionArgs);
                break;
            case REGISTRATION_ID:
                String rid = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_REGISTRATION,
                            values,
                            DbClass.REGISTRATION_ID + "=" + rid,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DbClass.TABLE_REGISTRATION,
                            values,
                            DbClass.REGISTRATION_ID + "=" + rid
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;




            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;


    }
}
