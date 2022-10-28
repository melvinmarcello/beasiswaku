package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.usage.StorageStats;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Model.Scholarship;
import com.example.myapplication.Model.Users;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageTask;

import java.net.URI;

public class EditProfile extends AppCompatActivity {
    private Button btnSaveChange;
    private EditText nama, email,  alamat, phone;
    private ImageView imgProfile;
    private Uri uri;
    public StorageTask storageTask;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnSaveChange = findViewById(R.id.btnSaveChange);
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        imgProfile = findViewById(R.id.imgProfile);


        nama.setText(GlobalVariable.user.getNama());
        alamat.setText(GlobalVariable.user.getAlamat());
        email.setText(GlobalVariable.user.getEmail());
        phone.setText(GlobalVariable.user.getPhone());


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }
    private void uploadImage(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image...");
        progressDialog.show();


    }

    private void  selectImage(){

    }

}