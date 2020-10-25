package com.example.ocrugbyapp.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.fixtures.Fixtures;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name;
    EditText nickname;
    EditText email;
    EditText mobile_number;
    TextView logoutBtn, deleteBtn;
    TextView changeProfileBtn;
    String userID;
    Spinner preferredPosition, secondPosition, thirdPosition;


    private static final String TAG = "Settings";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mStore;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name = findViewById(R.id.editFistName);
        nickname = findViewById(R.id.editNickname);
        email = findViewById(R.id.editEmail);
        mobile_number = findViewById(R.id.editMobileNumber);
        logoutBtn = findViewById(R.id.logoutBtn);
        changeProfileBtn = findViewById(R.id.changeProfileBtn);
        deleteBtn = findViewById(R.id.deleteAccount);
        mStore = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Intent data = getIntent();
        String fullName = data.getStringExtra("Name");
        String fullNickname = data.getStringExtra("Nickname");
        String fullEmail = data.getStringExtra("Email");
        String fullMobile_Number = data.getStringExtra("Mobile_Number");
        String fullPreferredPosition = data.getStringExtra("Preferred Position");
        String fullSecondPosition = data.getStringExtra("Second Position");
        String fullThirdPosition = data.getStringExtra("Third Position");



        Log.d(TAG, "onCreate: "+ fullName + " " + fullNickname + " " + fullEmail  + " " + fullMobile_Number);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        name.setText(fullName);
        nickname.setText(fullNickname);
        email.setText(fullEmail);
        mobile_number.setText(fullMobile_Number);


        preferredPosition = findViewById(R.id.PreferredPosition);
        secondPosition = findViewById(R.id.SecondPosition);
        thirdPosition = findViewById(R.id.ThirdPosition);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.positions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        preferredPosition.setAdapter(adapter);
        secondPosition.setAdapter(adapter);
        thirdPosition.setAdapter(adapter);

        if (fullPreferredPosition != null) {
            int spinnerPrefPosition = adapter.getPosition(fullPreferredPosition);
            preferredPosition.setSelection(spinnerPrefPosition);
        }

        if (fullSecondPosition != null) {
            int spinnerSecPosition = adapter.getPosition(fullSecondPosition);
            secondPosition.setSelection(spinnerSecPosition);
        }

        if (fullThirdPosition != null) {
            int spinnerThirdPosition = adapter.getPosition(fullThirdPosition);
            thirdPosition.setSelection(spinnerThirdPosition);
        }

        preferredPosition.setOnItemSelectedListener(this);
        secondPosition.setOnItemSelectedListener(this);
        thirdPosition.setOnItemSelectedListener(this);


        final String preferred_position = preferredPosition.getSelectedItem().toString();
        final String second_position = secondPosition.getSelectedItem().toString();
        final String third_position = thirdPosition.getSelectedItem().toString();



        //allowing items in nav bar to be clicked and change activity
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.backArrow:
                        Intent intent1 = new Intent(Settings.this, Fixtures.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        finish();
                        break;

                    case R.id.profile:
                        Intent intent2 = new Intent(Settings.this, Profile.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        break;

                    case R.id.settingsBtn:
                        break;

                }

                return false;
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, DeleteAccount.class);
                startActivity(intent);
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        changeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || nickname.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                        || mobile_number.getText().toString().isEmpty()) {
                    Toast.makeText(Settings.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                email = (EditText) findViewById(R.id.editEmail);

                final String femail = email.getText().toString();

                final String preferred_position = preferredPosition.getSelectedItem().toString();
                final String second_position = secondPosition.getSelectedItem().toString();
                final String third_position = thirdPosition.getSelectedItem().toString();

                name = (EditText) findViewById(R.id.editFistName);
                nickname = (EditText) findViewById(R.id.editNickname);
                mobile_number = (EditText) findViewById(R.id.editMobileNumber);


                user.updateEmail(femail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        DocumentReference documentReference = mStore.collection("users").document(userID);
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("Email", femail);
                        edited.put("Name", name.getText().toString());
                        edited.put("Nickname", nickname.getText().toString());
                        edited.put("Mobile_Number", mobile_number.getText().toString());
                        edited.put("PreferredPosition", preferred_position);
                        edited.put("SecondPosition", second_position);
                        edited.put("ThirdPosition", third_position);

                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Email update success");
                                Toast.makeText(Settings.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Settings.this, Profile.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Email update failed");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String fposition = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}