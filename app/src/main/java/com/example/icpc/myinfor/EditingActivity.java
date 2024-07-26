package com.example.icpc.myinfor;

import android.content.Context;
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

import com.example.icpc.R;
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
        if (userId == null) {
            finish(); // User not logged in, exit the activity
            return;
        }

        UserDAO.User currentUser = userDAO.getUserInfo(userId);

        ImageView return1 = findViewById(R.id.return1);
        return1.setOnClickListener(v -> finish());

        egNickname = findViewById(R.id.egnickname);
        egEmail = findViewById(R.id.egemail);
        egPhone = findViewById(R.id.egphone);
        avatar = findViewById(R.id.avatar);

        if (currentUser != null) {
            egNickname.setText(currentUser.getNickname());
            egEmail.setText(currentUser.getEmail());
            egPhone.setText(userId); // Display user ID as phone

            // Set the user's avatar if available
            if (currentUser.getAvatarUri() != null) {
                Uri avatarUri = Uri.parse(currentUser.getAvatarUri());
                avatar.setImageURI(avatarUri);
                // Optionally update SharedPreferences with the current avatar URI
                updateSharedPreferences("avatarUri", avatarUri.toString());
            }
        } else {
            // Try to load avatar from SharedPreferences if currentUser is null
            String savedAvatarUri = getSavedAvatarUri();
            if (savedAvatarUri != null) {
                avatar.setImageURI(Uri.parse(savedAvatarUri));
            }
        }

        ImageView imageView = findViewById(R.id.imageView);

        View.OnClickListener openGalleryListener = v -> openGallery();
        avatar.setOnClickListener(openGalleryListener);
        imageView.setOnClickListener(openGalleryListener);

        ImageView go2 = findViewById(R.id.go2);
        go2.setOnClickListener(v -> showEditNicknameDialog());

        ImageView go3 = findViewById(R.id.go3);
        go3.setOnClickListener(v -> showEditEmailDialog());

        // Other click listeners if needed
    }

    private void showEditNicknameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑昵称");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("确认", (dialog, which) -> {
            String newNickname = input.getText().toString();
            if (userDAO.updateNickname(userId, newNickname)) {
                egNickname.setText(newNickname);
                updateSharedPreferences("currentNickname", newNickname); // Optionally update SharedPreferences
            } else {
                // Handle update failure
                showErrorDialog("更新失败", "无法更新昵称，请稍后重试。");
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showEditEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑邮箱");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("确认", (dialog, which) -> {
            String newEmail = input.getText().toString();
            if (userDAO.updateEmail(userId, newEmail)) {
                egEmail.setText(newEmail);
                updateSharedPreferences("currentEmail", newEmail); // Optionally update SharedPreferences
            } else {
                // Handle update failure
                showErrorDialog("更新失败", "无法更新邮箱，请稍后重试。");
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

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

            // Update the avatar URI in the database and SharedPreferences
            if (userDAO.updateAvatar(userId, imageUri.toString())) {
                updateSharedPreferences("avatarUri", imageUri.toString()); // Update SharedPreferences
            } else {
                // Handle update failure
                showErrorDialog("更新失败", "无法更新头像，请稍后重试。");
            }
        }
    }

    private String getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("currentUserId", null);
    }

    private void updateSharedPreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getSavedAvatarUri() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("avatarUri", null);
    }

    private void showErrorDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
