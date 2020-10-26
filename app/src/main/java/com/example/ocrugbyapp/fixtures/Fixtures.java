package com.example.ocrugbyapp.fixtures;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.profile.Login;
import com.example.ocrugbyapp.results.Results;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.profile.Profile;
import com.example.ocrugbyapp.teams.Teams;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fixtures extends AppCompatActivity {

    private static final String TAG = "Fixtures";

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private RecyclerView mRecyclerView;
    FirebaseFirestore mStore;
    MensFixturesListAdapter mensFixturesListAdapter;
    Date fixtureDate;
    ProgressBar progressBar;

    ImageView profileBtn;

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(Fixtures.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        profileBtn = findViewById(R.id.btnProfile);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //allowing items in nav bar to be clicked and change activity
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {

                        case R.id.fixtures:
                            break;

                        case R.id.teams:
                            Intent intent2 = new Intent(Fixtures.this, Teams.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent2);
                            break;

                        case R.id.results:
                            Intent intent3 = new Intent(Fixtures.this, Results.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent3);
                            break;

                        case R.id.members:
                            Intent intent4 = new Intent(Fixtures.this, Members.class);
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

            mRecyclerView = findViewById(R.id.recyclerViewMensFixtures);

            mStore = FirebaseFirestore.getInstance();

            mStore.collection("ocrfcFixtures").orderBy("fixtureNum").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        List<MensFixtureCard> fixture = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document != null) {

                                final String fixtureNum, date, firstsOpponent, firstsHA, firstsLC, firstsKO, firstsMeet, firstsAddress, firstsPostcode;
                                final String secondsOpponent, secondsHA, secondsLC, secondsKO, secondsMeet, secondsAddress, secondsPostcode;
                                final String bsOpponent, bsHA, bsLC, bsKO, bsMeet, bsAddress, bsPostcode;

                                fixtureNum = "Fixture " + document.getId();
                                date = document.get("date").toString();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                                try {
                                    fixtureDate = sdf.parse(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (new Date().before(fixtureDate)) {

                                    if (!document.get("firstsOpponent").toString().equals("")) {
                                        firstsOpponent = "1st XV vs " + document.get("firstsOpponent").toString();
                                    } else {
                                        firstsOpponent = "No Fixture";
                                    }

                                    if (document.get("firstsHA").toString().equals("H")) {
                                        firstsHA = "Home";
                                    } else if (document.get("firstsHA").toString().equals("A")) {
                                        firstsHA = "Away";
                                    } else {
                                        firstsHA = document.get("firstsHA").toString();
                                    }

                                    if (document.get("firstsLC").toString().equals("L")) {
                                        firstsLC = "League Match";
                                    } else if (document.get("firstsLC").toString().equals("C")) {
                                        firstsLC = "Cup Match";
                                    } else {
                                        firstsLC = document.get("firstsLC").toString();
                                    }

                                    if (document.get("firstsKO").toString().equals("")) {
                                        firstsKO = document.get("firstsKO").toString();
                                    } else {
                                        firstsKO = "Kick Off: " + document.get("firstsKO").toString();
                                    }

                                    if (document.get("firstsMeet").toString().equals("")) {
                                        firstsMeet = document.get("firstsMeet").toString();
                                    } else {
                                        firstsMeet = "Meet Time: " + document.get("firstsMeet").toString();
                                    }

                                    firstsAddress = document.get("firstsAddress").toString();
                                    firstsPostcode = document.get("firstsPostcode").toString();


                                    if (!document.get("secondsOpponent").toString().equals("")) {
                                        secondsOpponent = "2nd XV vs " + document.get("secondsOpponent").toString();
                                    } else {
                                        secondsOpponent = "No Fixture";
                                    }

                                    if (document.get("secondsHA").toString().equals("H")) {
                                        secondsHA = "Home";
                                    } else if (document.get("secondsHA").toString().equals("A")) {
                                        secondsHA = "Away";
                                    } else {
                                        secondsHA = document.get("secondsHA").toString();
                                    }

                                    if (document.get("secondsLC").toString().equals("L")) {
                                        secondsLC = "League Match";
                                    } else if (document.get("secondsLC").toString().equals("C")) {
                                        secondsLC = "Cup Match";
                                    } else {
                                        secondsLC = document.get("secondsLC").toString();
                                    }

                                    if (document.get("secondsKO").toString().equals("")) {
                                        secondsKO = document.get("secondsKO").toString();
                                    } else {
                                        secondsKO = "Kick Off: " + document.get("secondsKO").toString();
                                    }

                                    if (document.get("secondsMeet").toString().equals("")) {
                                        secondsMeet = document.get("secondsMeet").toString();
                                    } else {
                                        secondsMeet = "Meet Time: " + document.get("secondsMeet").toString();
                                    }

                                    secondsAddress = document.get("secondsAddress").toString();
                                    secondsPostcode = document.get("secondsPostcode").toString();


                                    if (!document.get("bsOpponent").toString().equals("")) {
                                        bsOpponent = "B XV vs " + document.get("bsOpponent").toString();
                                    } else {
                                        bsOpponent = "No Fixture";
                                    }

                                    if (document.get("bsHA").toString().equals("H")) {
                                        bsHA = "Home";
                                    } else if (document.get("bsHA").toString().equals("A")) {
                                        bsHA = "Away";
                                    } else {
                                        bsHA = document.get("bsHA").toString();
                                    }

                                    if (document.get("bsLC").toString().equals("L")) {
                                        bsLC = "League Match";
                                    } else if (document.get("bsLC").toString().equals("C")) {
                                        bsLC = "Cup Match";
                                    } else {
                                        bsLC = document.get("bsLC").toString();
                                    }

                                    if (document.get("bsKO").toString().equals("")) {
                                        bsKO = document.get("bsKO").toString();
                                    } else {
                                        bsKO = "Kick Off: " + document.get("bsKO").toString();
                                    }

                                    if (document.get("bsMeet").toString().equals("")) {
                                        bsMeet = document.get("bsMeet").toString();
                                    } else {
                                        bsMeet = "Meet Time: " + document.get("bsMeet").toString();
                                    }

                                    bsAddress = document.get("bsAddress").toString();
                                    bsPostcode = document.get("bsPostcode").toString();

                                    fixture.add(new MensFixtureCard(fixtureNum, date, firstsOpponent, firstsHA, firstsLC, firstsKO, firstsMeet, firstsAddress, firstsPostcode,
                                            secondsOpponent, secondsHA, secondsLC, secondsKO, secondsMeet, secondsAddress, secondsPostcode,
                                            bsOpponent, bsHA, bsLC, bsKO, bsMeet, bsAddress, bsPostcode));

                                }
                            }
                        }
                        mensFixturesListAdapter = new MensFixturesListAdapter(Fixtures.this, fixture);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(Fixtures.this));
                        mRecyclerView.setAdapter(mensFixturesListAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }else {
            Intent intent = new Intent(Fixtures.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}