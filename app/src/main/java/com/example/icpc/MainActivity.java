package com.example.icpc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 模拟检查用户登录状态
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoggedIn = checkLoginStatus();
                if (isLoggedIn) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000); // 延迟2秒
    }

    private boolean checkLoginStatus() {
        // 在这里检查用户的登录状态
        // 返回true表示用户已登录，返回false表示用户未登录
        return false; // 默认未登录
    }
}