package com.example.icpc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArticleDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "articleDatabase.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_COMMENT_SUM = "comment_sum";
    public static final String COLUMN_STAR_SUM = "star_sum";

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_COMMENT_ID = "comment_id";
    public static final String COLUMN_COMMENT_AUTHOR = "comment_author";
    public static final String COLUMN_COMMENT_TIME = "comment_time";
    public static final String COLUMN_COMMENT_CONTENT = "comment_content";
    public static final String COLUMN_COMMENT_STARS = "comment_stars";
    public static final String COLUMN_POST_ID = "post_id";

    private static final String TABLE_CREATE_ARTICLES =
            "CREATE TABLE " + TABLE_ARTICLES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AUTHOR + " TEXT, " +
                    COLUMN_TIME + " TEXT, " +
                    COLUMN_CONTENT + " TEXT, " +
                    COLUMN_COMMENT_SUM + " INTEGER, " +
                    COLUMN_STAR_SUM + " INTEGER);";

    private static final String TABLE_CREATE_COMMENTS =
            "CREATE TABLE " + TABLE_COMMENTS + " (" +
                    COLUMN_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COMMENT_AUTHOR + " TEXT, " +
                    COLUMN_COMMENT_TIME + " TEXT, " +
                    COLUMN_COMMENT_CONTENT + " TEXT, " +
                    COLUMN_COMMENT_STARS + " INTEGER, " +
                    COLUMN_POST_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_POST_ID + ") REFERENCES " + TABLE_ARTICLES + "(" + COLUMN_ID + "));";

    public ArticleDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_ARTICLES);
        db.execSQL(TABLE_CREATE_COMMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

    public int getCommentCount(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_POST_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(postId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    public void updateCommentStars(int commentId, int newStarCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT_STARS, newStarCount);
        db.update(TABLE_COMMENTS, values, COLUMN_COMMENT_ID + "=?", new String[] {String.valueOf(commentId)});
    }
    // ArticleDatabase.java
    public void incrementCommentStar(int commentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // 获取当前的点赞数
            Cursor cursor = db.query(TABLE_COMMENTS, new String[]{COLUMN_COMMENT_STARS}, COLUMN_COMMENT_ID + "=?", new String[]{String.valueOf(commentId)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int currentStars = cursor.getInt(0);  // 确保 COLUMN_COMMENT_STARS 是第一列，或者使用 cursor.getColumnIndex(COLUMN_COMMENT_STARS)
                ContentValues values = new ContentValues();
                values.put(COLUMN_COMMENT_STARS, currentStars + 1);
                db.update(TABLE_COMMENTS, values, COLUMN_COMMENT_ID + "=?", new String[]{String.valueOf(commentId)});
            }
            if (cursor != null) {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }



}