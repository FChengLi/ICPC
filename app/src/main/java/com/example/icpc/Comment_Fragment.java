package com.example.icpc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Comment_Fragment extends Fragment {
    private List<CommentItem> commentList;
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(commentAdapter);

        return view;
    }

    public void updateComments(List<CommentItem> comments) {
        commentList.clear();
        commentList.addAll(comments);
        commentAdapter.notifyDataSetChanged();
    }
}
