package com.example.ocrugbyapp.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";


    EditText email, password;
    Button LoginBtn, SignUpBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.Email_editText);
        password = findViewById(R.id.Password_editText2);
        LoginBtn = findViewById(R.id.LoginBtn);
        SignUpBtn = findViewById(R.id.SignUpBtn2);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        forgotPassword = findViewById(R.id.forgotPassword);

        //login button and conditions
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r_email = email.getText().toString().trim();
                String r_password = password.getText().toString().trim();

                if (TextUtils.isEmpty(r_email)){
                    email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(r_password)){
                    password.setError("Password is required");
                    return;
                }

                if (r_password.length() < 6){
                    password.setError("Password must contain more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //checking the email and password
                mAuth.signInWithEmailAndPassword(r_email, r_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (task.isSuccessful()){
                            try {
                                if (user.isEmailVerified()) {
                                    Log.d(TAG, "OnComplete: Success, email is verified.");
                                    Intent intent = new Intent(Login.this, Home.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this,"Email is not verified\nPlease check your email inbox", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                }

                            } catch (NullPointerException e) {
                                Log.e(TAG, "OnComplete: NullPointerException: " + e.getMessage());
                            }
                        }else {
                            Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        //sign up button
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });



        //forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your email to reset password");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Reset link not sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }
}
