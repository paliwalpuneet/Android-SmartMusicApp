package com.example.smartmusicapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmusicapp.MainActivity;
import com.example.smartmusicapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button login;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passLogin);
        login = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textEmail = email.getText().toString();
                String textPass = password.getText().toString();
                loginUser(textEmail,textPass);
            }
        });

    }

    private void loginUser(String textEmail, String textPass) {
        if(textEmail.length()>0 && textPass.length()>0) {

            auth.signInWithEmailAndPassword(textEmail, textPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Not Able to Authenticate-Please Enter valid Details", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Please Enter the Data in the fields", Toast.LENGTH_SHORT).show();
        }
    }
}