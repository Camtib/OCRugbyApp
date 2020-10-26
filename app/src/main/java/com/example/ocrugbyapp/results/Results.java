package com.example.ocrugbyapp.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.profile.Login;
import com.example.ocrugbyapp.profile.Profile;
import com.example.ocrugbyapp.teams.Teams;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Results extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String[] titles = new String[]{"1st XV", "2nd XV", "B XV"};

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(Results.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            ImageView profileBtn = findViewById(R.id.btnProfile);

            //allowing items in nav bar to be clicked and change activity
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {

                    case R.id.fixtures:
                        Intent intent1 = new Intent(Results.this, Fixtures.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;

                    case R.id.teams:
                        Intent intent3 = new Intent(Results.this, Teams.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        break;

                    case R.id.results:
                        break;

                    case R.id.members:
                        Intent intent4 = new Intent(Results.this, Members.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent4);
                        break;

                }

                return false;
            });

            profileBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Profile.class)));

            ResultsPagerAdapter resultsPagerAdapter = new ResultsPagerAdapter(this);

            ViewPager2 mViewPager = findViewById(R.id.container);
            mViewPager.setAdapter(resultsPagerAdapter);

            TabLayout tabLayout = findViewById(R.id.tabs);

            new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(titles[position])).attach();
        }else {
            startActivity(new Intent(Results.this, Login.class));
            finish();
        }
    }
}