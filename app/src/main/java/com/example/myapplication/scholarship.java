package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Adapter.ScholarshipAdapter;
import com.example.myapplication.Model.Scholarship;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class scholarship extends Fragment {
    private TextView labelFindScholar;
    private RecyclerView recyclerView;
    private List<Scholarship> scholarshipList;
    private ScholarshipAdapter scholarshipAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scholarship, container, false);

        labelFindScholar = view.findViewById(R.id.labelFindScholar);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        scholarshipList = new ArrayList<>();
        scholarshipAdapter = new ScholarshipAdapter(getContext(), scholarshipList);
        recyclerView.setAdapter(scholarshipAdapter);

        showScholarship();

        return view;
    }
    private void showScholarship(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scholarship");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scholarshipList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Scholarship scholarship = dataSnapshot.getValue(Scholarship.class);
                    scholarshipList.add(scholarship);
                    scholarship.setKey(dataSnapshot.getKey());
                }
                scholarshipAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}