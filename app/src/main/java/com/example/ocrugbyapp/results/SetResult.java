package com.example.ocrugbyapp.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ocrugbyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SetResult extends AppCompatActivity {

    TextView date, oCTeam, opposition, confirmBtn, cancelBtn, scoreTV;
    EditText ocScore, oppScore;
    String resultDate, ocScored, oppScored, team;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_result);

        date = findViewById(R.id.dateTV);
        oCTeam = findViewById(R.id.ocTeam);
        opposition = findViewById(R.id.oppositionTeam);
        confirmBtn = findViewById(R.id.confirmBTN);
        cancelBtn = findViewById(R.id.cancelBTN);
        ocScore = findViewById(R.id.ocScore);
        oppScore = findViewById(R.id.oppScore);
        scoreTV = findViewById(R.id.scoreTV);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        Intent data = getIntent();
        resultDate = data.getStringExtra("Date");
        team = data.getStringExtra("Team");

        scoreTV.setText(team);
        date.setText(resultDate);

        mStore.collection("ocrfcFixtures").whereEqualTo("date", resultDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String doc = document.getId();

                                DocumentReference documentReference = mStore.collection("ocrfcFixtures").document(doc);

                                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (team.equals("1st XV Result")) {
                                            String opponent = value.get("firstsOpponent").toString();
                                            opposition.setText(opponent);
                                        }else if (team.equals("2nd XV Result")) {
                                            String opponent = value.get("secondsOpponent").toString();
                                            opposition.setText(opponent);
                                        }else if (team.equals("B XV Result")) {
                                            String opponent = value.get("bsOpponent").toString();
                                            opposition.setText(opponent);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStore.collection("ocrfcFixtures").whereEqualTo("date", resultDate)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String doc = document.getId();

                                        DocumentReference documentReference = mStore.collection("ocrfcFixtures").document(doc);

                                        ocScored = ocScore.getText().toString();
                                        oppScored = oppScore.getText().toString();

                                        Map<String, Object> edited = new HashMap<>();

                                        if (team.equals("1st XV Result")) {
                                            edited.put("firstsScore", ocScored);
                                            edited.put("firstsOppScore", oppScored);
                                        }else if (team.equals("2nd XV Result")) {
                                            edited.put("secondsScore", ocScored);
                                            edited.put("secondsOppScore", oppScored);
                                        }else if (team.equals("B XV Result")) {
                                            edited.put("bsScore", ocScored);
                                            edited.put("bsOppScore", oppScored);
                                        }

                                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SetResult.this, "Score successfully updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                                    }
                                }
                            }
                        });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}