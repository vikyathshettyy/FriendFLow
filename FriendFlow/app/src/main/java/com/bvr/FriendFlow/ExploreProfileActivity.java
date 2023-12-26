package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ExploreProfileActivity extends AppCompatActivity {
    private BottomNavigationView nav;
    private FirebaseAuth auth;
    FirebaseFirestore db;
    private TextView fn,bio,age,gender,address,email,ph;
    FirebaseUser usr;
    private ImageView like;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_profile);
        nav = findViewById(R.id.nav);
        auth = FirebaseAuth.getInstance();
        usr = auth.getCurrentUser();
        fn = findViewById(R.id.fname);
        bio = findViewById(R.id.bio);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.adrs);
        email = findViewById(R.id.email);
        ph = findViewById(R.id.phone);
        auth = FirebaseAuth.getInstance();
        usr = auth.getCurrentUser();
        like = findViewById(R.id.like);
        btn = findViewById(R.id.button4);
        db = FirebaseFirestore.getInstance();
        for (int i = 0; i <nav.getMenu().size()-1; i++) { nav.getMenu().getItem(i).setChecked(false); }
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.create) {

                    startActivity(new Intent(ExploreProfileActivity.this, CreateActivity.class));

                } else if (itemId == R.id.explore) {

                } else if (itemId == R.id.home) {
                    startActivity(new Intent(ExploreProfileActivity.this, MainActivity.class));
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(ExploreProfileActivity.this, ProfileActivity.class));
                } else if (itemId == R.id.logout) {
                    auth.signOut();
                    Toast.makeText(ExploreProfileActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ExploreProfileActivity.this, StartActivity.class));
                    finish();
                }

                return true;
            }
        });
        Intent intent = getIntent();
        String email1 = intent.getStringExtra("Email");
        String fName1 = intent.getStringExtra("FName");
        String lName1 = intent.getStringExtra("LName");
        String bio1 = intent.getStringExtra("Bio");
        String phone1 = intent.getStringExtra("Phone");
        String gender1 = intent.getStringExtra("Gender");
        String age1 = intent.getStringExtra("Age");
        String address1 = intent.getStringExtra("Address");
        fn.setText(fName1+" "+lName1);
        bio.setText(bio1);
        age.setText(age1);
        gender.setText(gender1);
        address.setText(address1);
        email.setText(email1);
        ph.setText(phone1);
        like.setOnClickListener(v->{
            like.setBackgroundResource(R.drawable.heart);
            Map<String, Object> like1 = new HashMap<>();
            like1.put("liked",email1);
            like1.put("user",usr.getEmail());
            db.collection("likes").whereEqualTo("liked",email1).whereEqualTo("user",usr.getEmail().toString()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().isEmpty())
                                {
                                    db.collection("likes").add(like1)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(ExploreProfileActivity.this, "Liked", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(ExploreProfileActivity.this, "Could Not Like", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else
                                {
                                    Toast.makeText(ExploreProfileActivity.this, "Already Liked", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Log.d("one", "Error getting documents: ");
                            }

                        }
                    });


        });

        btn.setOnClickListener(v->{
            Intent phone_intent = new Intent(Intent.ACTION_CALL);
            phone_intent.setData(Uri.parse("tel:" + phone1));
            startActivity(phone_intent);
        });



    }
}