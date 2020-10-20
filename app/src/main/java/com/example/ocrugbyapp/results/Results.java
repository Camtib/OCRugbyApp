package com.example.ocrugbyapp.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.SectionsPagerAdapter;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.example.ocrugbyapp.home.Home;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.profile.Profile;
import com.example.ocrugbyapp.teams.Teams;
import com.example.ocrugbyapp.teams.TeamsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Results extends AppCompatActivity {

    private ResultsPagerAdapter resultsPagerAdapter;

    private ViewPager2 mViewPager;

    private String[] titles = new String[]{"1st XV", "2nd XV", "B XV", "Women's XV"};

    private static final String TAG = "Leagues";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        ImageView profileBtn = (ImageView) findViewById(R.id.btnProfile);

        //allowing items in nav bar to be clicked and change activity
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.fixtures:
                        Intent intent1 = new Intent(Results.this, Fixtures.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(Results.this, Home.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
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
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        
        resultsPagerAdapter = new ResultsPagerAdapter(this);

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(resultsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(titles[position])).attach();

    }
}