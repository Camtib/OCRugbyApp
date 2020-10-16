package com.example.ocrugbyapp.teams;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ocrugbyapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


public class FirstTeam extends Fragment {

    TextView no1playerName, no2playerName, no3playerName, no4playerName, no5playerName,
            no6playerName, no7playerName, no8playerName, no9playerName, no10playerName,
            no11playerName, no12playerName, no13playerName, no14playerName, no15playerName;

    TextView sub1name, sub2name, sub3name, sub4name, sub5name, sub6name, sub7name, sub8name,sub9name;

    ImageView no1player, no2player, no3player, no4player, no5player, no6player, no7player, no8player,
            no9player, no10player, no11player, no12player, no13player, no14player, no15player,
            no16player, no17player, no18player, no19player, no20player, no21player, no22player,
            no23player, no24player;

    Button confirmTeamBtn, confirmSubsBtn;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firsts_team, container, false);

        no1playerName = view.findViewById(R.id.no1playerName);
        no2playerName = view.findViewById(R.id.no2playerName);
        no3playerName = view.findViewById(R.id.no3playerName);
        no4playerName = view.findViewById(R.id.no4playerName);
        no5playerName = view.findViewById(R.id.no5playerName);
        no6playerName = view.findViewById(R.id.no6playerName);
        no7playerName = view.findViewById(R.id.no7playerName);
        no8playerName = view.findViewById(R.id.no8playerName);
        no9playerName = view.findViewById(R.id.no9playerName);
        no10playerName = view.findViewById(R.id.no10playerName);
        no11playerName = view.findViewById(R.id.no11playerName);
        no12playerName = view.findViewById(R.id.no12playerName);
        no13playerName = view.findViewById(R.id.no13playerName);
        no14playerName = view.findViewById(R.id.no14playerName);
        no15playerName = view.findViewById(R.id.no15playerName);

        sub1name = view.findViewById(R.id.player16Name);
        sub2name = view.findViewById(R.id.player17Name);
        sub3name = view.findViewById(R.id.player18Name);
        sub4name = view.findViewById(R.id.player19Name);
        sub5name = view.findViewById(R.id.player20Name);
        sub6name = view.findViewById(R.id.player21Name);
        sub7name = view.findViewById(R.id.player22Name);
        sub8name = view.findViewById(R.id.player23Name);
        sub9name = view.findViewById(R.id.player24Name);

        no1player = view.findViewById(R.id.no1Player);
        no2player = view.findViewById(R.id.no2Player);
        no3player = view.findViewById(R.id.no3Player);
        no4player = view.findViewById(R.id.no4Player);
        no5player = view.findViewById(R.id.no5Player);
        no6player = view.findViewById(R.id.no6Player);
        no7player = view.findViewById(R.id.no7Player);
        no8player = view.findViewById(R.id.no8Player);
        no9player = view.findViewById(R.id.no9Player);
        no10player = view.findViewById(R.id.no10Player);
        no11player = view.findViewById(R.id.no11Player);
        no12player = view.findViewById(R.id.no12Player);
        no13player = view.findViewById(R.id.no13Player);
        no14player = view.findViewById(R.id.no14Player);
        no15player = view.findViewById(R.id.no15Player);
        no16player = view.findViewById(R.id.player16);
        no17player = view.findViewById(R.id.player17);
        no18player = view.findViewById(R.id.player18);
        no19player = view.findViewById(R.id.player19);
        no20player = view.findViewById(R.id.player20);
        no21player = view.findViewById(R.id.player21);
        no22player = view.findViewById(R.id.player22);
        no23player = view.findViewById(R.id.player23);
        no24player = view.findViewById(R.id.player24);

        confirmTeamBtn = view.findViewById(R.id.confirmSelectionBtn);
        confirmSubsBtn = view.findViewById(R.id.confirmSubSelectionBtn);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = mStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.get("Admin").equals(false)) {

                    confirmTeamBtn.setClickable(false);
                    confirmTeamBtn.setText(getString(R.string.firstXVStarters));

                    confirmSubsBtn.setClickable(false);
                    confirmSubsBtn.setText(getString(R.string.firstXVFinishers));

                    getSelectedPlayer(no1playerName,"1", "Loosehead Prop");
                    getSelectedPlayer(no2playerName, "2", "Hooker");
                    getSelectedPlayer(no3playerName, "3", "Tighthead Prop");
                    getSelectedPlayer(no4playerName, "4", "Lock");
                    getSelectedPlayer(no5playerName, "5", "Lock");
                    getSelectedPlayer(no6playerName, "6", "Blindside Flanker");
                    getSelectedPlayer(no7playerName, "7", "Openside Flanker");
                    getSelectedPlayer(no8playerName, "8", "Number 8");
                    getSelectedPlayer(no9playerName, "9", "Scrum-half");
                    getSelectedPlayer(no10playerName, "10", "Fly-half");
                    getSelectedPlayer(no11playerName, "11", "Left Wing");
                    getSelectedPlayer(no12playerName, "12", "Inside Centre");
                    getSelectedPlayer(no13playerName, "13", "Outside Centre");
                    getSelectedPlayer(no14playerName, "14", "Right Wing");
                    getSelectedPlayer(no15playerName, "15", "Full Back");
                    getSelectedPlayer(sub1name, "sub1", "Sub 1");
                    getSelectedPlayer(sub2name, "sub2", "Sub 2");
                    getSelectedPlayer(sub3name, "sub3", "Sub 3");
                    getSelectedPlayer(sub4name, "sub4", "Sub 4");
                    getSelectedPlayer(sub5name, "sub5", "Sub 5");
                    getSelectedPlayer(sub6name, "sub6", "Sub 6");
                    getSelectedPlayer(sub7name, "sub7", "Sub 7");
                    getSelectedPlayer(sub8name, "sub8", "Sub 8");
                    getSelectedPlayer(sub9name, "sub9", "Sub 9");

                }else {

                    getSelectedPlayerAdmin(no1playerName,"1", "Loosehead Prop");
                    getSelectedPlayerAdmin(no2playerName, "2", "Hooker");
                    getSelectedPlayerAdmin(no3playerName, "3", "Tighthead Prop");
                    getSelectedPlayerAdmin(no4playerName, "4", "Lock");
                    getSelectedPlayerAdmin(no5playerName, "5", "Lock");
                    getSelectedPlayerAdmin(no6playerName, "6", "Blindside Flanker");
                    getSelectedPlayerAdmin(no7playerName, "7", "Openside Flanker");
                    getSelectedPlayerAdmin(no8playerName, "8", "Number 8");
                    getSelectedPlayerAdmin(no9playerName, "9", "Scrum-half");
                    getSelectedPlayerAdmin(no10playerName, "10", "Fly-half");
                    getSelectedPlayerAdmin(no11playerName, "11", "Left Wing");
                    getSelectedPlayerAdmin(no12playerName, "12", "Inside Centre");
                    getSelectedPlayerAdmin(no13playerName, "13", "Outside Centre");
                    getSelectedPlayerAdmin(no14playerName, "14", "Right Wing");
                    getSelectedPlayerAdmin(no15playerName, "15", "Full Back");
                    getSelectedPlayerAdmin(sub1name, "sub1", "Sub 1");
                    getSelectedPlayerAdmin(sub2name, "sub2", "Sub 2");
                    getSelectedPlayerAdmin(sub3name, "sub3", "Sub 3");
                    getSelectedPlayerAdmin(sub4name, "sub4", "Sub 4");
                    getSelectedPlayerAdmin(sub5name, "sub5", "Sub 5");
                    getSelectedPlayerAdmin(sub6name, "sub6", "Sub 6");
                    getSelectedPlayerAdmin(sub7name, "sub7", "Sub 7");
                    getSelectedPlayerAdmin(sub8name, "sub8", "Sub 8");
                    getSelectedPlayerAdmin(sub9name, "sub9", "Sub 9");

                    selectPlayer(no1player, "1");
                    selectPlayer(no2player, "2");
                    selectPlayer(no3player, "3");
                    selectPlayer(no4player, "4");
                    selectPlayer(no5player, "5");
                    selectPlayer(no6player, "6");
                    selectPlayer(no7player, "7");
                    selectPlayer(no8player, "8");
                    selectPlayer(no9player, "9");
                    selectPlayer(no10player, "10");
                    selectPlayer(no11player, "11");
                    selectPlayer(no12player, "12");
                    selectPlayer(no13player, "13");
                    selectPlayer(no14player, "14");
                    selectPlayer(no15player, "15");
                    selectPlayer(no16player, "sub1");
                    selectPlayer(no17player, "sub2");
                    selectPlayer(no18player, "sub3");
                    selectPlayer(no19player, "sub4");
                    selectPlayer(no20player, "sub5");
                    selectPlayer(no21player, "sub6");
                    selectPlayer(no22player, "sub7");
                    selectPlayer(no23player, "sub8");
                    selectPlayer(no24player, "sub9");
                }
            }
        });

        return view;
    }

    private void getSelectedPlayer(TextView textView, String field, String positionName) {
        DocumentReference documentReference1 = mStore.collection("teams").document("FirstsTeam");
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String playerID = documentSnapshot.getString(field);
                if (playerID.isEmpty()) {
                    textView.setText(positionName);
                }else {
                    DocumentReference docRef = mStore.collection("users").document(playerID);
                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot.getString("Nickname").isEmpty()) {
                                textView.setText(documentSnapshot.getString("Name"));
                            }else {
                                textView.setText(documentSnapshot.getString("Nickname"));
                            }
                        }
                    });
                }
            }
        });
    }

    private void getSelectedPlayerAdmin(TextView textView, String field, String positionName) {
        DocumentReference documentReference1 = mStore.collection("teams").document("FirstsTeam");
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String playerID = documentSnapshot.getString(field);
                if (playerID.isEmpty()) {
                    textView.setText(getString(R.string.selectPlayer));
                }else {
                    DocumentReference docRef = mStore.collection("users").document(playerID);
                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot.getString("Nickname").isEmpty()) {
                                textView.setText(documentSnapshot.getString("Name"));
                            }else {
                                textView.setText(documentSnapshot.getString("Nickname"));
                            }
                        }
                    });
                }
            }
        });
    }

    private void selectPlayer(ImageView imageView, String position) {
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminPickTeamList.class);
                intent.putExtra("Position", position);
                intent.putExtra("Team", "FirstsTeam");
                startActivity(intent);
            }
        });
    }
}