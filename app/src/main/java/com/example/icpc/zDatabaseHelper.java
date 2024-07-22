package com.example.icpc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class zDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_VIDEO = "Video";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_FILE_PATH = "file_path";
    public static final String COLUMN_COVER = "cover";
    public static final String COLUMN_FAVORITES = "favorites";

    // 新增论坛表的列定义
    public static final String TABLE_FORUM = "Forum";
    public static final String COLUMN_FORUM_ID = "forum_id";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_FORUM_NAME = "forum_name";
    public static final String COLUMN_FORUM_DESCRIPTION = "forum_description";
    public static final String COLUMN_CREATOR_ID = "creator_id";
    public static final String COLUMN_TOTAL_POSTS = "total_posts";
    public static final String COLUMN_USERS = "users";
    public static final String COLUMN_CREATION_TIME = "creation_time";

    private static final String TABLE_VIDEO_CREATE =
            "CREATE TABLE " + TABLE_VIDEO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +  // id字段已经是PRIMARY KEY，默认为唯一
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT, " +
                    COLUMN_FILE_PATH + " TEXT, " +
                    COLUMN_COVER + " TEXT, " +
                    COLUMN_FAVORITES + " INTEGER" +
                    ");";

    // 新增论坛表的创建语句
    private static final String TABLE_FORUM_CREATE =
            "CREATE TABLE " + TABLE_FORUM + " (" +
                    COLUMN_FORUM_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_SECTION + " TEXT, " +
                    COLUMN_FORUM_NAME + " TEXT, " +
                    COLUMN_FORUM_DESCRIPTION + " TEXT, " +
                    COLUMN_CREATOR_ID + " INTEGER, " +
                    COLUMN_TOTAL_POSTS + " INTEGER, " +
                    COLUMN_USERS + " INTEGER, " +
                    COLUMN_CREATION_TIME + " TEXT" +
                    ");";

    public zDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_VIDEO_CREATE);
        db.execSQL(TABLE_FORUM_CREATE); // 创建论坛表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORUM); // 删除旧的论坛表
        onCreate(db);
    }
}
