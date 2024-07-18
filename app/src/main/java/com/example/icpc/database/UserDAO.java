package com.example.icpc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
