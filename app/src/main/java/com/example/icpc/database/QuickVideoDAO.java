package com.example.icpc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
// 确保在 DatabaseHelper 中创建的表名与 DAO 中一致
public class QuickVideoDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public QuickVideoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addQuickVideo(String videoId, String title, String description, String author, String filePath, String cover, int favoritesCount) {
        ContentValues values = new ContentValues();
        values.put("video_id", videoId);
        values.put("title", title);
        values.put("description", description);
        values.put("author", author);
        values.put("file_path", filePath);
        values.put("cover", cover);
        values.put("favorites_count", favoritesCount);
        return db.insert("video", null, values); // 修改表名
    }

    public int updateQuickVideo(String videoId, String title, String description, String author, String filePath, String cover, int favoritesCount) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("author", author);
        values.put("file_path", filePath);
        values.put("cover", cover);
        values.put("favorites_count", favoritesCount);
        return db.update("video", values, "video_id = ?", new String[]{videoId});
    }

    public int deleteQuickVideo(String videoId) {
        return db.delete("video", "video_id = ?", new String[]{videoId});
    }

    public Cursor getQuickVideo(String videoId) {
        return db.query("video", null, "video_id = ?", new String[]{videoId}, null, null, null); // 修改表名
    }

    public Cursor getAllQuickVideos() {
        return db.query("video", null, null, null, null, null, null); // 修改表名
    }
}
