package com.example.ocrugbyapp.teams;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.example.ocrugbyapp.profile.Login;
import com.example.ocrugbyapp.results.Results;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Teams extends AppCompatActivity {

    private String[] titles = new String[]{"1st XV", "2nd XV", "B XV", "Women's XV"};

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(Teams.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        ImageView profileBtn = findViewById(R.id.btnProfile);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            profileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                }
            });

            //allowing items in nav bar to be clicked and change activity
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {

                        case R.id.fixtures:
                            Intent intent1 = new Intent(Teams.this, Fixtures.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent1);
                            break;

                        case R.id.teams:
                            break;

                        case R.id.results:
                            Intent intent3 = new Intent(Teams.this, Results.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent3);
                            break;

                        case R.id.members:
                            Intent intent4 = new Intent(Teams.this, Members.class);
                            intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent4);
                            break;

                    }

                    return false;
                }
            });

            //fragments and tabLayout
            TeamsPagerAdapter teamsPagerAdapter = new TeamsPagerAdapter(this);

            ViewPager2 mViewPager = (ViewPager2) findViewById(R.id.container);
            mViewPager.setAdapter(teamsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            new TabLayoutMediator(tabLayout, mViewPager,(tab, position) -> tab.setText(titles[position])).attach();
        }else {
            startActivity(new Intent(Teams.this, Login.class));
            finish();
        }
    }
}