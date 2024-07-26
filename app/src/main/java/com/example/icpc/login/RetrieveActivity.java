package com.example.icpc.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.R;
import com.example.icpc.database.UserDAO;
import java.util.Random;

public class RetrieveActivity extends AppCompatActivity {

    private EditText phoneNumberEditText, passwordEditText1, verificationEditText;
    private Button verificationButton, changeButton;
    private ImageView eyeClose1, backImageView;
    private boolean isPasswordVisible = false;
    private int verificationCode;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText1 = findViewById(R.id.passwordEditText1);
        verificationEditText = findViewById(R.id.verificationEditText);
        verificationButton = findViewById(R.id.verificationButton);
        changeButton = findViewById(R.id.changeButton);
        eyeClose1 = findViewById(R.id.eye_close1);
        backImageView = findViewById(R.id.back);

        userDAO = new UserDAO(this);
        userDAO.open();

        backImageView.setOnClickListener(v -> finish());

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

        eyeClose1.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText1.setInputType(0x81); // textPassword
                eyeClose1.setImageResource(R.drawable.eye_open);
            } else {
                passwordEditText1.setInputType(0x90); // textVisiblePassword
                eyeClose1.setImageResource(R.drawable.eye_close);
            }
            isPasswordVisible = !isPasswordVisible;
            passwordEditText1.setSelection(passwordEditText1.getText().length());
        });

        verificationButton.setOnClickListener(v -> {
            verificationCode = new Random().nextInt(8999) + 1000;
            new AlertDialog.Builder(this)
                    .setTitle("验证码")
                    .setMessage("您的验证码是：" + verificationCode)
                    .setPositiveButton("确定", null)
                    .show();

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

        changeButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            String newPassword = passwordEditText1.getText().toString();
            String enteredCode = verificationEditText.getText().toString();

            if (phoneNumber.length() != 11) {
                Toast.makeText(RetrieveActivity.this, "手机号必须是11位数字", Toast.LENGTH_SHORT).show();
            } else if (enteredCode.isEmpty() || Integer.parseInt(enteredCode) != verificationCode) {
                Toast.makeText(RetrieveActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
            } else {
                if (userDAO.updatePassword(phoneNumber, newPassword)) {
                    Toast.makeText(RetrieveActivity.this, "更改密码成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RetrieveActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RetrieveActivity.this, "更改密码失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
