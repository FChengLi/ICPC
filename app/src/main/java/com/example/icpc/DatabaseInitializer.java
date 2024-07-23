package com.example.icpc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.icpc.database.ArticleDatabase;

public class DatabaseInitializer {

    public static void initializeDatabase(Context context) {
        ArticleDatabase dbHelper = new ArticleDatabase(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 添加示例文章
        ContentValues articleValues = new ContentValues();
        articleValues.put(ArticleDatabase.COLUMN_AUTHOR, "MeihuTETL");
        articleValues.put(ArticleDatabase.COLUMN_TIME, "2020");
        articleValues.put(ArticleDatabase.COLUMN_CONTENT, "This is a sample article content.");
        articleValues.put(ArticleDatabase.COLUMN_COMMENT_SUM, 0);
        articleValues.put(ArticleDatabase.COLUMN_STAR_SUM, 0);
        db.insert(ArticleDatabase.TABLE_ARTICLES, null, articleValues);

        // 添加示例评论
        ContentValues commentValues = new ContentValues();
        commentValues.put(ArticleDatabase.COLUMN_COMMENT_AUTHOR, "user1");
        commentValues.put(ArticleDatabase.COLUMN_COMMENT_TIME, "2022.5.20");
        commentValues.put(ArticleDatabase.COLUMN_COMMENT_CONTENT, "Great article!");
        commentValues.put(ArticleDatabase.COLUMN_COMMENT_STARS, 5);
        commentValues.put(ArticleDatabase.COLUMN_POST_ID, 5); // 文章ID为1
        db.insert(ArticleDatabase.TABLE_COMMENTS, null, commentValues);
    }
}
