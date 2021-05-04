package com.felipe.twitchflix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.felipe.twitchflix.LoginActivity.KEY_CHECKBOX;
import static com.felipe.twitchflix.LoginActivity.SHAREDPREF_KEY;

public class DeleteActivity extends AppCompatActivity {

    // Firebase Fields
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    // Value Holders
    String email;
    String pass;


    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletion);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        email = mUser.getEmail();

        sharedPref = getSharedPreferences(SHAREDPREF_KEY, MODE_PRIVATE);

        EditText editPass = findViewById(R.id.pass_delete);
        Button cancelButton = findViewById(R.id.delete_cancel_button);
        Button deleteButton = findViewById(R.id.delete_delete_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = editPass.getText().toString();

                reauthenticate(email, pass);
            }
        });
    }

    private void reauthenticate(String email, String pass) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass);
        mUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(LoginActivity.TAG, "re-authenticated");
                        delete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DeleteActivity.this, "This password does not match.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }

    private void delete() {
        String user = mUser.getDisplayName();
        mUser.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(LoginActivity.TAG, "Deleted.");
                    }
                });
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users");
        mRef.child(user).removeValue();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
