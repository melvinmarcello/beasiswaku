package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Apply_form extends AppCompatActivity {
    private Button btnApplyForm;
    private TextView btnConsult;
    private EditText nama, email, alamat, phone, linkedin, age;
    private String strNama, strEmail, strAlamat, strPhone, strLinkedin, scholarshipId, scholarshipKey;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_form);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        linkedin = findViewById(R.id.linkedin);
        age = findViewById(R.id.age);

        nama.setText(GlobalVariable.user.getNama());
        alamat.setText(GlobalVariable.user.getAlamat());
        email.setText(GlobalVariable.user.getEmail());
        phone.setText(GlobalVariable.user.getPhone());
        age.setText(GlobalVariable.user.getAge());

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("detail", Context.MODE_PRIVATE);
        scholarshipId = prefs.getString("id", "none");
        scholarshipKey = prefs.getString("key", "none");


        btnConsult = findViewById(R.id.consult);
        btnConsult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String number = "6287781166378";
                // Performs action on click
                String url = "https://api.whatsapp.com/send?phone=" + number + "&text=`Hi, I want to apply Scholarship";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnApplyForm = findViewById(R.id.btnApplyForm);
        btnApplyForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strEmail = email.getText().toString();
                String strAlamat = alamat.getText().toString();
                String strPhone = phone.getText().toString();
                String strAge = age.getText().toString();
                String strLinkedin = linkedin.getText().toString();

                if(strEmail.isEmpty()){
                    email.setError("PLEASE INPUT YOUR EMAIL!");
                }else if(strLinkedin.isEmpty()){
                    linkedin.setError("PLEASE INPUT YOUR LINKEDIN!");
                }else if(strPhone.isEmpty()){
                    phone.setError("PLEASE INPUT YOUR PHONE!");
                }else if(strAge.isEmpty()){
                    age.setError("PLEASE INPUT YOUR AGE!");
                }else if(strAlamat.isEmpty()) {
                    alamat.setError("PLEASE INPUT YOUR ADDRESS!");
                }else if(strNama.isEmpty()){
                    nama.setError("PLEASE INPUT YOUR NAME");
                }else{
                    applyForm(strNama, strEmail, strAlamat, strPhone, strAge, strLinkedin, scholarshipId, scholarshipKey, GlobalVariable.user.getId());
                }
            }
        });


    }

    private void applyForm(String nama, String email, String alamat, String phone, String age, String linkedin, String id, String key, String uid){
        reference = FirebaseDatabase.getInstance().getReference().child("Applied_Scholarship").child(uid).child(key);

        Log.e("id", id);
        Log.e("key", key);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nama", nama);
        hashMap.put("email", email);
        hashMap.put("alamat", alamat);
        hashMap.put("phone", phone);
        hashMap.put("age", age);
        hashMap.put("linkedin", linkedin);
        hashMap.put("id", id);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Saving Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Applied_scholarship.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Failed Apply", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}