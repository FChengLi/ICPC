package com.example.icpc.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.icpc.discover_article;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 3; // 更新版本号

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating database tables");
        db.execSQL("PRAGMA foreign_keys=ON;"); // 启用外键支持
        createUserTable(db);
        createHistoryTable(db);
        createFavoriteTable(db);
        createInformationTable(db);
        createPlateTable(db);
        createForumTable(db);
        createPostTable(db);
        createAnswerTable(db);
        createVideoTable(db);
        createCommentTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        // 删除所有旧表
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS history");
        db.execSQL("DROP TABLE IF EXISTS favorite");
        db.execSQL("DROP TABLE IF EXISTS information");
        db.execSQL("DROP TABLE IF EXISTS plate");
        db.execSQL("DROP TABLE IF EXISTS forum");
        db.execSQL("DROP TABLE IF EXISTS post");
        db.execSQL("DROP TABLE IF EXISTS answer");
        db.execSQL("DROP TABLE IF EXISTS video");
        db.execSQL("DROP TABLE IF EXISTS comment");
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS question");
        db.execSQL("DROP TABLE IF EXISTS option");
        db.execSQL("DROP TABLE IF EXISTS mistake");

        // 重新创建所有表
        onCreate(db);
    }

    //用户
    private void createUserTable(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE user ("
                + "user_id TEXT PRIMARY KEY,"
                + "password TEXT NOT NULL,"
                + "nickname TEXT,"
                + "email TEXT,"
                + "certified INTEGER DEFAULT 0)";
        db.execSQL(CREATE_USER_TABLE);
    }

    //浏览历史
    private void createHistoryTable(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE history ("
                + "history_id TEXT PRIMARY KEY,"
                + "information_id TEXT,"
                + "user_id TEXT,"
                + "browse_time DATETIME,"
                + "FOREIGN KEY (information_id) REFERENCES information(information_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    //收藏
    private void createFavoriteTable(SQLiteDatabase db) {
        String CREATE_FAVORITE_TABLE = "CREATE TABLE favorite ("
                + "favorite_id TEXT PRIMARY KEY,"
                + "user_id TEXT,"
                + "information_id TEXT,"
                + "favorite_time DATETIME,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (information_id) REFERENCES information(information_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    //发现
    private void createInformationTable(SQLiteDatabase db) {
        String CREATE_INFORMATION_TABLE = "CREATE TABLE information ("
                + "information_id TEXT PRIMARY KEY,"
                + "title TEXT NOT NULL,"
                + "content_text TEXT,"
                + "author TEXT,"
                + "likes INTEGER DEFAULT 0,"
                + "star INTEGER DEFAULT 0,"
                + "view_count INTEGER DEFAULT 0,"
                + "publish_time DATETIME)";
        db.execSQL(CREATE_INFORMATION_TABLE);
    }

    //板块
    private void createPlateTable(SQLiteDatabase db) {
        String CREATE_PLATE_TABLE = "CREATE TABLE plate ("
                + "plate_id TEXT PRIMARY KEY,"
                + "plate_name TEXT NOT NULL,"
                + "description TEXT,"
                + "creation_time DATETIME,"
                + "post_count INTEGER DEFAULT 0,"
                + "follow_count INTEGER DEFAULT 0)";
        db.execSQL(CREATE_PLATE_TABLE);
    }

    //论坛
    private void createForumTable(SQLiteDatabase db) {
        String CREATE_FORUM_TABLE = "CREATE TABLE forum ("
                + "forum_id TEXT PRIMARY KEY,"
                + "plate_id TEXT,"
                + "forum_name TEXT NOT NULL,"
                + "forum_description TEXT,"
                + "creator_id TEXT,"
                + "post_count INTEGER DEFAULT 0,"
                + "follow_count INTEGER DEFAULT 0,"
                + "creation_time DATETIME,"
                + "FOREIGN KEY (plate_id) REFERENCES plate(plate_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (creator_id) REFERENCES user(user_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_FORUM_TABLE);
    }

    //帖子
    private void createPostTable(SQLiteDatabase db) {
        String CREATE_POST_TABLE = "CREATE TABLE post ("
                + "post_id TEXT PRIMARY KEY,"
                + "user_id TEXT,"
                + "forum_id TEXT,"
                + "publish_time DATETIME,"
                + "title TEXT NOT NULL,"
                + "content TEXT,"
                + "answer_count INTEGER DEFAULT 0,"
                + "like_count INTEGER DEFAULT 0,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (forum_id) REFERENCES forum(forum_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_POST_TABLE);
    }

    //回答
    private void createAnswerTable(SQLiteDatabase db) {
        String CREATE_ANSWER_TABLE = "CREATE TABLE answer ("
                + "answer_id TEXT PRIMARY KEY,"
                + "post_id TEXT,"
                + "user_id TEXT,"
                + "content TEXT NOT NULL,"
                + "answer_time DATETIME,"
                + "like_count INTEGER DEFAULT 0,"
                + "FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_ANSWER_TABLE);
    }

    //快学
    private void createVideoTable(SQLiteDatabase db) {
        String CREATE_VIDEO_TABLE = "CREATE TABLE video ("
                + "video_id TEXT PRIMARY KEY,"
                + "title TEXT NOT NULL,"
                + "description TEXT,"
                + "author TEXT,"
                + "file_path TEXT,"
                + "cover TEXT,"
                + "favorites_count INTEGER DEFAULT 0)";
        db.execSQL(CREATE_VIDEO_TABLE);
    }

    //评论
    private void createCommentTable(SQLiteDatabase db) {
        String CREATE_COMMENT_TABLE = "CREATE TABLE comment ("
                + "comment_id TEXT PRIMARY KEY,"
                + "information_id TEXT,"
                + "user_id TEXT,"
                + "comment_text TEXT NOT NULL,"
                + "comment_time DATETIME,"
                + "like_count INTEGER DEFAULT 0,"
                + "FOREIGN KEY (information_id) REFERENCES information(information_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_COMMENT_TABLE);
    }

    // 获取随机文章
    public List<discover_article> getRandomArticles(int count) {
        List<discover_article> articles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM information ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(count)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(getColumnIndexSafe(cursor, "information_id"));
                String author = cursor.getString(getColumnIndexSafe(cursor, "author"));
                String publishTime = cursor.getString(getColumnIndexSafe(cursor, "publish_time"));
                String content = cursor.getString(getColumnIndexSafe(cursor, "content_text"));
                int likes = cursor.getInt(getColumnIndexSafe(cursor, "likes"));
                int star = cursor.getInt(getColumnIndexSafe(cursor, "star"));
                String title = cursor.getString(getColumnIndexSafe(cursor, "title"));
                String source = cursor.getString(getColumnIndexSafe(cursor, "author")); // Assuming source is stored in author column
                String date = cursor.getString(getColumnIndexSafe(cursor, "publish_time")); // Assuming date is the publish time

                articles.add(new discover_article(id, author, publishTime, content, likes, star, title, source, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articles;
    }

    private int getColumnIndexSafe(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            throw new IllegalArgumentException("Column " + columnName + " does not exist in cursor.");
        }
        return index;
    }

}
