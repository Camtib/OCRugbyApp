package com.example.ocrugbyapp.members;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.example.ocrugbyapp.home.Home;
import com.example.ocrugbyapp.profile.Login;
import com.example.ocrugbyapp.results.Results;
import com.example.ocrugbyapp.profile.Profile;
import com.example.ocrugbyapp.teams.Teams;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Members extends AppCompatActivity {

    private static final String TAG = "Members";

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userID;
    FirebaseUser user;
    StorageReference mStorageRef;
    private ListView membersList;
    EditText search;
    Query searchQuery;
    MembersListAdapter adapter;
    List<MembersCard> members;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(Members.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        membersList = (ListView) findViewById(R.id.contactsListView);
        search = (EditText) findViewById(R.id.editTextSearch);
        searchQuery = mStore.collection("users").orderBy("Name");
        members = new ArrayList<>();
        adapter = new MembersListAdapter(Members.this, R.layout.listview_members, members);
        progressBar = findViewById(R.id.progressBar);

        if (search.getText().toString().isEmpty()) {
            showAdapter(searchQuery);
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().length() == 0) {
                    searchQuery = mStore.collection("users").orderBy("Name");
                } // This is used as if user erases the characters in the search field.
                else {
                    searchQuery = mStore.collection("users").orderBy("Name").startAt(charSequence.toString().trim()).endAt(charSequence.toString().trim() + "\uf8ff"); // name - the field for which you want to make search
                }
                showAdapter(searchQuery);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable charSequence) {

            }
        });

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        ImageView profileBtn = (ImageView) findViewById(R.id.btnProfile);

        //allowing items in nav bar to be clicked and change activity
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.fixtures:
                        Intent intent0 = new Intent(Members.this, Fixtures.class);
                        intent0.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent0);
                        break;

                    case R.id.home:
                        Intent intent1 = new Intent(Members.this, Home.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;

                    case R.id.teams:
                        Intent intent2 = new Intent(Members.this, Teams.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        break;

                    case R.id.results:
                        Intent intent3 = new Intent(Members.this, Results.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        break;

                    case R.id.members:
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

        membersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView fname = view.findViewById(R.id.textViewName);
                String user_name = fname.getText().toString();

                Intent intent = new Intent(Members.this, MembersProfile.class);
                intent.putExtra("Member Name", user_name);
                startActivity(intent);
            }
        });
    }

    private void showAdapter(Query q1) {
        q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                final List<MembersCard> members = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            final String name = document.get("Name").toString();
                            final String nickname = document.get("Nickname").toString();
                            userID = document.getId();

                            members.add(new MembersCard(name, nickname, userID));
                        } else {
                            Toast.makeText(Members.this, "Member not found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    adapter = new MembersListAdapter(Members.this, R.layout.listview_members, members);
                    membersList.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}