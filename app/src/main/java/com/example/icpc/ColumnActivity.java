package com.example.icpc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ColumnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ColumnAdapter adapter;
    private List<Post> columnList;
    private TextView middleTextView;
    private Button subscribeButton;
    private Button addColumnButton;
    private DatabaseHelper dbHelper;
    private int forumId; // 当前论坛的 ID
    private TextView followNumTextView;

    private static final int ADD_COLUMN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);

        // 初始化 UI 组件
        middleTextView = findViewById(R.id.middle);
        subscribeButton = findViewById(R.id.subscribebutton);
        addColumnButton = findViewById(R.id.addcolumn);
        recyclerView = findViewById(R.id.recyclerView);
        followNumTextView = findViewById(R.id.follownum);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        columnList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        // 获取传递的数据
        String iconName = getIntent().getStringExtra("iconName");
        String forumIdString = getIntent().getStringExtra("forumId");

        // 检查 forumIdString 是否为 null 或空
        if (forumIdString != null && !forumIdString.isEmpty()) {
            try {
                forumId = Integer.parseInt(forumIdString);
            } catch (NumberFormatException e) {
                Log.e("ColumnActivity", "Invalid forumId format", e);
                Toast.makeText(this, "Invalid Forum ID format.", Toast.LENGTH_SHORT).show();
                forumId = -1; // 设置一个无效的默认值
            }
        } else {
            Toast.makeText(this, "Forum ID is missing or not available.", Toast.LENGTH_SHORT).show();
            forumId = -1; // 设置一个无效的默认值
        }

        middleTextView.setText(iconName);

        // 初始化适配器
        adapter = new ColumnAdapter(this, columnList, forumId); // 传递 forumId
        recyclerView.setAdapter(adapter);

        // 加载数据
        loadColumnData(iconName);

        // 显示关注人数
        updateFollowNum();

        // 关注按钮点击事件
        subscribeButton.setOnClickListener(v -> subscribeToForum());

        // 添加栏目按钮点击事件
        addColumnButton.setOnClickListener(v -> {
            Intent intent = new Intent(ColumnActivity.this, AddPostActivity.class);
            intent.putExtra("forumId", String.valueOf(forumId)); // 发送字符串格式的 forumId
            startActivityForResult(intent, ADD_COLUMN_REQUEST_CODE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COLUMN_REQUEST_CODE && resultCode == RESULT_OK) {
            // 重新加载栏目数据
            loadColumnData(middleTextView.getText().toString());
        }
    }

    private void loadColumnData(String iconName) {
        columnList.clear();
        if (forumId == -1) { // 检查无效的 forumId
            Toast.makeText(this, "Forum ID is missing.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM post WHERE forum_id=?", new String[]{String.valueOf(forumId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String postId = cursor.getString(cursor.getColumnIndex("post_id"));
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                    @SuppressLint("Range") String publishTime = cursor.getString(cursor.getColumnIndex("publish_time"));
                    Post post = new Post(postId, title, forumId, publishTime);
                    columnList.add(post);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No posts available.", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Error loading data.", Toast.LENGTH_SHORT).show();
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Adapter is not initialized.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFollowNum() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM follow_forum WHERE forum_id=?", new String[]{String.valueOf(forumId)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int followCount = cursor.getInt(0);
                followNumTextView.setText("关注人数: " + followCount);
            }
            cursor.close();
        }
    }

    private void subscribeToForum() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // 从 SharedPreferences 获取用户 ID
        String userId = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("currentUserId", null);

        if (userId == null) {
            Toast.makeText(this, "用户未登录", Toast.LENGTH_SHORT).show();
            return;
        }

        values.put("user_id", userId);
        values.put("forum_id", forumId);

        long result = db.insert("follow_forum", null, values);
        if (result != -1) {
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
            updateFollowNum();
        } else {
            Toast.makeText(this, "关注失败", Toast.LENGTH_SHORT).show();
        }
    }
}
