package com.example.ocrugbyapp.teams;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.SectionsPagerAdapter;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.example.ocrugbyapp.home.Home;
import com.example.ocrugbyapp.leagues.Leagues;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Teams extends AppCompatActivity {

    private static final String TAG = "Teams";
    private SectionsPagerAdapter sectionsPagerAdapter;

    private ViewPager mViewPager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        ImageView profileBtn = (ImageView) findViewById(R.id.btnProfile);

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

                    case R.id.home:
                        Intent intent2 = new Intent(Teams.this, Home.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        break;

                    case R.id.teams:
                        break;

                    case R.id.leagues:
                        Intent intent3 = new Intent(Teams.this, Leagues.class);
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
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("1st XV");
        tabLayout.getTabAt(1).setText("2nd XV");
        tabLayout.getTabAt(2).setText("B XV");
        tabLayout.getTabAt(3).setText("Women's XV");

    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstTeam());
        adapter.addFragment(new SecondTeam());
        adapter.addFragment(new BTeam());
        adapter.addFragment(new WomensTeam());
        viewPager.setAdapter(adapter);

    }
}