package com.example.ocrugbyapp.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.MensFixtureCard;
import com.example.ocrugbyapp.fixtures.MensFixturesListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FirstsResults extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_firsts_results, container, false);

        recyclerView = view.findViewById(R.id.resultsList);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        mStore.collection("ocrfcFixtures").orderBy("fixtureNum").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    List<ResultsCard> fixture = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {


                            final int fixtureNum;
                            final String date;
                            final String homeTeam, awayTeam, homeScore, awayScore;

                            fixtureNum = (int) document.get("fixtureNum");
                            date = document.get("date").toString();


                            if (!document.get("firstsHA").toString().equals("")) {

                                if (document.get("firstsHA").equals("H")) {

                                    homeTeam = "Old Cranleighans";
                                    awayTeam = document.get("firstsOpponent").toString();

                                    if (document.get("firstsScore").toString().isEmpty()) {
                                        homeScore = "no score yet";
                                    }else {
                                        homeScore = document.get("firstsScore").toString();
                                    }

                                    if (document.get("firstsOppScore").toString().isEmpty()) {
                                        awayScore = "-";
                                    }else {
                                        awayScore = document.get("firstsOppScore").toString();
                                    }

                                    fixture.add(new ResultsCard(fixtureNum, date, homeTeam, awayTeam, homeScore, awayScore));

                                }else if (document.get("firstsHA").equals("A")) {

                                    awayTeam = "Old Cranleighans";
                                    homeTeam = document.get("firstsOpponent").toString();

                                    if (document.get("firstsScore").toString().isEmpty()) {
                                        awayScore = "-";
                                    } else {
                                        awayScore = document.get("firstsScore").toString();
                                    }

                                    if (document.get("firstsOppScore").toString().isEmpty()) {
                                        homeScore = "no score yet";
                                    } else {
                                        homeScore = document.get("firstsOppScore").toString();
                                    }

                                    fixture.add(new ResultsCard(fixtureNum, date, homeTeam, awayTeam, homeScore, awayScore));
                                }
                            }
                        }
                    }
                }
            }
        });

        return view;
    }
}