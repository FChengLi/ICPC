package com.example.icpc.login;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.R;

public class z__add extends AppCompatActivity {

    private EditText editTextForumId, editTextSection, editTextForumName, editTextForumDescription, editTextCreatorId, editTextTotalPosts, editTextUsers, editTextCreationTime;
    private Button buttonAdd;


    private com.example.icpc.database.DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z__add);

        dbHelper = new com.example.icpc.database.DatabaseHelper(this);

        editTextForumId = findViewById(R.id.a);
        editTextSection = findViewById(R.id.b);
        editTextForumName = findViewById(R.id.c);
        editTextUsers = findViewById(R.id.g);
        buttonAdd = findViewById(R.id.loginButton);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addForum();
            }
        });

        // 打开数据库连接并保持一段时间以便调试
        keepDatabaseOpenForDebugging();
    }

    private void addForum() {
        String forumIdStr = editTextForumId.getText().toString();
        if (forumIdStr.isEmpty()) {
            showErrorDialog("论坛ID不能为空");
            return;
        }

        int forumId = 0;
        try {
            forumId = Integer.parseInt(forumIdStr);
        } catch (NumberFormatException e) {
            Log.e("z__add", "Invalid number format in Forum ID field", e);
            showErrorDialog("论坛ID格式无效");
            return;
        }

        if (!isForumIdUnique(forumId)) {
            showErrorDialog("论坛ID已存在");
            return;
        }

        String section = editTextSection.getText().toString();
        String forumName = editTextForumName.getText().toString();
        int users = Integer.parseInt(editTextUsers.getText().toString());
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("forum_id", forumId);
            values.put("plate_id", section);
            values.put("forum_name", forumName);
            values.put("follow_count", users);

            long newRowId = db.insert("forum", null, values);
            if (newRowId == -1) {
                Log.e("z__add", "Error with saving forum");
            } else {
                Log.d("z__add", "Forum saved with row id: " + newRowId);
            }
        } catch (SQLException e) {
            Log.e("z__add", "Database error", e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private boolean isForumIdUnique(int forumId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String[] columns = {"forum_id"};
            String selection = "forum_id" + " = ?";
            String[] selectionArgs = {String.valueOf(forumId)};
            cursor = db.query("forum", columns, selection, selectionArgs, null, null, null);

            return !cursor.moveToFirst();
        } catch (SQLException e) {
            Log.e("z__add", "Database error", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("插入失败")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    private void keepDatabaseOpenForDebugging() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            // 这里可以执行一些简单的查询或操作
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            if (cursor != null) {
                cursor.moveToFirst();
                cursor.close();
            }
        } finally {
            // 延迟关闭数据库连接，便于调试
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (db != null && db.isOpen()) {
                        db.close();
                    }
                }
            }, 60000); // 延迟60秒关闭数据库连接
        }
    }
}
