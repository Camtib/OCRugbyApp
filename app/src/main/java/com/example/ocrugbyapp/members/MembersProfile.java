package com.example.ocrugbyapp.members;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.profile.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MembersProfile extends AppCompatActivity {

    private static final String TAG = "Profile";
    TextView name, nickname, email, mobile_number, preferred_position, secondPosition, thirdPosition;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    ImageView profilePic, backBtn;
    StorageReference mStorageRef;
    ProgressBar progressBar;
    Button adminBtn;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_profile);

        name = findViewById(R.id.name_textView);
        nickname = findViewById(R.id.nickname_textView);
        email = findViewById(R.id.email_textView);
        mobile_number = findViewById(R.id.mobileNumber_textView);
        preferred_position = findViewById(R.id.position_textView);
        secondPosition = findViewById(R.id.secondPosition);
        thirdPosition = findViewById(R.id.thirdPosition);
        profilePic = findViewById(R.id.profilePic);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);
        adminBtn = findViewById(R.id.adminBtn);


        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        CollectionReference collectionReference = mStore.collection("users");

        Intent data = getIntent();
        String member_name = data.getStringExtra("Member Name");

        collectionReference
                .whereEqualTo("Name", member_name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    String doc_id = document.getId();

                                    StorageReference profileRef = mStorageRef.child("users/"+doc_id+"/profile.jpg");
                                    profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).into(profilePic);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "storage failed");
                                        }
                                    });

                                    name.setText(document.getString("Name"));
                                    nickname.setText(document.getString("Nickname"));
                                    email.setText(document.getString("Email"));
                                    mobile_number.setText(document.getString("Mobile_Number"));
                                    preferred_position.setText(document.getString("PreferredPosition"));
                                    secondPosition.setText(document.getString("SecondPosition"));
                                    thirdPosition.setText(document.getString("ThirdPosition"));

                                    progressBar.setVisibility(View.GONE);

                                    String currentUser = mAuth.getCurrentUser().getUid();

                                    DocumentReference documentReference2 = mStore.collection("users").document(currentUser);
                                    documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (value.get("Admin").equals(true)) {
                                                DocumentReference documentReference3 = mStore.collection("users").document(doc_id);

                                                documentReference3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        if (value.get("Admin").equals(false)) {
                                                            adminBtn.setVisibility(View.VISIBLE);
                                                            adminBtn.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    documentReference3.update("Admin", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Toast.makeText(MembersProfile.this, "member has successfully been added as an admin", Toast.LENGTH_SHORT).show();
                                                                            adminBtn.setVisibility(View.GONE);
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        }else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MembersProfile.this, Members.class));
            }
        });
    }
}
