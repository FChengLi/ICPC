package com.example.icpc;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.database.DatabaseHelper;

public class discover_delete extends AppCompatActivity {

    private EditText articleIdEditText;
    private TextView titleTextView;
    private TextView sourceTextView;
    private TextView contentTextView;
    private Button selectButton;
    private Button deleteButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_delete);

        articleIdEditText = findViewById(R.id.articleid);
        titleTextView = findViewById(R.id.title);
        sourceTextView = findViewById(R.id.source);
        contentTextView = findViewById(R.id.content);
        selectButton = findViewById(R.id.select);
        deleteButton = findViewById(R.id.add); // 确保按钮ID一致

        dbHelper = new DatabaseHelper(this);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectArticle();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteArticle();
            }
        });
    }

    private void selectArticle() {
        String articleId = articleIdEditText.getText().toString().trim();

        if (articleId.isEmpty()) {
            Toast.makeText(this, "请填写文章ID", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("information", null, "information_id=?", new String[]{articleId}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String source = cursor.getString(cursor.getColumnIndex("author"));
            String content = cursor.getString(cursor.getColumnIndex("content_text"));

            titleTextView.setText(title);
            sourceTextView.setText(source);
            contentTextView.setText(content);

            cursor.close();
        } else {
            Toast.makeText(this, "未找到对应的文章ID", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void confirmDeleteArticle() {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("你确定要删除这篇文章吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteArticle();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteArticle() {
        String articleId = articleIdEditText.getText().toString().trim();

        if (articleId.isEmpty()) {
            Toast.makeText(this, "请填写文章ID", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete("information", "information_id=?", new String[]{articleId});

        if (rowsAffected > 0) {
            Toast.makeText(this, "文章删除成功", Toast.LENGTH_SHORT).show();
            // 清空文本框
            titleTextView.setText("");
            sourceTextView.setText("");
            contentTextView.setText("");
            articleIdEditText.setText("");
        } else {
            Toast.makeText(this, "未找到对应的文章ID", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
