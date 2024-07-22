package com.example.icpc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.database.UserDAO;

public class EditingActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView egNickname;
    private TextView egEmail;
    private TextView egPhone;
    private ImageView avatar;
    private UserDAO userDAO;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_fragment);

        userDAO = new UserDAO(this);
        userDAO.open();

        userId = getCurrentUserId();
        UserDAO.User currentUser = userDAO.getUserInfo(userId);

        ImageView return1 = findViewById(R.id.return1);
        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        egNickname = findViewById(R.id.egnickname);
        egEmail = findViewById(R.id.egemail);
        egPhone = findViewById(R.id.egphone);

        if (currentUser != null) {
            egNickname.setText(currentUser.getNickname());
            egEmail.setText(currentUser.getEmail());
        }

        egPhone.setText(userId); // 将用户ID显示为手机号

        avatar = findViewById(R.id.avatar);
        ImageView imageView = findViewById(R.id.imageView);

        View.OnClickListener openGalleryListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        };
        avatar.setOnClickListener(openGalleryListener);
        imageView.setOnClickListener(openGalleryListener);

        ImageView go2 = findViewById(R.id.go2);
        go2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditNicknameDialog();
            }
        });

        ImageView go3 = findViewById(R.id.go3);
        go3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditEmailDialog();
            }
        });

        // 如果还有其他需要点击的图标，类似地设置点击事件监听器
    }

    private void showEditNicknameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑昵称");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNickname = input.getText().toString();
                if (userDAO.updateNickname(userId, newNickname)) {
                    egNickname.setText(newNickname);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showEditEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑邮箱");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newEmail = input.getText().toString();
                if (userDAO.updateEmail(userId, newEmail)) {
                    egEmail.setText(newEmail);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            avatar.setImageURI(imageUri);
        }
    }

    private String getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("currentUserId", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
