package com.example.icpc;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.MutableLiveData;
import com.example.icpc.database.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private static PostRepository instance;
    private MutableLiveData<Post> post;
    private MutableLiveData<List<Comment_Feng>> comments;
    private DatabaseHelper databaseHelper;
    private Context context;

    private PostRepository(Context context) {
        this.context = context.getApplicationContext(); // Ensure context is application context
        databaseHelper = new DatabaseHelper(this.context);
        post = new MutableLiveData<>();
        comments = new MutableLiveData<>();
    }

    public static PostRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PostRepository(context.getApplicationContext()); // Use application context
        }
        return instance;
    }

    public MutableLiveData<Post> getPost(String postId) {
        loadPostFromDatabase(postId);
        return post;
    }

    public MutableLiveData<List<Comment_Feng>> getComments(String postId) {
        loadCommentsFromDatabase(postId);
        return comments;
    }

    public void addComment(String commentText, String postId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // 获取当前登录用户的用户名
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("currentUsername", "user2"); // Default to "user2" if not found

        values.put("user_id", currentUsername);
        values.put("comment_time", "2022-05-21"); // Replace with actual time
        values.put("content", commentText);
        values.put("like_count", 0);
        values.put("post_id", postId);
        db.insert("comment", null, values);
        loadCommentsFromDatabase(postId);
    }

    // 获取用户头像 URI
    public String getUserAvatarUri(String userId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"avatar_uri"}, "user_id=?",
                new String[]{userId}, null, null, null);
        String avatarUri = null;
        if (cursor != null && cursor.moveToFirst()) {
            avatarUri = cursor.getString(cursor.getColumnIndexOrThrow("avatar_uri"));
            cursor.close();
        }
        return avatarUri;
    }

    public void incrementStar(String postId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("post", new String[]{"like_sum"}, "post_id=?",
                new String[]{postId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int likeSum = cursor.getInt(cursor.getColumnIndexOrThrow("like_sum"));
            ContentValues values = new ContentValues();
            values.put("like_sum", likeSum + 1);
            db.update("post", values, "post_id=?",
                    new String[]{postId});
            loadPostFromDatabase(postId);
            cursor.close();
        }
    }

    public void incrementComment(String postId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("post", new String[]{"comment_sum"}, "post_id=?",
                new String[]{postId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int commentSum = cursor.getInt(cursor.getColumnIndexOrThrow("comment_sum"));
            ContentValues values = new ContentValues();
            values.put("comment_sum", commentSum + 1);
            db.update("post", values, "post_id=?",
                    new String[]{postId});
            loadPostFromDatabase(postId);
            cursor.close();
        }
    }

    public int getCommentCount(String postId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("comment", new String[]{"COUNT(*)"}, "post_id=?",
                new String[]{postId}, null, null, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    private void loadPostFromDatabase(String postId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("post", null, "post_id=?",
                new String[]{postId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String postIdStr = cursor.getString(cursor.getColumnIndexOrThrow("post_id"));
            String userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
            int forumId = cursor.getInt(cursor.getColumnIndexOrThrow("forum_id"));
            int commentSum = cursor.getInt(cursor.getColumnIndexOrThrow("comment_sum"));
            int likeSum = cursor.getInt(cursor.getColumnIndexOrThrow("like_sum"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String publishTime = cursor.getString(cursor.getColumnIndexOrThrow("publish_time"));
            post.postValue(new Post(postIdStr, userId, forumId, commentSum, likeSum, title, publishTime));
            cursor.close();
        }
    }

    private void loadCommentsFromDatabase(String postId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("comment", null, "post_id=?",
                new String[]{postId}, null, null, "comment_id DESC");
        List<Comment_Feng> commentList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String commentId = cursor.getString(cursor.getColumnIndexOrThrow("comment_id"));
                String author = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("comment_time"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                int stars = cursor.getInt(cursor.getColumnIndexOrThrow("like_count"));
                commentList.add(new Comment_Feng(commentId, author, time, content, stars));
            } while (cursor.moveToNext());
            comments.postValue(commentList);
            cursor.close();
        }
    }
}
