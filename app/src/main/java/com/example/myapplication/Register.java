package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button btnContinue;
    private EditText nama, email, password, alamat, phone, age;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    String[] list;
    ArrayAdapter<String> majorList;
    private String majorItems;
    AutoCompleteTextView major;
    TextInputLayout majorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnContinue = findViewById(R.id.continueSignup);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        password = findViewById(R.id.password);
        majorLayout = (TextInputLayout)findViewById(R.id.majorLayout);
        major = (AutoCompleteTextView) findViewById(R.id.major);

        list = getResources().getStringArray(R.array.fakultasUPNVJ);
        majorList = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_layout, list);
        major.setAdapter(majorList);
        major.setThreshold(1);

        major.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                majorItems = adapterView.getItemAtPosition(i).toString();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strEmail = email.getText().toString();
                String strAlamat = alamat.getText().toString();
                String strPhone = phone.getText().toString();
                String strAge = age.getText().toString();
                String strPassword = password.getText().toString();

                if(strEmail.isEmpty()){
                    email.setError("PLEASE INPUT YOUR EMAIL!");
                }else if(strPassword.isEmpty()){
                    password.setError("PLEASE INPUT YOUR PASSWORD!");
                }else if(strPhone.isEmpty()){
                    phone.setError("PLEASE INPUT YOUR PHONE!");
                }else if(strAge.isEmpty()){
                    age.setError("PLEASE INPUT YOUR AGE!");
                }else if(strAlamat.isEmpty()) {
                    alamat.setError("PLEASE INPUT YOUR ADDRESS!");
                }else if(strNama.isEmpty()){
                    nama.setError("PLEASE INPUT YOUR NAME");
                }else{
                    register(strNama, strEmail,strAlamat, strPhone, strAge, strPassword);
                }
            }
        });
    }

    private void register(String nama, String email, String alamat, String phone, String age,  String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Register Succesfully!", Toast.LENGTH_LONG).show();

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    String profImg = "https://firebasestorage.googleapis.com/v0/b/beasiswaku.appspot.com/o/default-person.png?alt=media&token=4e4316a2-827d-4e92-a623-48c57607dd06";

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("nama", nama);
                    hashMap.put("major", majorItems);
                    hashMap.put("email", email);
                    hashMap.put("alamat", alamat);
                    hashMap.put("phone", phone);
                    hashMap.put("age", age);
                    hashMap.put("profImage", profImg);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(Register.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK) );
                        }
                    });
                }else{
                    Toast.makeText(Register.this, "Register Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}