package com.example.icpc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Discover_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        Log.d("Discover_Fragment", "onCreateView: RecyclerView initialized");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        articleList = new ArrayList<>();
        articleList.add(new Article("Title 1", "Source 1", "2024-07-19", R.drawable.clear));
        articleList.add(new Article("Title 2", "Source 2", "2024-07-19", R.drawable.clear));
        articleList.add(new Article("Title 3", "Source 3", "2024-07-19", R.drawable.clear));

        Log.d("Discover_Fragment", "onCreateView: Article list size: " + articleList.size());

        articleAdapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(articleAdapter);

        return view;
    }
}
