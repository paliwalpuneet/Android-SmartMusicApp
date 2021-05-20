package com.example.smartmusicapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmusicapp.DbHandler.Details;
import com.example.smartmusicapp.DbHandler.MyDbHandler;
import com.example.smartmusicapp.MainActivity;
import com.example.smartmusicapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,password,firstName,lastName,mobileNumber;
    private Button registerButton;

    private FirebaseAuth auth;
    public MyDbHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         db = new MyDbHandler(this);

        firstName = findViewById(R.id.firrstName);
        lastName  = findViewById(R.id.lastName);
        mobileNumber = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String pass = password.getText().toString();
                String first = firstName.getText().toString();
                String last = lastName.getText().toString();
                String mobile = mobileNumber.getText().toString();


                if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(pass))
                {
                    Toast.makeText(RegisterActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<6)
                {
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(emailText,pass,first,last,mobile);
                }

            }
        });
    }

    private void registerUser(String emailText, String pass,String first,String last,String mobile) {

        auth.createUserWithEmailAndPassword(emailText,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {


                    Details details = new Details(first,last,mobile,1);

                    db.addUser(details);

                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();




                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}