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
        setContentView(R.layout.z_first); // 确保 activity_z_first.xml 是您的布局文件名

        Button addButton = findViewById(R.id.a);
        Button changeButton = findViewById(R.id.b);
        Button deleteButton = findViewById(R.id.c);

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
    }
}
