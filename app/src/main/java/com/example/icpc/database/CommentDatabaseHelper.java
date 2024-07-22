package com.example.icpc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CommentDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "comments.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VIDEO_ID = "video_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_LIKED = "liked";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_COMMENTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_VIDEO_ID + " INTEGER, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_COMMENT + " TEXT, " +
                    COLUMN_LIKED + " INTEGER);";

    public CommentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}
