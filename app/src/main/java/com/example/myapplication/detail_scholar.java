package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button btnApply;
    String scholarshipId, scholarshipKey;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scholar);

        saved = findViewById(R.id.saveButton);
        btnApply = findViewById(R.id.btnApply);


        SharedPreferences prefs = getApplicationContext().getSharedPreferences("detail", Context.MODE_PRIVATE);
        scholarshipId = prefs.getString("id", "none");
        scholarshipKey = prefs.getString("key", "none");

        reference = FirebaseDatabase.getInstance().getReference().child("Scholarship");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("scholarship");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("applyform", Context.MODE_PRIVATE).edit();
                editor.putString("id", scholarshipId);
                editor.apply();
                startActivity(new Intent(detail_scholar.this, Apply_form.class));
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        isSaveScholarship(GlobalVariable.user.getId(), saved);

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saved.getTag().equals("unsaved")){
                    FirebaseDatabase.getInstance().getReference().child("Saved_Scholarship")
                            .child(GlobalVariable.user.getId())
                            .child(scholarshipKey).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Saved_Scholarship")
                            .child(GlobalVariable.user.getId())
                            .child(scholarshipKey).removeValue();
                }

            }
        });

    }
    public void isSaveScholarship(String UserId, ImageView imageView){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Saved_Scholarship").child(UserId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(scholarshipKey).exists()){
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