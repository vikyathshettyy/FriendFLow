package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CreateActivity extends AppCompatActivity {
    private Button btn;
    public FirebaseFirestore db;
    public FirebaseAuth auth;
    public FirebaseUser usr;
    private BottomNavigationView nav;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = FirebaseFirestore.getInstance();
        dbHandler = new DBHandler(CreateActivity.this);
        btn = findViewById(R.id.button5);
        auth = FirebaseAuth.getInstance();
        usr = auth.getCurrentUser();
        String email = usr.getEmail();
        nav = findViewById(R.id.nav);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.create) {



                } else if (itemId == R.id.explore) {
                    startActivity(new Intent(CreateActivity.this, ExploreActivity.class));
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(CreateActivity.this, MainActivity.class));
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(CreateActivity.this, ProfileActivity.class));
                } else if (itemId == R.id.logout) {
                    auth.signOut();
                    Toast.makeText(CreateActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateActivity.this, StartActivity.class));
                    finish();
                }

                return true;
            }
        });

        btn.setOnClickListener(v->{
           EditText et1 = findViewById(R.id.fname);
            String fn = et1.getText().toString();

            EditText et2 = findViewById(R.id.lname);
            String ln = et2.getText().toString();

            EditText et3 = findViewById(R.id.bio);
            String bio = et3.getText().toString();

            EditText et4 = findViewById(R.id.age);
            String age = et4.getText().toString();

            EditText et5 = findViewById(R.id.gender);
            String gender = et5.getText().toString();

            EditText et6 = findViewById(R.id.adrs);
            String adrs = et6.getText().toString();

            EditText et7 = findViewById(R.id.phone);
            String ph = et7.getText().toString();
            // Create a new user with a first and last name
            Map<String, Object> user1 = new HashMap<>();
            user1.put("Email",email);
            user1.put("First Name", fn);
            user1.put("Last Name", ln);
            user1.put("Bio", bio);
            user1.put("Age",age);
            user1.put("Gender",gender);
            user1.put("Address",adrs);
            user1.put("Phone Number",ph);
            dbHandler.addNewUser(fn, ln, bio, age, email, ph, gender, adrs);
            db.collection("users").document(email)
                    .set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateActivity.this, "User Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateActivity.this, "Could Not Updata User Profile", Toast.LENGTH_SHORT).show();
                        }
                    });

            startActivity(new Intent(CreateActivity.this,MainActivity.class));

        });

    }
}