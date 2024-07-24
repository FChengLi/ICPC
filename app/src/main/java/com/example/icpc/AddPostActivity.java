package com.example.icpc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddPostActivity extends AppCompatActivity {

    private EditText contentEditText;
    private Button submitButton;
    private ImageView returnImageView;
    private DatabaseHelper dbHelper;
    private int forumId; // 传递的论坛 ID
    private String userId;  // 当前登录用户 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        // 初始化 UI 组件
        contentEditText = findViewById(R.id.contentEditText);
        submitButton = findViewById(R.id.subscribebutton); // 确保按钮 ID 正确
        returnImageView = findViewById(R.id.return1);

        // 初始化数据库助手
        dbHelper = new DatabaseHelper(this);

        // 获取传递的数据
        String forumIdString = getIntent().getStringExtra("forumId");
        try {
            forumId = Integer.parseInt(forumIdString); // 将字符串转换为整数
        } catch (NumberFormatException e) {
            Toast.makeText(this, "论坛 ID 格式无效", Toast.LENGTH_SHORT).show();
            finish();
            return; // 退出方法
        }

        userId = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("currentUserId", null);

        if (userId == null) {
            Toast.makeText(this, "用户未登录", Toast.LENGTH_SHORT).show();
            finish();
            return; // 退出方法
        }

        // 提交按钮点击事件
        submitButton.setOnClickListener(v -> submitPost());

        // 返回按钮点击事件
        returnImageView.setOnClickListener(v -> finish());
    }

    private void submitPost() {
        String content = contentEditText.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String postId = generatePostId(); // 生成唯一的帖子 ID

        values.put("post_id", postId);
        values.put("user_id", userId);
        values.put("forum_id", forumId); // 作为整数插入
        values.put("publish_time", getCurrentTime()); // 当前时间
        values.put("title", content);

        long result = db.insert("post", null, values);
        if (result != -1) {
            Toast.makeText(this, "帖子创建成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent); // 设置结果
            finish();
        } else {
            Toast.makeText(this, "帖子创建失败", Toast.LENGTH_SHORT).show();
        }
    }

    private static synchronized String generatePostId() {
        return UUID.randomUUID().toString();
    }

    private String getCurrentTime() {
        // 格式化当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
