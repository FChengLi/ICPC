package com.example.icpc.myinfor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.R;
import com.example.icpc.database.DatabaseHelper;
import com.example.icpc.discover.discover_article;

import java.util.List;

public class my_collection extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        List<discover_article> collectionList = dbHelper.getFavoriteArticles();

        my_CollectionAdapter adapter = new my_CollectionAdapter(getContext(), collectionList);
        recyclerView.setAdapter(adapter);
    }
}
