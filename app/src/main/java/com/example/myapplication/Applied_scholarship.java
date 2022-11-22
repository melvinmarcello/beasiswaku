package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Adapter.AppliedScholarshipAdapter;
import com.example.myapplication.Adapter.SavedScholarshipAdapter;
import com.example.myapplication.Model.Scholarship;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Applied_scholarship extends AppCompatActivity {
    private RecyclerView appliedRecyclerView;
    private List<Scholarship> scholarshipAppliedList;
    private List<String> myAppliedList;
    AppliedScholarshipAdapter appliedScholarshipAdapter;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_scholarship);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Applied Scholarship");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        scholarshipAppliedList = new ArrayList<>();
        appliedScholarshipAdapter = new AppliedScholarshipAdapter(getApplicationContext(), scholarshipAppliedList);

        //deklarasi
        appliedRecyclerView = findViewById(R.id.appliedRecycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        appliedRecyclerView.setLayoutManager(linearLayoutManager);
        appliedRecyclerView.setAdapter(appliedScholarshipAdapter);


        //fetch data
        fetchAppliedScholarship();
    }

    public void fetchAppliedScholarship(){
        myAppliedList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Applied_Scholarship").child(GlobalVariable.user.getId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myAppliedList.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    myAppliedList.add(data.getKey());
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
                scholarshipAppliedList.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    Scholarship scholarship = data.getValue(Scholarship.class);
                    scholarship.setKey(data.getKey());
                    for (String scholarshipKey: myAppliedList){
                        if (scholarship.getKey().equals(scholarshipKey)){
                            scholarshipAppliedList.add(scholarship);
                        }
                    }
                }
                appliedScholarshipAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}