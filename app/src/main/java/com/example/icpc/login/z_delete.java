package com.example.icpc.login;

import android.content.DialogInterface;
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

public class z_delete extends AppCompatActivity {

    private EditText editTextId;
    private Button buttonDelete;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_delete);

        dbHelper = new DatabaseHelper(this);

        editTextId = findViewById(R.id.a);
        buttonDelete = findViewById(R.id.loginButton);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteRecord();
            }
        });
    }

    private void confirmDeleteRecord() {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除该记录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteRecord() {
        String idStr = editTextId.getText().toString();
        if (idStr.isEmpty()) {
            showErrorDialog("ID不能为空");
            return;
        }

        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Log.e("z_delete", "Invalid number format in ID field", e);
            showErrorDialog("ID格式无效");
            return;
        }

        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            int rowsAffected = db.delete("video", "video_id" + " = ?", new String[]{String.valueOf(id)});
            if (rowsAffected > 0) {
                showSuccessDialog("记录删除成功");
            } else {
                showErrorDialog("未找到匹配的记录");
            }
        } catch (SQLException e) {
            Log.e("z_delete", "Database error", e);
            showErrorDialog("删除记录时发生错误");
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("删除失败")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    private void showSuccessDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("删除成功")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }
}
