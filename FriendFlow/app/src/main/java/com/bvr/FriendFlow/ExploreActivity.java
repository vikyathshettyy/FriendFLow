package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {
    private BottomNavigationView nav;
    private FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser usr;

    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        nav = findViewById(R.id.nav);
        auth = FirebaseAuth.getInstance();
        usr = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        pb = findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        String email = usr.getEmail();
        for (int i = 0; i <nav.getMenu().size()-1; i++) { nav.getMenu().getItem(i).setChecked(false); }
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.create) {

                    startActivity(new Intent(ExploreActivity.this, CreateActivity.class));

                } else if (itemId == R.id.explore) {

                } else if (itemId == R.id.home) {
                    startActivity(new Intent(ExploreActivity.this, MainActivity.class));
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(ExploreActivity.this, ProfileActivity.class));
                } else if (itemId == R.id.logout) {
                    auth.signOut();
                    Toast.makeText(ExploreActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ExploreActivity.this, StartActivity.class));
                    finish();
                }

                return true;
            }
        });
        RecyclerView users1 =findViewById(R.id.users);
        ArrayList <ProfileData> user = new ArrayList<ProfileData>();

            db.collection("users").whereNotEqualTo("Email",email).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                               for (QueryDocumentSnapshot document : task.getResult()) {

                                    user.add(new ProfileData(document.get("First Name").toString(),document.get("Last Name").toString(),document.get("Bio").toString(),document.get("Age").toString(),document.get("Email").toString(),document.get("Phone Number").toString(),document.get("Gender").toString(),document.get("Address").toString()));
                                    UsersAdapter ua = new UsersAdapter(ExploreActivity.this,user);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExploreActivity.this, LinearLayoutManager.VERTICAL, false);
                                    users1.setLayoutManager(linearLayoutManager);
                                    users1.setAdapter(ua);
                                    pb.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                Log.d("one", "Error getting documents: ");
                            }

                        }
                    });



    }

}