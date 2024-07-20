package com.example.icpc;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Discover_Fragment extends Fragment {
    private ViewPager2 viewPager;
    private List<Integer> imageList = new ArrayList<>();
    private Handler handler = new Handler();
    private int currentPage = 0;
    private final long DELAY_MS = 3000; // 轮播间隔时间
    private Runnable runnable = new Runnable() {
        public void run() {
            if (currentPage == imageList.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
            handler.postDelayed(this, DELAY_MS);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        viewPager = view.findViewById(R.id.slideshow);

        // 添加轮播图图片资源
        imageList.add(R.drawable.image1);
        imageList.add(R.drawable.image2);
        imageList.add(R.drawable.image3);

        // 创建适配器
        Discover_image_Adapter discoverimageAdapter = new Discover_image_Adapter(imageList);
        viewPager.setAdapter(discoverimageAdapter);

        // 启动轮播
        handler.postDelayed(runnable, DELAY_MS);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 停止轮播
        handler.removeCallbacks(runnable);
    }
}
