package com.example.icpc;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String videopath = "android.resource://" + getPackageName() + "/" + R.raw.vedio1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail);

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        mediaPlayer = new MediaPlayer();

        videoView.setMediaController(mediaController);
        videoView.setVideoPath(videopath);
        videoView.setOnPreparedListener(this);

        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);

        ViewPager viewPager = findViewById(R.id.viewpager);
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
                    CommentItem newCommentItem = new CommentItem();
                    newCommentItem.setComment(commentText);
                    newCommentItem.setUsername("luke");
                    commentList.add(newCommentItem);
                    commentAdapter.notifyItemInserted(commentList.size() - 1);
                    commentEditText.setText("");
                    updateCommentFragment(viewPager, commentList); // 更新评论列表
                }
            }
        });
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
        DetailPagerAdapter adapter = new DetailPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void updateCommentFragment(ViewPager viewPager, List<CommentItem> comments) {
        DetailPagerAdapter adapter = (DetailPagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            int currentPosition = viewPager.getCurrentItem();
            viewPager.setAdapter(null); // 清除适配器
            viewPager.setAdapter(adapter); // 重新设置适配器
            viewPager.setCurrentItem(currentPosition); // 恢复当前位置
            Comment_Fragment commentFragment = (Comment_Fragment) adapter.getCommentFragment();
            if (commentFragment != null) {
                commentFragment.updateComments(comments); // 更新Fragment中的评论列表
            }
        }
    }
}
