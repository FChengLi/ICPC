package com.example.icpc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.MediaController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



public class History_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载 fragment 的布局
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        TextView textview = view.findViewById(R.id.textView1);
        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "huisong.ttf");
        textview.setTypeface(typeface1);
        // 设置自定义字体
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "ruiti.ttf");

        // 设置需要修改的 TextView
        TextView textview10 = view.findViewById(R.id.textView10);
        textview10.setTypeface(typeface, Typeface.BOLD);
        textview10.setTextColor(Color.RED);

        TextView textview2 = view.findViewById(R.id.textView2);
        textview2.setTypeface(typeface, Typeface.BOLD);
        textview2.setTextColor(Color.RED);

        TextView textview3 = view.findViewById(R.id.textView3);
        textview3.setTypeface(typeface, Typeface.BOLD);
        textview3.setTextColor(Color.RED);

        TextView textview4 = view.findViewById(R.id.textView4);
        textview4.setTypeface(typeface, Typeface.BOLD);
        textview4.setTextColor(Color.RED);

        TextView textview5 = view.findViewById(R.id.textView5);
        textview5.setTypeface(typeface, Typeface.BOLD);
        textview5.setTextColor(Color.RED);

        TextView textview6 = view.findViewById(R.id.textView6);
        textview6.setTypeface(typeface, Typeface.BOLD);
        textview6.setTextColor(Color.RED);

        TextView textview7 = view.findViewById(R.id.textView7);
        textview7.setTypeface(typeface, Typeface.BOLD);
        textview7.setTextColor(Color.RED);

        // 找到 ImageView 并设置点击事件
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

            // 找到 ImageView 并设置点击事件
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
                    finish(); // 返回到上一个活动
                }
            });
        }
    }

    public static class DsDemo1DetailActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ds_demo1_detail);

            // 找到 ImageView 并设置点击事件
            ImageView backButton = findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // 返回到上一个活动
                }
            });

            // 初始化 VideoView 和 MediaController
            VideoView videoView = findViewById(R.id.videoView);
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ds;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);

            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);

            videoView.start();
        }
    }
}
