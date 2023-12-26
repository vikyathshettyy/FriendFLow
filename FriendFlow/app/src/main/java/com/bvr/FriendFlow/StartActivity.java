package com.bvr.FriendFlow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        register = findViewById(R.id.button);
        login = findViewById(R.id.button2);
        register.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this,RegisterActivity.class));

        });
        login.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this,LoginActivity.class));

        });
    }
}