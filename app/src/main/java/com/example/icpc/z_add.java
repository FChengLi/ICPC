package com.example.icpc;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class z_add extends AppCompatActivity {

    private EditText editTextId, editTextTitle, editTextDescription, editTextAuthor, editTextFilePath, editTextCover, editTextFavorites;
    private Button buttonAdd;

    private zDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_add);

        dbHelper = new zDatabaseHelper(this);

        editTextId = findViewById(R.id.a);
        editTextTitle = findViewById(R.id.b);
        editTextDescription = findViewById(R.id.c);
        editTextAuthor = findViewById(R.id.d);
        editTextFilePath = findViewById(R.id.e);
        editTextCover = findViewById(R.id.f);
        editTextFavorites = findViewById(R.id.g);
        buttonAdd = findViewById(R.id.loginButton);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecord();
            }
        });

        // 打开数据库连接并保持一段时间以便调试
        keepDatabaseOpenForDebugging();
    }

    private void addRecord() {
        String idStr = editTextId.getText().toString();
        if (idStr.isEmpty()) {
            showErrorDialog("ID不能为空");
            return;
        }

        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Log.e("z_add", "Invalid number format in ID field", e);
            showErrorDialog("ID格式无效");
            return;
        }

        if (!isIdUnique(id)) {
            showErrorDialog("ID已存在");
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
            Log.e("z_add", "Invalid number format in favorites field", e);
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(zDatabaseHelper.COLUMN_ID, id);
            values.put(zDatabaseHelper.COLUMN_TITLE, title);
            values.put(zDatabaseHelper.COLUMN_DESCRIPTION, description);
            values.put(zDatabaseHelper.COLUMN_AUTHOR, author);
            values.put(zDatabaseHelper.COLUMN_FILE_PATH, filePath);
            values.put(zDatabaseHelper.COLUMN_COVER, cover);
            values.put(zDatabaseHelper.COLUMN_FAVORITES, favorites);

            long newRowId = db.insert(zDatabaseHelper.TABLE_VIDEO, null, values);
            if (newRowId == -1) {
                Log.e("z_add", "Error with saving record");
            } else {
                Log.d("z_add", "Record saved with row id: " + newRowId);
            }
        } catch (SQLException e) {
            Log.e("z_add", "Database error", e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private boolean isIdUnique(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String[] columns = {zDatabaseHelper.COLUMN_ID};
            String selection = zDatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};
            cursor = db.query(zDatabaseHelper.TABLE_VIDEO, columns, selection, selectionArgs, null, null, null);

            return !cursor.moveToFirst();
        } catch (SQLException e) {
            Log.e("z_add", "Database error", e);
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
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            db.close();
                        }
                    },
                    60000 // 延迟60秒关闭数据库连接
            );
        }
    }
}
