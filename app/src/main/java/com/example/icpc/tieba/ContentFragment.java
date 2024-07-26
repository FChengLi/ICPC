package com.example.icpc.tieba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.R;
import com.example.icpc.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {

    private static final String ARG_BOARD_TYPE = "board_type";
    private String boardType;
    private DatabaseHelper dbHelper; // 添加数据库助手

    public static ContentFragment newInstance(String boardType) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOARD_TYPE, boardType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.contentRecyclerView);

        if (getArguments() != null) {
            boardType = getArguments().getString(ARG_BOARD_TYPE);
        }

        // 初始化数据库助手
        dbHelper = new DatabaseHelper(getContext());

        // 从数据库读取图标数据
        List<IconItem> iconList = new ArrayList<>();
        loadIconData(iconList);

        // 创建适配器
        BaIconAdapter adapter = new BaIconAdapter(iconList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        // 设置图标点击事件监听器
        adapter.setOnItemClickListener(iconItem -> {
            Intent intent = new Intent(getActivity(), ColumnActivity.class);
            // 传递数据到 ColumnActivity，例如图标名称或ID
            intent.putExtra("iconName", iconItem.getIconName());
            intent.putExtra("forumId", iconItem.getForumId()); // 传递 forumId
            startActivity(intent);
        });

        return view;
    }

    private void loadIconData(List<IconItem> iconList) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM forum WHERE plate_id = ?";
            cursor = db.rawQuery(query, new String[]{boardType});
            while (cursor.moveToNext()) {
                int iconResId = R.drawable.ic_launcher_background; // 示例图标资源ID
                @SuppressLint("Range") String iconName = cursor.getString(cursor.getColumnIndex("forum_name"));
                @SuppressLint("Range") String forumId = cursor.getString(cursor.getColumnIndex("forum_id"));
                iconList.add(new IconItem(iconResId, iconName, forumId)); // 包含论坛 ID
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
    }
}
