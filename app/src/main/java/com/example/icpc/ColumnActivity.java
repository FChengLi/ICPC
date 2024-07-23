package com.example.icpc;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.icpc.ColumnAdapter;
import com.example.icpc.Column;
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

        // 获取传递的数据
        String iconName = getIntent().getStringExtra("iconName");

        // 根据 iconName 或其他标识来加载相应的数据
        loadColumnData(iconName);

        adapter = new ColumnAdapter(this, columnList);
        recyclerView.setAdapter(adapter);
    }

    private void loadColumnData(String iconName) {
        // 根据 iconName 从数据库或其他来源加载数据
        // 示例数据加载代码：
        columnList.add(new Column(1, iconName + " Column 1", "image_url_1", "Source 1", "2022-05-20"));
        columnList.add(new Column(2, iconName + " Column 2", "image_url_2", "Source 2", "2022-05-21"));
        // 添加更多的数据
    }
}
