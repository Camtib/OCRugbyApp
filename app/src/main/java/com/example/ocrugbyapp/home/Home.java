package com.example.ocrugbyapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.example.ocrugbyapp.profile.Login;
import com.example.ocrugbyapp.results.Results;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.profile.Profile;
import com.example.ocrugbyapp.teams.Teams;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser currentUser;


    private static final String TAG = "Home";

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(Home.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        ImageView profileBtn = (ImageView) findViewById(R.id.btnProfile);



        //allowing items in nav bar to be clicked and change activity
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.fixtures:
                        Intent intent1 = new Intent(Home.this, Fixtures.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;

                    case R.id.home:
                        break;

                    case R.id.teams:
                        Intent intent2 = new Intent(Home.this, Teams.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        break;

                    case R.id.results:
                        Intent intent3 = new Intent(Home.this, Results.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        break;

                    case R.id.members:
                        Intent intent4 = new Intent(Home.this, Members.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent4);
                        break;

                }

                return false;
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
    }
}