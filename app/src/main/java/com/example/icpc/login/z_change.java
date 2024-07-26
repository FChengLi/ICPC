package com.example.icpc.login;

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

import com.example.icpc.R;
import com.example.icpc.database.DatabaseHelper;

public class z_change extends AppCompatActivity {

    private EditText editTextId, editTextTitle, editTextDescription, editTextAuthor, editTextFilePath, editTextCover, editTextFavorites;
    private Button buttonChange;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_change);

        dbHelper = new DatabaseHelper(this);

        editTextId = findViewById(R.id.a);
        editTextTitle = findViewById(R.id.b);
        editTextDescription = findViewById(R.id.c);
        editTextAuthor = findViewById(R.id.d);
        editTextFilePath = findViewById(R.id.e);
        editTextCover = findViewById(R.id.f);
        editTextFavorites = findViewById(R.id.g);
        buttonChange = findViewById(R.id.loginButton);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecord();
            }
        });
    }

    private void updateRecord() {
        String idStr = editTextId.getText().toString();
        if (idStr.isEmpty()) {
            showErrorDialog("ID不能为空");
            return;
        }

        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Log.e("z_change", "Invalid number format in ID field", e);
            showErrorDialog("ID格式无效");
            return;
        }

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String author = editTextAuthor.getText().toString();
        String filePath = editTextFilePath.getText().toString();
        String cover = editTextCover.getText().toString();
        int favorites = 0;

        try {
            favorites = Integer.parseInt(editTextFavorites.getText().toString());
        } catch (NumberFormatException e) {
            Log.e("z_change", "Invalid number format in favorites field", e);
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("description", description);
            values.put("author", author);
            values.put("file_path", filePath);
            values.put("cover", cover);
            values.put("favorites_count", favorites);

            int rowsAffected = db.update("video", values, "video_id" + " = ?", new String[]{String.valueOf(id)});
            if (rowsAffected > 0) {
                showSuccessDialog("记录更新成功");
            } else {
                showErrorDialog("未找到匹配的记录");
            }
        } catch (SQLException e) {
            Log.e("z_change", "Database error", e);
            showErrorDialog("更新记录时发生错误");
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
