package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private TextView login;
    private EditText email;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        register = findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(v->{
            String email_1 = email.getText().toString();
            String password_1 = password.getText().toString();
            if(TextUtils.isEmpty(email_1) || TextUtils.isEmpty(password_1)){
                Toast.makeText(this,"Please Enter User Credentials",Toast.LENGTH_LONG).show();
            }
            else if(password_1.length()<8)
            {
                Toast.makeText(this,"Password Must have atleast 8 characters",Toast.LENGTH_LONG).show();
            }
            else {
                auth.createUserWithEmailAndPassword(email_1,password_1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this,"Account Created Successfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"There was some error, Please try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        login = findViewById(R.id.log);
        login.setOnClickListener(v->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });


    }
}