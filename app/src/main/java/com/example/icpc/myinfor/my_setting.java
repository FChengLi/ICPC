package com.example.icpc.myinfor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.login.LoginActivity;
import com.example.icpc.R;

public class my_setting extends AppCompatActivity {

    private ImageView return1;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting);

        // 初始化UI组件
        return1 = findViewById(R.id.return1);
        logoutButton = findViewById(R.id.loginButton);

        // 设置返回按钮点击事件
        return1.setOnClickListener(v -> finish());

        // 设置退出登录按钮点击事件
        logoutButton.setOnClickListener(v -> {
            // 清除SharedPreferences中的用户数据
            clearUserPreferences();

            // 跳转到LoginActivity
            Intent intent = new Intent(my_setting.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 清除任务栈并启动新的任务
            startActivity(intent);
            finish();
        });
    }

    private void clearUserPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // 清除所有数据
        editor.apply();
    }
}
