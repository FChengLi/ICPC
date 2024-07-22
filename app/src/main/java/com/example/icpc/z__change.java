package com.example.icpc;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class z__change extends AppCompatActivity {

    private EditText editTextForumId, editTextSection, editTextForumName, editTextForumDescription, editTextCreatorId, editTextTotalPosts, editTextUsers, editTextCreationTime;
    private Button buttonChange;

    private zDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z__change);

        dbHelper = new zDatabaseHelper(this);

        editTextForumId = findViewById(R.id.a);
        editTextSection = findViewById(R.id.b);
        editTextForumName = findViewById(R.id.c);
        editTextUsers = findViewById(R.id.g);
        buttonChange = findViewById(R.id.loginButton);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateForum();
            }
        });
    }

    private void updateForum() {
        String forumIdStr = editTextForumId.getText().toString();
        if (forumIdStr.isEmpty()) {
            showErrorDialog("论坛ID不能为空");
            return;
        }

        int forumId = 0;
        try {
            forumId = Integer.parseInt(forumIdStr);
        } catch (NumberFormatException e) {
            Log.e("z__change", "Invalid number format in Forum ID field", e);
            showErrorDialog("论坛ID格式无效");
            return;
        }

        String section = editTextSection.getText().toString();
        String forumName = editTextForumName.getText().toString();
        int users = Integer.parseInt(editTextUsers.getText().toString());

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(zDatabaseHelper.COLUMN_SECTION, section);
            values.put(zDatabaseHelper.COLUMN_FORUM_NAME, forumName);
            values.put(zDatabaseHelper.COLUMN_USERS, users);

            int rowsAffected = db.update(zDatabaseHelper.TABLE_FORUM, values, zDatabaseHelper.COLUMN_FORUM_ID + " = ?", new String[]{String.valueOf(forumId)});
            if (rowsAffected > 0) {
                showSuccessDialog("论坛更新成功");
            } else {
                showErrorDialog("未找到匹配的论坛");
            }
        } catch (SQLException e) {
            Log.e("z__change", "Database error", e);
            showErrorDialog("更新论坛时发生错误");
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("更新失败")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    private void showSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("更新成功")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
}
