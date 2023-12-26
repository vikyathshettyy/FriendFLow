package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    private CheckBox cb;
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cb = findViewById(R.id.checkBox);
        register = findViewById(R.id.reg);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        login = findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();
        SharedPreferences sp1 = getSharedPreferences("Login_Details",MODE_PRIVATE);
        email.setText(sp1.getString("email",""));
        password.setText(sp1.getString("password",""));
        login.setOnClickListener(v->{
            SharedPreferences sp2 = getSharedPreferences("Login_Details",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp2.edit();
            String email1 = email.getText().toString();
            String password1 = password.getText().toString();
            if(cb.isChecked())
            {
                editor.putString("email",email1);
                editor.putString("password",password1);
                editor.apply();
            }

            auth.signInWithEmailAndPassword(email1,password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,"Login Failed, Try Again!",Toast.LENGTH_LONG).show();
                }
            });


        });
        register.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

        });
    }
}