package com.example.icpc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class z_first extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_first);

        Button addButton = findViewById(R.id.a);
        Button changeButton = findViewById(R.id.b);
        Button deleteButton = findViewById(R.id.c);
        Button addForumButton = findViewById(R.id.e);
        Button changeForumButton = findViewById(R.id.f);

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
    }
}
