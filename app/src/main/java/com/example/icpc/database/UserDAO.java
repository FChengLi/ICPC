package com.example.icpc.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean checkUser(String userId, String password) {
        Cursor cursor = db.query("user", new String[]{"user_id"},
                "user_id=? AND password=?", new String[]{userId, password},
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean addUser(String userId, String password) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("password", password);
        long result = db.insert("user", null, values);
        return result != -1;
    }

    public boolean updatePassword(String userId, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        int rows = db.update("user", values, "user_id=?", new String[]{userId});
        return rows > 0;
    }

    public boolean updateNickname(String userId, String newNickname) {
        ContentValues values = new ContentValues();
        values.put("nickname", newNickname);
        int rows = db.update("user", values, "user_id=?", new String[]{userId});
        return rows > 0;
    }

    public boolean updateEmail(String userId, String newEmail) {
        ContentValues values = new ContentValues();
        values.put("email", newEmail);
        int rows = db.update("user", values, "user_id=?", new String[]{userId});
        return rows > 0;
    }

    public User getUserInfo(String userId) {
        User user = null;
        Cursor cursor = db.query("user", new String[]{"user_id", "nickname", "email"},
                "user_id=?", new String[]{userId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            user = new User(userId, nickname, email);
            cursor.close();
        }
        return user;
    }

    @SuppressLint("Range")
    public String getUsernameByUserId(String userId) {
        String username = null;
        Cursor cursor = db.query("user", new String[]{"nickname"}, "user_id=?", new String[]{userId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("nickname"));
            cursor.close();
        }
        return username;
    }

    // 内部类 User
    public static class User {
        private String userId;
        private String nickname;
        private String email;

        public User(String userId, String nickname, String email) {
            this.userId = userId;
            this.nickname = nickname;
            this.email = email;
        }

        public String getUserId() {
            return userId;
        }

        public String getNickname() {
            return nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
