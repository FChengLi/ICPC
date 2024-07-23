package com.example.icpc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 2; // 更新版本号

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        createFollowForumTable(db);
/*        createTestTable(db);
        createQuestionTable(db);
        createOptionTable(db);
        createMistakeTable(db);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
                + "follow_count INTEGER DEFAULT 0)";
        db.execSQL(CREATE_FORUM_TABLE);
    }

    private void createFollowForumTable(SQLiteDatabase db) {
        String CREATE_FOLLOW_FORUM_TABLE = "CREATE TABLE follow_forum ("
                + "follow_id TEXT PRIMARY KEY,"
                + "user_id TEXT,"
                + "forum_id TEXT,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (forum_id) REFERENCES forum(forum_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_FOLLOW_FORUM_TABLE);
    }


    //帖子
    private void createPostTable(SQLiteDatabase db) {
        String CREATE_POST_TABLE = "CREATE TABLE post ("
                + "post_id TEXT PRIMARY KEY,"
                + "user_id TEXT,"
                + "forum_id TEXT,"
                + "publish_time DATETIME,"
                + "title TEXT NOT NULL,"
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

/*    //测试
    private void createTestTable(SQLiteDatabase db) {
        String CREATE_TEST_TABLE = "CREATE TABLE test ("
                + "test_id TEXT PRIMARY KEY,"
                + "content_id TEXT,"
                + "question_count INTEGER DEFAULT 0,"
                + "FOREIGN KEY (content_id) REFERENCES video(video_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_TEST_TABLE);
    }

    //题目
    private void createQuestionTable(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE question ("
                + "question_id TEXT PRIMARY KEY,"
                + "quiz_id TEXT,"
                + "content TEXT NOT NULL,"
                + "question_type TEXT,"
                + "category TEXT,"
                + "FOREIGN KEY (quiz_id) REFERENCES test(test_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    //选项
    private void createOptionTable(SQLiteDatabase db) {
        String CREATE_OPTION_TABLE = "CREATE TABLE option ("
                + "option_id TEXT PRIMARY KEY,"
                + "question_id TEXT,"
                + "content TEXT NOT NULL,"
                + "is_correct INTEGER DEFAULT 0,"
                + "FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_OPTION_TABLE);
    }

    //错题集
    private void createMistakeTable(SQLiteDatabase db) {
        String CREATE_MISTAKE_TABLE = "CREATE TABLE mistake ("
                + "mistake_id TEXT PRIMARY KEY,"
                + "user_id TEXT,"
                + "question_id TEXT,"
                + "test_id TEXT,"
                + "mistake_time DATETIME,"
                + "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE,"
                + "FOREIGN KEY (test_id) REFERENCES test(test_id) ON DELETE CASCADE)";
        db.execSQL(CREATE_MISTAKE_TABLE);
    }*/

}
