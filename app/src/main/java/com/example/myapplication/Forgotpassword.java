package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Forgotpassword extends AppCompatActivity {
    private EditText forgotForm;
    private Button btnForgot;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        forgotForm = findViewById(R.id.forgotForm);
        btnForgot = findViewById(R.id.btnForgot);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });



    }
    private void resetPassword(){
        String strEmail = forgotForm.getText().toString();
        if(strEmail.isEmpty()){
            forgotForm.setError("PLEASE INPUT YOUR EMAIL!");
            return ;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
            forgotForm.setError("PLEASE PROVIDE VALID EMAIL!");
            return ;
        }
        mAuth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgotpassword.this, "PLEASE CHECK YOUR EMAIL!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgotpassword.this, Login.class));
                }else{
                    Toast.makeText(Forgotpassword.this, "UNABLE TO SEND EMAIL, PLEASE TRY AGAIN!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}