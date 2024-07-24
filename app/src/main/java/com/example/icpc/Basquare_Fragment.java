package com.example.icpc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.icpc.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basquare_Fragment extends Fragment {

    private RecyclerView leftRecyclerView;
    private ViewPager2 viewPager;
    private List<String> boardList;
    private List<ContentFragment> fragmentList;
    private ContentPagerAdapter pagerAdapter;
    private DatabaseHelper dbHelper; // 添加数据库助手

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getContext()); // 初始化数据库助手
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basquare, container, false);

        leftRecyclerView = view.findViewById(R.id.leftRecyclerView);
        viewPager = view.findViewById(R.id.viewPager);

        // 从数据库读取板块数据
        boardList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        loadBoardData();

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

    private void loadBoardData() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        // 创建一个映射来将数据库中的 section 值转换为显示名称
        Map<String, String> sectionNameMap = new HashMap<>();
        sectionNameMap.put("1", "政治");
        sectionNameMap.put("2", "历史");
        sectionNameMap.put("3", "党员");

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT plate_id FROM forum GROUP BY plate_id", null);
            while (cursor.moveToNext()) {
                String section = cursor.getString(0);
                String displayName = sectionNameMap.getOrDefault(section, section); // 使用映射来获取显示名称

                boardList.add(displayName); // 根据 section 生成板块名称
                fragmentList.add(ContentFragment.newInstance(section));
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
    }

}
