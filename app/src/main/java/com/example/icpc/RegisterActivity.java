package com.example.icpc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.icpc.database.UserDAO;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText phoneNumberEditText, passwordEditText1, passwordEditText2, verificationEditText;
    private Button verificationButton, registerButton;
    private ImageView eyeClose1, eyeClose2, backImageView;
    private boolean isPasswordVisible = false;
    private int verificationCode;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化UI组件
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText1 = findViewById(R.id.passwordEditText1);
        passwordEditText2 = findViewById(R.id.passwordEditText2);
        verificationEditText = findViewById(R.id.verificationEditText);
        verificationButton = findViewById(R.id.verificationButton);
        registerButton = findViewById(R.id.registerButton);
        eyeClose1 = findViewById(R.id.eye_close1);
        eyeClose2 = findViewById(R.id.eye_close2);
        backImageView = findViewById(R.id.back);

        // 初始化UserDAO，用于数据库操作
        userDAO = new UserDAO(this);
        userDAO.open();

        // 返回按钮点击事件，关闭当前Activity
        backImageView.setOnClickListener(v -> finish());

        // 手机号输入框的文本变化监听器
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

        // 密码可见性切换的点击事件
        View.OnClickListener eyeClickListener = v -> {
            if (isPasswordVisible) {
                passwordEditText1.setInputType(0x81); // textPassword
                passwordEditText2.setInputType(0x81); // textPassword
                eyeClose1.setImageResource(R.drawable.eye_open);
                eyeClose2.setImageResource(R.drawable.eye_open);
            } else {
                passwordEditText1.setInputType(0x90); // textVisiblePassword
                passwordEditText2.setInputType(0x90); // textVisiblePassword
                eyeClose1.setImageResource(R.drawable.eye_close);
                eyeClose2.setImageResource(R.drawable.eye_close);
            }
            isPasswordVisible = !isPasswordVisible;
            passwordEditText1.setSelection(passwordEditText1.getText().length());
            passwordEditText2.setSelection(passwordEditText2.getText().length());
        };

        eyeClose1.setOnClickListener(eyeClickListener);
        eyeClose2.setOnClickListener(eyeClickListener);

        // 获取验证码按钮点击事件
        verificationButton.setOnClickListener(v -> {
            verificationCode = new Random().nextInt(8999) + 1000;
            new AlertDialog.Builder(this)
                    .setTitle("验证码")
                    .setMessage("您的验证码是：" + verificationCode)
                    .setPositiveButton("确定", null)
                    .show();

            // 禁用获取验证码按钮并开始倒计时
            verificationButton.setEnabled(false);
            new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    verificationButton.setText(millisUntilFinished / 1000 + "秒");
                }

                public void onFinish() {
                    verificationButton.setText("获取验证码");
                    verificationButton.setEnabled(true);
                }
            }.start();
        });

        // 注册按钮点击事件
        registerButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            String password1 = passwordEditText1.getText().toString();
            String password2 = passwordEditText2.getText().toString();
            String enteredCode = verificationEditText.getText().toString();

            // 验证两次输入的密码是否一致
            if (!password1.equals(password2)) {
                Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                // 验证输入的验证码是否正确
            } else if (enteredCode.isEmpty() || Integer.parseInt(enteredCode) != verificationCode) {
                Toast.makeText(RegisterActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                // 将用户信息插入数据库，并跳转到登录页面
            } else {
                if (userDAO.addUser(phoneNumber, password1)) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库连接
        userDAO.close();
    }
}
