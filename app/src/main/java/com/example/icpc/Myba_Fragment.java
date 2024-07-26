package com.example.icpc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Myba_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private MybaForumAdapter adapter;
    private List<String> forumNames;
    private List<Integer> followedForumIds; // 用于存储用户关注的论坛ID列表

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myba, container, false);
        recyclerView = view.findViewById(R.id.myba);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        forumNames = new ArrayList<>();
        followedForumIds = new ArrayList<>(); // 初始化关注的论坛ID列表
        adapter = new MybaForumAdapter(getContext(), forumNames, followedForumIds);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取当前登录用户的 user_id
        String userId = getCurrentUserId();

        // 查询数据库获取关注的论坛名称和对应的论坛ID
        fetchFollowedForums(userId);
    }

    private String getCurrentUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("currentUserId", ""); // 返回当前登录用户的 user_id
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchFollowedForums(String userId) {
        // 查询关注的论坛名称和对应的论坛ID
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询当前用户关注的所有论坛的 forum_id 和对应的论坛名称
        followedForumIds.clear(); // 清空之前的数据
        forumNames.clear(); // 清空之前的数据

        String query = "SELECT forum_id FROM follow_forum WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int forumId = cursor.getInt(cursor.getColumnIndex("forum_id"));
                followedForumIds.add(forumId); // 添加论坛ID到列表中
                String forumName = getForumName(forumId, db);
                forumNames.add(forumName); // 添加论坛名称到列表中
            }
            cursor.close();
        }

        adapter.notifyDataSetChanged();
        db.close();
    }

    @SuppressLint("Range")
    private String getForumName(int forumId, SQLiteDatabase db) {
        String forumName = "";

        String query = "SELECT forum_name FROM forum WHERE forum_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(forumId)});

        if (cursor != null && cursor.moveToFirst()) {
            forumName = cursor.getString(cursor.getColumnIndex("forum_name"));
            cursor.close();
        }

        return forumName;
    }
}