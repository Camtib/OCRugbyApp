package com.example.ocrugbyapp.teams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.members.Members;
import com.example.ocrugbyapp.members.MembersCard;
import com.example.ocrugbyapp.members.MembersListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminPickTeamList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    String position;
    String team;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    List<TeamPlayer> playerList;
    String userID;
    Query searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pick_team_list);

        Intent data = getIntent();
        position = data.getStringExtra("Position");
        team = data.getStringExtra("Team");
        mRecyclerView = findViewById(R.id.recyclerViewPlayers);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        searchQuery = mStore.collection("users").whereEqualTo("Available", true);
        showAdapter(searchQuery);


    }

    private void updateTeam(String team, String position, String userID) {
        DocumentReference documentReference = mStore.collection("teams").document(team);
        documentReference.update(position, userID).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent1 = new Intent(AdminPickTeamList.this, Teams.class);
                startActivity(intent1);
            }
        });
    }

    private void showAdapter(Query q1) {
        q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                final List<TeamPlayer> playerList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        userID = document.getId();
                        playerList.add(new TeamPlayer(userID));
                    }
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(AdminPickTeamList.this));
                    mRecyclerView.setAdapter(new AdminPickListAdapter(AdminPickTeamList.this, playerList));
                }
            }
        });
    }
}