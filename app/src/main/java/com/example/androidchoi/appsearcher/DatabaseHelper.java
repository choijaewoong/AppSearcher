package com.example.androidchoi.appsearcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Choi on 2016-03-12.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "app_searcher.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "app_list";
    public static final String TAG = "DatabaseHelper";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "app_logo";
    public static final String COLUMN_PACKAGE_NAME = "package_name";
    public static final String COLUMN_ACTIVITY_NAME = "activity_name";

//    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("db", "creating table ["+ TABLE_NAME + "].");
        try {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);
        }catch (Exception e){
            Log.e(TAG, "Exception in DROP_SQL", e);
        }

        String CREATE_SQL = "create table " + TABLE_NAME + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + COLUMN_NAME + ", "
                + COLUMN_IMAGE + " BLOB, "
                + COLUMN_PACKAGE_NAME + ", "
                + COLUMN_ACTIVITY_NAME + ")";
        try {
            db.execSQL(CREATE_SQL);
        }catch (Exception e){
            Log.e(TAG, "Exception in CREATE_SQL", e);
        }

        // println("inserting records");
        // insert data
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion +
        " to " + newVersion + ".");
        try {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);
        }catch (Exception e){
            Log.e(TAG, "Exception in DROP_SQL", e);
        }
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i("db","opened database [" + DATABASE_NAME + "].");
    }

}
