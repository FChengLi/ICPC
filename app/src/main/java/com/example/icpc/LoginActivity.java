package com.example.icpc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.icpc.database.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private ImageView clearImageView;
    private ImageView eyeImageView;
    private TextView forgotPasswordTextView;
    private boolean isPasswordVisible = false;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化UI组件
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        clearImageView = findViewById(R.id.clear);
        eyeImageView = findViewById(R.id.eye_close);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        // 初始化UserDAO，用于数据库操作
        userDAO = new UserDAO(this);
        userDAO.open();

        // 添加手机号输入框的文本变化监听器
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 11) {
                    phoneNumberEditText.setError("手机号必须是11位数字");
                } else {
                    phoneNumberEditText.setError(null);
                }
            }
        });

        // 登录按钮点击事件
        loginButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // 验证手机号长度
            if (phoneNumber.length() != 11) {
                Toast.makeText(LoginActivity.this, "手机号错误", Toast.LENGTH_SHORT).show();
            } else {
                // 验证用户
                if (userDAO.checkUser(phoneNumber, password)) {
                    // 用户验证成功，跳转到MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // 用户验证失败，显示错误信息
                    Toast.makeText(LoginActivity.this, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 清除密码输入框内容
        clearImageView.setOnClickListener(v -> passwordEditText.setText(""));

        // 显示或隐藏密码
        eyeImageView.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setInputType(0x81); // textPassword
                eyeImageView.setImageResource(R.drawable.eye_close);
            } else {
                passwordEditText.setInputType(0x90); // textVisiblePassword
                eyeImageView.setImageResource(R.drawable.eye_open);
            }
            isPasswordVisible = !isPasswordVisible;
            passwordEditText.setSelection(passwordEditText.getText().length());
        });

        // 跳转到注册页面
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // 跳转到找回密码页面
        forgotPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RetrieveActivity.class);
            startActivity(intent);
        });
    }

    // 关闭数据库连接
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
