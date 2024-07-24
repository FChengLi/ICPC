package com.example.icpc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.icpc.database.DatabaseHelper;

public class discover_article_content extends AppCompatActivity {
    private static final String TAG = "ArticleContentActivity"; // 日志标签
    private TextView titleTextView, sourceTextView, contentTextView, dateTextView, readTextView, likeTextView;
    private ImageView likeIconImageView, shareIconImageView;
    private DatabaseHelper dbHelper;
    private String articleId;
    private boolean isLiked = false; // 标记是否已经点赞

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_article_content);

        // 初始化 UI 组件
        titleTextView = findViewById(R.id.title);
        sourceTextView = findViewById(R.id.source);
        contentTextView = findViewById(R.id.articleTextView);
        dateTextView = findViewById(R.id.date_text);
        readTextView = findViewById(R.id.read);
        likeTextView = findViewById(R.id.likes);
        likeIconImageView = findViewById(R.id.like_icon);
        shareIconImageView = findViewById(R.id.share);

        ImageView backImageView = findViewById(R.id.back);
        backImageView.setOnClickListener(v -> finish());

        // 获取数据库助手实例
        dbHelper = new DatabaseHelper(this);

        // 从 intent 获取文章 ID
        Intent intent = getIntent();
        articleId = intent.getStringExtra("article_id");
        Log.d(TAG, "Received article ID from intent: " + articleId);

        if (articleId != null && !articleId.isEmpty()) {
            loadArticleContent(articleId);
        } else {
            Log.e(TAG, "Invalid or missing article ID");
            showArticleNotFound();
        }

        // 设置点赞图标点击事件
        likeIconImageView.setOnClickListener(v -> {
            if (!isLiked) {
                incrementLikeCount(articleId);
            } else {
                decrementLikeCount(articleId);
            }
        });

        // 设置分享图标点击事件
        shareIconImageView.setOnClickListener(v -> shareArticle());
    }

    private void loadArticleContent(String articleId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Log.d(TAG, "Attempting to query database for article ID: " + articleId);

        Cursor cursor = db.rawQuery("SELECT * FROM information WHERE information_id = ?", new String[]{articleId});

        if (cursor != null && cursor.moveToFirst()) {
            Log.d(TAG, "Article data found, loading data into views.");

            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String source = cursor.getString(cursor.getColumnIndexOrThrow("author"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content_text"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("publish_time"));
            int viewCount = cursor.getInt(cursor.getColumnIndexOrThrow("view_count"));
            int likeCount = cursor.getInt(cursor.getColumnIndexOrThrow("likes"));

            titleTextView.setText(title);
            sourceTextView.setText(source != null ? source : "未知来源");
            contentTextView.setText(content);
            dateTextView.setText(date);
            readTextView.setText("阅读" + viewCount);
            likeTextView.setText("点赞" + likeCount);

            // 增加阅读数并更新数据库
            incrementViewCount(articleId, viewCount);
        } else {
            Log.e(TAG, "No data found for article ID: " + articleId);
            showArticleNotFound();
        }
        cursor.close();
    }

    private void incrementViewCount(String articleId, int currentCount) {
        int newCount = currentCount + 1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE information SET view_count = ? WHERE information_id = ?", new Object[]{newCount, articleId});
        Log.d(TAG, "Updated view count to " + newCount + " for article ID: " + articleId);
        readTextView.setText("阅读" + newCount); // 更新界面上的阅读数
    }

    private void incrementLikeCount(String articleId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE information SET likes = likes + 1 WHERE information_id = ?", new Object[]{articleId});

        // 更新 UI
        Cursor cursor = db.rawQuery("SELECT likes FROM information WHERE information_id = ?", new String[]{articleId});
        if (cursor != null && cursor.moveToFirst()) {
            int newLikeCount = cursor.getInt(cursor.getColumnIndexOrThrow("likes"));
            likeTextView.setText("点赞" + newLikeCount);
            likeIconImageView.setImageResource(R.drawable.gs_liked); // 假设 gs_liked 是全红的图标资源
            isLiked = true; // 设置为已点赞
        }
        cursor.close();
        Log.d(TAG, "Updated like count for article ID: " + articleId);
    }

    private void decrementLikeCount(String articleId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE information SET likes = likes - 1 WHERE information_id = ?", new Object[]{articleId});

        // 更新 UI
        Cursor cursor = db.rawQuery("SELECT likes FROM information WHERE information_id = ?", new String[]{articleId});
        if (cursor != null && cursor.moveToFirst()) {
            int newLikeCount = cursor.getInt(cursor.getColumnIndexOrThrow("likes"));
            likeTextView.setText("点赞" + newLikeCount);
            likeIconImageView.setImageResource(R.drawable.gs_like); // 假设 gs_like 是默认的图标资源
            isLiked = false; // 设置为未点赞
        }
        cursor.close();
        Log.d(TAG, "Updated like count for article ID: " + articleId);
    }

    private void shareArticle() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享文章");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "标题: " + titleTextView.getText().toString() +
                "\n\n内容: " + contentTextView.getText().toString() +
                "\n\n来源: " + sourceTextView.getText().toString() +
                "\n\n发布日期: " + dateTextView.getText().toString());
        startActivity(Intent.createChooser(shareIntent, "分享文章到"));
    }

    private void showArticleNotFound() {
        Log.e(TAG, "Displaying 'article not found' information.");
        titleTextView.setText("文章未找到");
        sourceTextView.setText("无");
        contentTextView.setText("无法加载文章内容。");
        dateTextView.setText("无");
        readTextView.setText("阅读0");
        likeTextView.setText("点赞0");
    }
}
