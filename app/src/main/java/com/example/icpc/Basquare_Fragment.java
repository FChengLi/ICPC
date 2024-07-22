package com.example.icpc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Basquare_Fragment extends Fragment {

    private RecyclerView leftRecyclerView;
    private ViewPager2 viewPager;
    private List<String> boardList;
    private List<ContentFragment> fragmentList;
    private ContentPagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basquare, container, false);

        leftRecyclerView = view.findViewById(R.id.leftRecyclerView);
        viewPager = view.findViewById(R.id.viewPager);

        // 初始化板块数据
        boardList = new ArrayList<>();
        boardList.add("板块1");
        boardList.add("板块2");
        boardList.add("板块3");
        // 更多板块...

        // 初始化Fragment列表
        fragmentList = new ArrayList<>();
        for (String board : boardList) {
            fragmentList.add(ContentFragment.newInstance(board));
        }

        // 设置RecyclerView
        BoardAdapter boardAdapter = new BoardAdapter(boardList);
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leftRecyclerView.setAdapter(boardAdapter);

        // 设置ViewPager2
        pagerAdapter = new ContentPagerAdapter(requireActivity(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        // 设置板块点击事件
        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewPager.setCurrentItem(position);
            }
        });

        return view;
    }
}
