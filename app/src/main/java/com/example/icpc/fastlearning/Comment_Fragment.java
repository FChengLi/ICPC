package com.example.icpc.fastlearning;

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

import com.example.icpc.R;
import com.example.icpc.database.CommentDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Comment_Fragment extends Fragment {
    private static final String ARG_DATA_ITEM = "data_item";
    private List<CommentItem> commentList;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private DataItem dataItem;
    private CommentDatabaseHelper dbHelper;

    public static Comment_Fragment newInstance(DataItem dataItem) {
        Comment_Fragment fragment = new Comment_Fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DATA_ITEM, dataItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataItem = getArguments().getParcelable(ARG_DATA_ITEM);
        }
        dbHelper = new CommentDatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(commentAdapter);

        loadComments(); // 加载评论

        return view;
    }

    public void updateComments(List<CommentItem> comments) {
        commentList.clear();
        commentList.addAll(comments);
        commentAdapter.notifyDataSetChanged();
    }

    @SuppressLint("Range")
    private void loadComments() {
        commentList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CommentDatabaseHelper.TABLE_COMMENTS, null,
                CommentDatabaseHelper.COLUMN_VIDEO_ID + "=?",
                new String[]{String.valueOf(dataItem.getVideoId())}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CommentItem comment = new CommentItem();
                comment.setUsername(cursor.getString(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_USERNAME)));
                comment.setComment(cursor.getString(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_COMMENT)));
                comment.setLiked(cursor.getInt(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_LIKED)) == 1);
                comment.setVideoId(cursor.getInt(cursor.getColumnIndex(CommentDatabaseHelper.COLUMN_VIDEO_ID)));
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        commentAdapter.notifyDataSetChanged(); // 确保更新适配器
    }

    private String getCurrentUserId() {
        // 使用上下文对象获取 SharedPreferences
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            return sharedPreferences.getString("currentUserId", null);
        }
        return null;
    }
}
