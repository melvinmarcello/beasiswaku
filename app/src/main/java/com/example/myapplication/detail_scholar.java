package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class detail_scholar extends AppCompatActivity {
    private ImageButton saved;
    String scholarshipId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scholar);


        SharedPreferences prefs = getApplicationContext().getSharedPreferences("detail", Context.MODE_PRIVATE);
        scholarshipId = prefs.getString("id", "none");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("scholarship");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        saved = findViewById(R.id.saveButton);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saved.setImageResource(R.drawable.ic_saved_ribbon_fill);
            }
        });

    }
}