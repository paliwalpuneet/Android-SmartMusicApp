package com.example.smartmusicapp.Authentication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmusicapp.DbHandler.Details;
import com.example.smartmusicapp.DbHandler.MyDbHandler;
import com.example.smartmusicapp.MainActivity;
import com.example.smartmusicapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private Button register,login;

    FirebaseStorage firebaseStorage;
    StorageReference reference;
    StorageReference pathReference;


    ImageView myImages;
    MediaPlayer player;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDbHandler db =new MyDbHandler(this);
        Details details = db.getUser();
        if(details!=null)
        {
            if(details.getLogin()==1)
            {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }



        setContentView(R.layout.activity_start);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}