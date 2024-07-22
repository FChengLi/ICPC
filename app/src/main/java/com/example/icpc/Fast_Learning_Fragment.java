package com.example.icpc;

// Fast_Learning_Fragment.java

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class Fast_Learning_Fragment extends Fragment {

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fast_learning, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // 创建数据项列表
        List<DataItem> dataItemList = new ArrayList<>();

        // 获取可读的数据库实例
        zDatabaseHelper dbHelper = new zDatabaseHelper(getContext()); // 替换为正确的上下文
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 执行查询语句
        String query = "SELECT * FROM video"; // 替换为你的表格名
        Cursor cursor = db.rawQuery(query, null);

        // 遍历查询结果
        if (cursor.moveToFirst()) {
            do {
                DataItem dataItem = new DataItem();
                // 从游标中获取数据并设置到 DataItem 对象中
                dataItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                dataItem.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                dataItem.setCoverpath(cursor.getString(cursor.getColumnIndex("cover")));
                dataItem.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                dataItem.setFilepath(cursor.getString(cursor.getColumnIndex("file_path")));

                // 设置其他属性值

                // 将数据项添加到列表中
                dataItemList.add(dataItem);

            } while (cursor.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor.close();
        dbHelper.close();

        // 创建适配器并设置给 RecyclerView
        VideoAdapter adapter = new VideoAdapter(dataItemList);
        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DataItem item = dataItemList.get(position);

                Intent intent = new Intent(getContext(), Vedio_DetailActivity.class);
                intent.putExtra("data_item", item); // 确保这里的键值是 "data_item"
                startActivity(intent);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}