package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class detail_scholar extends AppCompatActivity {
    private ImageButton saved;
    String scholarshipId, scholarshipKey;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scholar);

        saved = findViewById(R.id.saveButton);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("detail", Context.MODE_PRIVATE);
        scholarshipId = prefs.getString("id", "none");
        scholarshipKey = prefs.getString("key", "none");

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

        isSaveScholarship(scholarshipKey, saved);

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saved.getTag().equals("unsaved")){
                    FirebaseDatabase.getInstance().getReference().child("Saved_Scholarship")
                            .child(scholarshipKey)
                            .child(GlobalVariable.user.getId()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Saved_Scholarship")
                            .child(scholarshipKey)
                            .child(GlobalVariable.user.getId()).removeValue();
                }

            }
        });

    }
    public void isSaveScholarship(String scholarshipKey, ImageView imageView){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Saved_Scholarship").child(scholarshipKey);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(GlobalVariable.user.getId()).exists()){
                    imageView.setImageResource(R.drawable.ic_saved_ribbon_fill);
                    imageView.setTag("saved");
                }else{
                    imageView.setImageResource(R.drawable.ic_saved_ribbon);
                    imageView.setTag("unsaved");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}