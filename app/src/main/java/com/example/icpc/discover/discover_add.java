package com.example.icpc.discover;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.R;
import com.example.icpc.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class discover_add extends AppCompatActivity {

    private EditText articleIdEditText;
    private EditText titleEditText;
    private EditText contentEditText;
    private EditText sourceEditText;
    private Button addButton;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_add);

        articleIdEditText = findViewById(R.id.articleid);
        titleEditText = findViewById(R.id.title);
        contentEditText = findViewById(R.id.content);
        sourceEditText = findViewById(R.id.source);
        addButton = findViewById(R.id.add);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArticle();
            }
        });
    }

    private void addArticle() {
        String articleId = articleIdEditText.getText().toString().trim();
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String source = sourceEditText.getText().toString().trim();
        String publishTime = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(new Date());

        if (articleId.isEmpty() || title.isEmpty() || content.isEmpty() || source.isEmpty()) {
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put("information_id", articleId);
            values.put("title", title);
            values.put("content_text", content);
            values.put("author", source);
            values.put("publish_time", publishTime);

            long newRowId = db.insert("information", null, values);
            if (newRowId != -1) {
                Toast.makeText(this, "文章添加成功", Toast.LENGTH_SHORT).show();
                finish(); // 关闭当前 Activity
            } else {
                Toast.makeText(this, "文章添加失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
