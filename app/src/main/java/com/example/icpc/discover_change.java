package com.example.icpc;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.database.DatabaseHelper;

public class discover_change extends AppCompatActivity {

    private EditText articleIdEditText;
    private EditText titleEditText;
    private EditText contentEditText;
    private EditText sourceEditText;
    private Button changeButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_change);

        articleIdEditText = findViewById(R.id.articleid);
        titleEditText = findViewById(R.id.title);
        contentEditText = findViewById(R.id.content);
        sourceEditText = findViewById(R.id.source);
        changeButton = findViewById(R.id.add);

        dbHelper = new DatabaseHelper(this);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeArticle();
            }
        });
    }

    private void changeArticle() {
        String articleId = articleIdEditText.getText().toString().trim();
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String source = sourceEditText.getText().toString().trim();

        if (articleId.isEmpty()) {
            Toast.makeText(this, "请填写文章ID", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (!title.isEmpty()) {
            values.put("title", title);
        }
        if (!content.isEmpty()) {
            values.put("content_text", content);
        }
        if (!source.isEmpty()) {
            values.put("author", source);
        }

        if (values.size() > 0) {
            int rowsAffected = db.update("information", values, "information_id=?", new String[]{articleId});
            if (rowsAffected > 0) {
                Toast.makeText(this, "文章修改成功", Toast.LENGTH_SHORT).show();
                finish(); // 关闭当前 Activity
            } else {
                Toast.makeText(this, "未找到对应的文章ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "未修改任何字段", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
