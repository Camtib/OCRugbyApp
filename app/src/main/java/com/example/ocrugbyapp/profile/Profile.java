package com.example.ocrugbyapp.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.home.Home;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {

    private static final String TAG = "Profile";
    TextView name, nickname, email, mobile_number, preferred_position, secondPosition, thirdPosition;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userID;
    ImageView profilePic, homeBtn;
    StorageReference mStorageRef;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name_textView);
        nickname = findViewById(R.id.nickname_textView);
        email = findViewById(R.id.email_textView);
        mobile_number = findViewById(R.id.mobileNumber_textView);
        preferred_position = findViewById(R.id.position_textView);
        secondPosition = findViewById(R.id.secondPosition);
        thirdPosition = findViewById(R.id.thirdPosition);
        profilePic = findViewById(R.id.profilePic);
        homeBtn = findViewById(R.id.homeBtn);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        mAuth = FirebaseAuth.getInstance();

        mStore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        StorageReference profileRef = mStorageRef.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
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




        //allowing items in nav bar to be clicked and change activity
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.backArrow:
                        Intent intent1 = new Intent(Profile.this, Home.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;

                    case R.id.profile:
                        break;

                    case R.id.settingsBtn:
                        Intent intent2 = new Intent(Profile.this, Settings.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent2.putExtra("Name", name.getText().toString());
                        intent2.putExtra("Nickname", nickname.getText().toString());
                        intent2.putExtra("Email", email.getText().toString());
                        intent2.putExtra("Mobile_Number", mobile_number.getText().toString());
                        intent2.putExtra("Preferred Position", preferred_position.getText().toString());
                        intent2.putExtra("Second Position", secondPosition.getText().toString());
                        intent2.putExtra("Third Position", thirdPosition.getText().toString());
                        startActivity(intent2);
                        break;

                }

                return false;
            }
        });

        userID = mAuth.getCurrentUser().getUid();

        //user info storage
        DocumentReference documentReference = mStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    name.setText(documentSnapshot.getString("Name"));
                    nickname.setText(documentSnapshot.getString("Nickname"));
                    email.setText(documentSnapshot.getString("Email"));
                    mobile_number.setText(documentSnapshot.getString("Mobile_Number"));
                    preferred_position.setText(documentSnapshot.getString("PreferredPosition"));
                    secondPosition.setText(documentSnapshot.getString("SecondPosition"));
                    thirdPosition.setText(documentSnapshot.getString("ThirdPosition"));
                }else {
                    Log.d(TAG, "document does not exist");
                }
            }
        });


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                profilePic.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = mStorageRef.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePic);
                    }
                });
                Toast.makeText(Profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
