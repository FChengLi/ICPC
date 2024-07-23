package com.example.icpc;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        Discover_image_Adapter discoverImageAdapter = new Discover_image_Adapter(imageList);
        viewPager.setAdapter(discoverImageAdapter);

        // 启动轮播
        handler.postDelayed(runnable, DELAY_MS);

        // 初始化文章列表
        List<discover_article> articleList = new ArrayList<>();
        articleList.add(new discover_article(1, "Author 1", "Time 1", "Content 1", 10, 20, "Title 1", "Source 1", "Date 1", R.drawable.image1));
        articleList.add(new discover_article(2, "Author 2", "Time 2", "Content 2", 15, 25, "Title 2", "Source 2", "Date 2", R.drawable.image2));
        articleList.add(new discover_article(3, "Author 3", "Time 3", "Content 3", 20, 30, "Title 3", "Source 3", "Date 3", R.drawable.image3));
        articleList.add(new discover_article(3, "Author 3", "Time 3", "Content 3", 20, 30, "Title 3", "Source 3", "Date 3", R.drawable.image3));
        articleList.add(new discover_article(3, "Author 3", "Time 3", "Content 3", 20, 30, "Title 3", "Source 3", "Date 3", R.drawable.image3));
        articleList.add(new discover_article(3, "Author 3", "Time 3", "Content 3", 20, 30, "Title 3", "Source 3", "Date 3", R.drawable.image3));
        articleList.add(new discover_article(3, "Author 3", "Time 3", "Content 3", 20, 30, "Title 3", "Source 3", "Date 3", R.drawable.image3));

        // 设置RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArticleAdapter articleAdapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(articleAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 停止轮播
        handler.removeCallbacks(runnable);
    }
}
