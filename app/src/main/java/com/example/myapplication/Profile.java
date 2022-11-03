package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.SavedScholarshipAdapter;
import com.google.firebase.auth.FirebaseAuth;


public class Profile extends Fragment {
    private TextView signOut, editProfile, strNama, strEmail;
    private ImageView profImage, btn_saved_scholarship;
    private Button btnSaveChange;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        editProfile = view.findViewById(R.id.edit_profile);
        btnSaveChange = view.findViewById(R.id.btnSaveChange);
        signOut = view.findViewById(R.id.signOut);
        mAuth = FirebaseAuth.getInstance();
        strNama = view.findViewById(R.id.name);
        strEmail = view.findViewById(R.id.email);
        profImage = view.findViewById(R.id.profileImage);
        btn_saved_scholarship = view.findViewById(R.id.btn_saved_scholarship);


        Glide.with(getContext()).load(GlobalVariable.user.getProfImage()).into(profImage);
        strNama.setText(GlobalVariable.user.getNama());
        strEmail.setText(GlobalVariable.user.getEmail());



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfile.class));
            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        btn_saved_scholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), savedScholarship.class));
            }
        });

        return view;
    }

}