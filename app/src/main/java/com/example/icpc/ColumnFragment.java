package com.example.icpc;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icpc.ColumnAdapter;
import com.example.icpc.Column;

import java.util.ArrayList;
import java.util.List;

public class ColumnFragment extends Fragment {

    private RecyclerView recyclerView;
    private ColumnAdapter adapter;
    private List<Column> columnList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_column, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        columnList = new ArrayList<>();
        columnList.add(new Column(1, "Column 1", R.drawable.ic_logo_red, "Source 1", "2022-05-20"));
        columnList.add(new Column(2, "Column 2", R.drawable.ic_logo_red, "Source 2", "2022-05-21"));
        columnList.add(new Column(3, "Column 3", R.drawable.ic_logo_red, "Source 3", "2022-05-22"));
        columnList.add(new Column(4, "Column 1", R.drawable.ic_logo_red, "Source 1", "2022-05-20"));
        columnList.add(new Column(5, "Column 2", R.drawable.ic_logo_red, "Source 2", "2022-05-21"));
        columnList.add(new Column(6, "Column 3", R.drawable.ic_logo_red, "Source 3", "2022-05-22"));
        adapter = new ColumnAdapter(getContext(), columnList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
