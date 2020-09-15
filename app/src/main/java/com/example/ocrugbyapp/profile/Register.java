package com.example.ocrugbyapp.profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.example.ocrugbyapp.home.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText firstName, surname, email, password, confirmPassword;
    Button signUpBtn;
    TextView loginBtn2;
    FirebaseAuth mAuth;
    ProgressBar progressBarSignUp;
    FirebaseFirestore mStore;
    StorageReference mStorageRef;
    String userID;
    Uri genericPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.First_Name_editText);
        surname = findViewById(R.id.Surname_editText2);
        email = findViewById(R.id.Email_editText);
        password = findViewById(R.id.Password_editText2);
        confirmPassword = findViewById(R.id.Confirm_Password_editText5);
        signUpBtn = findViewById(R.id.SignUpBtn);
        loginBtn2 = findViewById(R.id.LoginTextView);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        progressBarSignUp = findViewById(R.id.SignUpprogressBar);



        //sign in buttons and conditions
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String r_email = email.getText().toString().trim();
                String r_password = password.getText().toString().trim();
                String r_confirmPassword = confirmPassword.getText().toString().trim();
                final String r_firstName = firstName.getText().toString();
                final String r_surname = surname.getText().toString();

                if (TextUtils.isEmpty(r_email)){
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(r_password)){
                    password.setError("Password is required");
                    return;
                }

                if (r_password.length() < 8){
                    password.setError("Password must contain more than 8 characters");
                    return;
                }

                if (!r_password.equals(r_confirmPassword)){
                    confirmPassword.setError("Passwords do not match");
                    return;
                }

                progressBarSignUp.setVisibility(View.VISIBLE);




                mAuth.createUserWithEmailAndPassword(r_email, r_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //email verification
                            FirebaseUser fuser = mAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification email sent", Toast.LENGTH_SHORT).show();

                                    mAuth.signOut();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: Email not sent" + e.getMessage());

                                }
                            });

                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mStore.collection("users").document(userID);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", r_firstName + " " + r_surname);
                            user.put("Email",r_email);
                            user.put("PreferredPosition", "Preferred Position");
                            user.put("SecondPosition", "Second Choice Position");
                            user.put("ThirdPosition", "Third Choice Position");
                            user.put("Nickname", "AKA");
                            user.put("Mobile_Number", "Mobile Number");

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "User profile has been created for "+ userID);
                                }
                            });

                            mStorageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference profileRef = mStorageRef.child("generic_profile_pic/Logo-1-e1475594787455.jpg");
                            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    genericPic = uri;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "storage failed");
                                }
                            });
                            
                            final StorageReference fileRef = mStorageRef.child("users/"+userID+"/profile.jpg");
                            fileRef.putFile(genericPic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("TAG", "Generic profile picture has been uploaded");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "Generic profile picture failed to upload");
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }else {
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarSignUp.setVisibility(View.GONE);
                        }
                    }
                });

                finish();
            }
        });




        loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
