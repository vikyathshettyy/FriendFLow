package com.bvr.FriendFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView nav;
    private FirebaseAuth auth;
    private BroadcastReceiver br;
    private FirebaseFirestore db;
    FirebaseUser usr;
    private Button likes;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(MainActivity.this,StartActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDailyNotification();
        likes = findViewById(R.id.likes);
        br = new InternetReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }


        auth = FirebaseAuth.getInstance();
        nav = findViewById(R.id.nav);
        for (int i = 0; i <nav.getMenu().size()-2; i++) { nav.getMenu().getItem(i).setChecked(false); }
        db = FirebaseFirestore.getInstance();
        usr = auth.getCurrentUser();
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.create) {

                    startActivity(new Intent(MainActivity.this, CreateActivity.class));

                } else if (itemId == R.id.explore) {
                    startActivity(new Intent(MainActivity.this, ExploreActivity.class));
                } else if (itemId == R.id.home) {

                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else if (itemId == R.id.logout) {
                    auth.signOut();
                    Toast.makeText(MainActivity.this,"Logout Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,StartActivity.class));
                    finish();
                }

                return true;
            }
        });
        String email;
        if(usr.getEmail()!=null)
        {
            email = usr.getEmail().toString();
        }
        else
        {
            email = "test123@gmail.com";
        }

        db.collection("likes").whereEqualTo("liked",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Integer i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        i++;
                    }
                    String res = " " + i;
                    if(i!=0)
                    {
                        likes.setText("LIKES : "+i);
                        likes.setVisibility(View.VISIBLE);
                    }


                } else {
                    Log.d("one", "Error getting documents: ");
                }
            }

        });

    }


    private void setDailyNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE  // Add this line
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(br);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}