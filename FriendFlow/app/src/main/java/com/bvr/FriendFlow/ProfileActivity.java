package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView nav;
    private TextView fn,bio,age,gender,address,email1,ph;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser usr;
    private ImageView dp;
    private ConstraintLayout cl;
    private ProgressBar pb;
    private Map<String, Object> user1 = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nav = findViewById(R.id.nav);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usr = auth.getCurrentUser();
        String email = usr.getEmail();
        fn = findViewById(R.id.fname);
        bio = findViewById(R.id.bio);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.adrs);
        email1 = findViewById(R.id.email);
        ph = findViewById(R.id.phone);
        dp = findViewById(R.id.imageButton);
        pb = findViewById(R.id.pb);
        db.collection("users");
        cl = findViewById(R.id.one);
        DocumentReference ref = db.collection("users").document(email);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                if(doc.exists())
                {

                    user1=doc.getData();
                    dp.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    fn.setText(user1.get("First Name").toString()+" "+user1.get("Last Name").toString());
                    fn.setVisibility(View.VISIBLE);
                    bio.setText(user1.get("Bio").toString());
                    bio.setVisibility(View.VISIBLE);
                    age.setText(user1.get("Age").toString());
                    age.setVisibility(View.VISIBLE);
                    gender.setText(user1.get("Gender").toString());
                    gender.setVisibility(View.VISIBLE);
                    address.setText(user1.get("Address").toString());
                    address.setVisibility(View.VISIBLE);
                    email1.setText(user1.get("Email").toString());
                    email1.setVisibility(View.VISIBLE);
                    ph.setText(user1.get("Phone Number").toString());
                    ph.setVisibility(View.VISIBLE);
                    String g = user1.get("Gender").toString().charAt(0)+"he/she";
                   
                    if(g.charAt(0)=='f' || g.charAt(0)=='F')
                    {
                        dp.setImageResource(R.drawable.female_dp);
                        cl.setBackgroundResource(R.color.back1);
                    }
                    else if((g.charAt(0)=='m' || g.charAt(0)=='M'))
                    {
                        dp.setImageResource(R.drawable.male_dp);
                        cl.setBackgroundResource(R.color.back1);
                    }


                }
                else
                {
                    startActivity(new Intent(ProfileActivity.this, CreateActivity.class));
                }
            }
        });
        for (int i = 0; i <nav.getMenu().size()-3; i++) { nav.getMenu().getItem(i).setChecked(false); }
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.create) {

                    startActivity(new Intent(ProfileActivity.this, CreateActivity.class));

                } else if (itemId == R.id.explore) {
                    startActivity(new Intent(ProfileActivity.this, ExploreActivity.class));
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                } else if (itemId == R.id.profile) {

                } else if (itemId == R.id.logout) {
                    auth.signOut();
                    Toast.makeText(ProfileActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProfileActivity.this, StartActivity.class));
                    finish();
                }

                return true;
            }
        });
    }
}