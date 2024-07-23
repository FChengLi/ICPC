package com.example.icpc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
    private List<Column> columnList;
    private TextView middleTextView;
    private Button subscribeButton;
    private DatabaseHelper dbHelper;
    private String forumId; // 当前论坛的 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);

        middleTextView = findViewById(R.id.middle);
        subscribeButton = findViewById(R.id.subscribebutton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        columnList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        // 获取传递的数据
        String iconName = getIntent().getStringExtra("iconName");
        forumId = getIntent().getStringExtra("forumId"); // 获取传递的论坛 ID

        // 设置论坛名称
        middleTextView.setText(iconName);

        // 根据 iconName 或其他标识来加载相应的数据
        loadColumnData(iconName);

        adapter = new ColumnAdapter(this, columnList);
        recyclerView.setAdapter(adapter);

        // 关注按钮点击事件
        subscribeButton.setOnClickListener(v -> subscribeToForum());
    }

    private void loadColumnData(String iconName) {
        // 根据 iconName 从数据库或其他来源加载数据
        columnList.add(new Column(1, iconName + " Column 1", "image_url_1", "Source 1", "2022-05-20"));
        columnList.add(new Column(2, iconName + " Column 2", "image_url_2", "Source 2", "2022-05-21"));
        // 添加更多的数据
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
        } else {
            Toast.makeText(this, "关注失败", Toast.LENGTH_SHORT).show();
        }
    }
}
