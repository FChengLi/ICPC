package com.example.icpc.fastlearning;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.icpc.R;
import com.example.icpc.database.CommentDatabaseHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Vedio_DetailActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private VideoView videoView;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    private EditText commentEditText;
    private Button commitButton;
    private CommentAdapter commentAdapter;
    private List<CommentItem> commentList;
    private ImageView favoriteIcon;
    private boolean isFavorite = false;
    private DataItem dataItem;
    private CommentDatabaseHelper dbHelper;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail);

        dbHelper = new CommentDatabaseHelper(this);

        // 获取传递的 DataItem 对象
        dataItem = getIntent().getParcelableExtra("data_item");
        if (dataItem != null) {
            Log.d("Vedio_DetailActivity", "Received dataItem: " + dataItem.getTitle());
        } else {
            Log.d("Vedio_DetailActivity", "Received dataItem is null");
        }

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        mediaPlayer = new MediaPlayer();

        videoView.setMediaController(mediaController);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vedio1);
        videoView.setOnPreparedListener(this);

        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);

        viewPager = findViewById(R.id.viewpager);  // 确保viewPager已初始化
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        favoriteIcon = findViewById(R.id.favoriteIcon);
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    favoriteIcon.setImageResource(R.drawable.favorite_icon); // 未收藏图片
                } else {
                    favoriteIcon.setImageResource(R.drawable.favorite_icon_selected); // 收藏后的图片
                }
                isFavorite = !isFavorite; // 切换状态
            }
        });

        // 初始化评论相关视图
        commentEditText = findViewById(R.id.commentEditText);
        commitButton = findViewById(R.id.commit);

        // 初始化评论列表和适配器
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentEditText.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    String currentUsername = getCurrentUsername(); // 获取当前用户名
                    if (currentUsername != null) {
                        CommentItem newCommentItem = new CommentItem();
                        newCommentItem.setComment(commentText);
                        newCommentItem.setUsername(currentUsername); // 设置当前用户名
                        newCommentItem.setVideoId(dataItem.getVideoId()); // 设置视频ID
                        commentList.add(newCommentItem);
                        commentAdapter.notifyItemInserted(commentList.size() - 1);
                        commentEditText.setText("");
                        saveCommentToDatabase(newCommentItem); // 保存评论到数据库
                        updateCommentFragment(commentList); // 更新评论列表
                    } else {
                        // 处理未登录用户的情况
                        Log.d("Vedio_DetailActivity", "User is not logged in");
                    }
                }
            }
        });

        loadComments(); // 加载评论
    }

    private void saveCommentToDatabase(CommentItem comment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommentDatabaseHelper.COLUMN_VIDEO_ID, comment.getVideoId());
        values.put(CommentDatabaseHelper.COLUMN_USERNAME, comment.getUsername());
        values.put(CommentDatabaseHelper.COLUMN_COMMENT, comment.getComment());
        values.put(CommentDatabaseHelper.COLUMN_LIKED, comment.isLiked() ? 1 : 0);
        db.insert(CommentDatabaseHelper.TABLE_COMMENTS, null, values);
    }

    @SuppressLint("Range")
    private void loadComments() {
        commentList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CommentDatabaseHelper.TABLE_COMMENTS, null,
                CommentDatabaseHelper.COLUMN_VIDEO_ID + "=?",
                new String[]{String.valueOf(dataItem.getVideoId())}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CommentItem comment = new CommentItem();
                comment.setUsername(cursor.getString(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_USERNAME)));
                comment.setComment(cursor.getString(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_COMMENT)));
                comment.setLiked(cursor.getInt(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_LIKED)) == 1);
                comment.setVideoId(cursor.getInt(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_VIDEO_ID)));
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        commentAdapter.notifyDataSetChanged(); // 确保更新适配器
    }

    private String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("currentUsername", null);
        if (username == null) {
            Log.d("Vedio_DetailActivity", "currentUsername is null in SharedPreferences");
        } else {
            Log.d("Vedio_DetailActivity", "currentUsername retrieved: " + username);
        }
        return username;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer = mp;
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (IllegalStateException e) {
                // Handle or log the exception as needed
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailPagerAdapter adapter = new DetailPagerAdapter(getSupportFragmentManager(), dataItem);
        viewPager.setAdapter(adapter);
    }

    private void updateCommentFragment(List<CommentItem> comments) {
        DetailPagerAdapter adapter = (DetailPagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            Comment_Fragment commentFragment = (Comment_Fragment) adapter.getCommentFragment();
            if (commentFragment != null) {
                commentFragment.updateComments(comments); // 更新Fragment中的评论列表
            }
        }
    }
}
