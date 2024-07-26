package com.example.icpc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icpc.R;
import com.example.icpc.discover.discover_add;
import com.example.icpc.discover.discover_change;
import com.example.icpc.discover.discover_delete;

public class z_first extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_first);

        ImageView backButton = findViewById(R.id.back);
        Button addButton = findViewById(R.id.a);
        Button changeButton = findViewById(R.id.b);
        Button deleteButton = findViewById(R.id.c);
        Button addForumButton = findViewById(R.id.e);
        Button changeForumButton = findViewById(R.id.f);
        Button addArticleButton = findViewById(R.id.g);
        Button changeArticleButton = findViewById(R.id.t);
        Button deleteArticleButton = findViewById(R.id.i);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, z_add.class);
                startActivity(intent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, z_change.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, z_delete.class);
                startActivity(intent);
            }
        });

        addForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, z__add.class);
                startActivity(intent);
            }
        });

        changeForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, z__change.class);
                startActivity(intent);
            }
        });

        addArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, discover_add.class);
                startActivity(intent);
            }
        });

        changeArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, discover_change.class);
                startActivity(intent);
            }
        });

        deleteArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(z_first.this, discover_delete.class);
                startActivity(intent);
            }
        });
    }
}
