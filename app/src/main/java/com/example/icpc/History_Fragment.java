package com.example.icpc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.MediaController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



public class History_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // 确保引用的是正确的 ImageView ID
        ImageView imageView1 = view.findViewById(R.id.imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public static class DetailActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);

            // 添加 ds_demo1_image 的点击事件
            ImageView dsDemo1Image = findViewById(R.id.ds_demo1_image);
            dsDemo1Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this, DsDemo1DetailActivity.class);
                    startActivity(intent);
                }
            });
            ImageView backButton = findViewById(R.id.back_button1);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // 返回上一个活动
                }
            });
        }
    }

    // 新增 DsDemo1DetailActivity
    public static class DsDemo1DetailActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ds_demo1_detail);

            // 设置返回按钮的点击事件
            ImageView backButton = findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // 返回上一个活动
                }
            });

            // 初始化 VideoView
            VideoView videoView = findViewById(R.id.videoView);
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ds;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);

            // 添加媒体控制器
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);

            videoView.start();
        }
    }
}