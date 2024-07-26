package com.example.icpc.search;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.R;

import androidx.appcompat.widget.Toolbar;

public class Search_Fragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.top_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
