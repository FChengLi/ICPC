package com.example.icpc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class zDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "videoDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_VIDEO = "Video";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_FILE_PATH = "file_path";
    public static final String COLUMN_COVER = "cover";
    public static final String COLUMN_FAVORITES = "favorites";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_VIDEO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +  // id字段已经是PRIMARY KEY，默认为唯一
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT, " +
                    COLUMN_FILE_PATH + " TEXT, " +
                    COLUMN_COVER + " TEXT, " +
                    COLUMN_FAVORITES + " INTEGER" +
                    ");";

    public zDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO);
        onCreate(db);
    }
}
