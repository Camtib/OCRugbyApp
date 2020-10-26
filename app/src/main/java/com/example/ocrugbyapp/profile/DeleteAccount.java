package com.example.ocrugbyapp.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ocrugbyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccount extends AppCompatActivity {

    TextView deleteBtn, cancelBtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        deleteBtn = findViewById(R.id.deleteBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                private static final String TAG = "deleteAccount";

                @Override
                public void onClick(View v) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User account deleted.");
                                        Intent intent = new Intent(DeleteAccount.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeleteAccount.this, Profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            startActivity(new Intent(DeleteAccount.this, Login.class));
            finish();
        }
    }
}