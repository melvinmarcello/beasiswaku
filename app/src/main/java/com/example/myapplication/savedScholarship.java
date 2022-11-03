package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.SavedScholarshipAdapter;
import com.example.myapplication.Model.Scholarship;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class savedScholarship extends AppCompatActivity {
    private RecyclerView savedRecyclerView;
    private ImageView btnBackSavedScholar;
    private List<Scholarship> scholarshipSavedList;
    private List<String> mySavedList;
    SavedScholarshipAdapter savedScholarshipAdapter;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_scholarship);

        scholarshipSavedList = new ArrayList<>();
        savedScholarshipAdapter = new SavedScholarshipAdapter(getApplicationContext(), scholarshipSavedList);

        //deklarasi
        savedRecyclerView = findViewById(R.id.savedRecyclerView);
        btnBackSavedScholar = findViewById(R.id.btnBackSavedScholar);

        //set kotak kotak
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        savedRecyclerView.setLayoutManager(linearLayoutManager);
        savedRecyclerView.setAdapter(savedScholarshipAdapter);

        //fetch data
        fetchSavedScholarship();
    }

    public void fetchSavedScholarship() {
        mySavedList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saved_Scholarship").child(GlobalVariable.user.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mySavedList.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    mySavedList.add(data.getKey());
                }
                readScholarship();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void readScholarship(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scholarship");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scholarshipSavedList.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    Scholarship scholarship = data.getValue(Scholarship.class);
                    scholarship.setKey(data.getKey());
                    for (String scholarshipKey: mySavedList){
                        if (scholarship.getKey().equals(scholarshipKey)){
                            scholarshipSavedList.add(scholarship);
                        }
                    }
                }
                Log.i("scholarship", scholarshipSavedList.toString());
                savedScholarshipAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}