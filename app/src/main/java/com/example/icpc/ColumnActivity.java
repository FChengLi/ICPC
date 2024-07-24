package com.example.icpc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ColumnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ColumnAdapter adapter;
    private List<Column> columnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        columnList = new ArrayList<>();
        columnList.add(new Column(1, "Column 1", R.drawable.ic_logo_red, "Source 1", "2022-05-20"));
        columnList.add(new Column(2, "Column 2", R.drawable.ic_logo_red, "Source 2", "2022-05-21"));
        columnList.add(new Column(3, "Column 3", R.drawable.ic_logo_red, "Source 3", "2022-05-22"));
        columnList.add(new Column(4, "Column 1", R.drawable.ic_logo_red, "Source 1", "2022-05-20"));
        columnList.add(new Column(5, "Column 2", R.drawable.ic_logo_red, "Source 2", "2022-05-21"));
        columnList.add(new Column(6, "Column 3", R.drawable.ic_logo_red, "Source 3", "2022-05-22"));
        adapter = new ColumnAdapter(this, columnList);
        recyclerView.setAdapter(adapter);
    }
}
