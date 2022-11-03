package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.ScholarCardAdapter;
import com.example.myapplication.Adapter.ScholarImageAdapter;
import com.example.myapplication.Adapter.ScholarshipAdapter;
import com.example.myapplication.Model.Scholarship;
import com.example.myapplication.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends Fragment {
    private TextView strNama, btnScholarship, strMajor;
    private ImageView profImage;
    private RecyclerView recyclerView, cardRecycleView;
    private List<Scholarship> scholarshipList, cardScholarshipList;
    private ScholarImageAdapter scholarImageAdapter;
    private ScholarCardAdapter scholarCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnScholarship = view.findViewById(R.id.btnScholarship);
        btnScholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("Home", "scholarship");
                startActivity(intent);
            }
        });

        strNama = view.findViewById(R.id.nama);
        strNama.setText("Hi, "+ GlobalVariable.user.getNama());

        strMajor = view.findViewById(R.id.major);
        strMajor.setText(GlobalVariable.user.getMajor());


        profImage = view.findViewById(R.id.profImage);
        Glide.with(getContext()).load(GlobalVariable.user.getProfImage()).into(profImage);

        //Carrousel Section
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        scholarshipList = new ArrayList<>();
        scholarImageAdapter = new ScholarImageAdapter(getContext(), scholarshipList);
        recyclerView.setAdapter(scholarImageAdapter);

        showCarrousel();

        //Card Items Section
        cardRecycleView = view.findViewById(R.id.cardRecycleView);
        cardRecycleView.setHasFixedSize(true);
        LinearLayoutManager cardLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cardLinearLayoutManager.setStackFromEnd(false);
        cardRecycleView.setLayoutManager(cardLinearLayoutManager);
        cardScholarshipList = new ArrayList<>();
        scholarCardAdapter = new ScholarCardAdapter(getContext(), cardScholarshipList);
        cardRecycleView.setAdapter(scholarCardAdapter);

        showCard();

        return view;


    }

    private void showCarrousel(){
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
                scholarImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showCard(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scholarship");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cardScholarshipList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Scholarship scholarship = dataSnapshot.getValue(Scholarship.class);
                    cardScholarshipList.add(scholarship);
                }
                scholarCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}