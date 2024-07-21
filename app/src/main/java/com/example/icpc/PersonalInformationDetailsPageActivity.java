package com.example.icpc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class PersonalInformationDetailsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_details);

        int authorId = getIntent().getIntExtra("id", -1);

        TextView textView = findViewById(R.id.tv_author_details);
        if (authorId != -1) {
            textView.setText("Author ID: " + authorId);
        } else {
            textView.setText("No Author ID received");
        }
    }
}
