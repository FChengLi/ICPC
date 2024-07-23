package com.example.icpc;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.MutableLiveData;

import com.example.icpc.database.ArticleDatabase;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private static ArticleRepository instance;
    private MutableLiveData<Article> article;
    private MutableLiveData<List<Comment_Feng>> comments;
    private ArticleDatabase database;
    private Context context;

    private ArticleRepository(Context context) {
        this.context = context;
        database = new ArticleDatabase(context);
        article = new MutableLiveData<>();
        comments = new MutableLiveData<>();
    }

    public static ArticleRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ArticleRepository(context);
        }
        return instance;
    }

    public MutableLiveData<Article> getArticle(int articleId) {
        loadArticleFromDatabase(articleId);
        return article;
    }

    public MutableLiveData<List<Comment_Feng>> getComments(int articleId) {
        loadCommentsFromDatabase(articleId);
        return comments;
    }

    public void addComment(String commentText, int articleId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        // 获取当前登录用户的用户名
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("currentUsername", "user2"); // Default to "user2" if not found

        values.put(ArticleDatabase.COLUMN_COMMENT_AUTHOR, currentUsername);
        values.put(ArticleDatabase.COLUMN_COMMENT_TIME, "2022.5.21"); // Replace with actual time
        values.put(ArticleDatabase.COLUMN_COMMENT_CONTENT, commentText);
        values.put(ArticleDatabase.COLUMN_COMMENT_STARS, 0);
        values.put(ArticleDatabase.COLUMN_POST_ID, articleId);
        db.insert(ArticleDatabase.TABLE_COMMENTS, null, values);
        loadCommentsFromDatabase(articleId);
    }

    public void incrementStar(int articleId) {
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.query(ArticleDatabase.TABLE_ARTICLES, null, ArticleDatabase.COLUMN_ID + "=?",
                new String[]{String.valueOf(articleId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int starSum = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_STAR_SUM));
            ContentValues values = new ContentValues();
            values.put(ArticleDatabase.COLUMN_STAR_SUM, starSum + 1);
            db.update(ArticleDatabase.TABLE_ARTICLES, values, ArticleDatabase.COLUMN_ID + "=?",
                    new String[]{String.valueOf(articleId)});
            loadArticleFromDatabase(articleId);
            cursor.close();
        }
    }

    public void incrementComment(int articleId) {
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.query(ArticleDatabase.TABLE_ARTICLES, null, ArticleDatabase.COLUMN_ID + "=?",
                new String[]{String.valueOf(articleId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int commentSum = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_SUM));
            ContentValues values = new ContentValues();
            values.put(ArticleDatabase.COLUMN_COMMENT_SUM, commentSum + 1);
            db.update(ArticleDatabase.TABLE_ARTICLES, values, ArticleDatabase.COLUMN_ID + "=?",
                    new String[]{String.valueOf(articleId)});
            loadArticleFromDatabase(articleId);
            cursor.close();
        }
    }

    public int getCommentCount(int postId) {
        return database.getCommentCount(postId);
    }

    private void loadArticleFromDatabase(int articleId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(ArticleDatabase.TABLE_ARTICLES, null, ArticleDatabase.COLUMN_ID + "=?",
                new String[]{String.valueOf(articleId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String author = cursor.getString(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_AUTHOR));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_TIME));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_CONTENT));
            int commentSum = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_SUM));
            int starSum = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_STAR_SUM));
            article.postValue(new Article(articleId, author, time, content, commentSum, starSum));
            cursor.close();
        }
    }

    private void loadCommentsFromDatabase(int articleId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(ArticleDatabase.TABLE_COMMENTS, null, ArticleDatabase.COLUMN_POST_ID + "=?",
                new String[]{String.valueOf(articleId)}, null, null, ArticleDatabase.COLUMN_COMMENT_ID + " DESC");
        List<Comment_Feng> commentList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int commentId = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_ID));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_AUTHOR));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_TIME));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_CONTENT));
                int stars = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleDatabase.COLUMN_COMMENT_STARS));
                commentList.add(new Comment_Feng(commentId, author, time, content, stars));
            } while (cursor.moveToNext());
            comments.postValue(commentList);
            cursor.close();
        }
    }
}
