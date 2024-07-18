package com.example.icpc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 2; // 更新版本号

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建新表的SQL语句，不包含region和occupation列
        String CREATE_USER_TABLE = "CREATE TABLE user ("
                + "user_id TEXT PRIMARY KEY,"
                + "password TEXT,"
                + "nickname TEXT,"
                + "email TEXT,"
                + "certified INTEGER)";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // 创建新表，不包含region和occupation列
            db.execSQL("CREATE TABLE user_new ("
                    + "user_id TEXT PRIMARY KEY,"
                    + "password TEXT,"
                    + "nickname TEXT,"
                    + "email TEXT,"
                    + "certified INTEGER)");

            // 复制旧表数据到新表
            db.execSQL("INSERT INTO user_new (user_id, password, nickname, email, certified) "
                    + "SELECT user_id, password, nickname, email, certified FROM user");

            // 删除旧表
            db.execSQL("DROP TABLE user");

            // 将新表重命名为旧表
            db.execSQL("ALTER TABLE user_new RENAME TO user");
        }
    }
}
