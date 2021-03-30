package com.felipe.twitchflix;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

public class CreateAccountActivity extends AppCompatActivity {
    Button mCreateButton;
    EditText mUserText;
    EditText mMailText;
    EditText mPassText;
    EditText mPassCheckText;

    // Firebase instances variables
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference myUserRef;
    FirebaseUser mFirebaseUser;

    private String mUser;
    private String mMail;
    private String mPass;
    private String mPassCheck;

    public static final String TAG = "TwitchFlix";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance();
        myUserRef = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mCreateButton = findViewById(R.id.create_account_button);
        mUserText = findViewById(R.id.user_edit_text);
        mMailText = findViewById(R.id.email_edit_text);
        mPassText = findViewById(R.id.pass_edit_text);
        mPassCheckText = findViewById(R.id.passcheck_edit_text);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = mUserText.getText().toString();
                mMail = mMailText.getText().toString();
                mPass = mPassText.getText().toString();
                mPassCheck = mPassCheckText.getText().toString();

                if(!mMail.contains("@")) {
                    Toast.makeText(CreateAccountActivity.this, "Please insert a valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mUser.length()<5 || mUser.length()>15) {
                    Toast.makeText(CreateAccountActivity.this, "Please insert a user with 5 to 14 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPass.length()<6) {
                    Toast.makeText(CreateAccountActivity.this, "Please insert a password of at least 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!mPass.equals(mPassCheck)) {
                    Toast.makeText(CreateAccountActivity.this, "Please make sure you typed the same password twice!", Toast.LENGTH_SHORT).show();
                    return;
                }
                myUserRef.child("users").orderByChild("username").equalTo(mUser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            Toast.makeText(CreateAccountActivity.this, "This username is already taken!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            tryToCreate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
                }

        });

    }

    public void tryToCreate() {
        mAuth.createUserWithEmailAndPassword(mMail, mPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthUserCollisionException existEmail)
                            {
                                Toast.makeText(CreateAccountActivity.this, "This email is already linked!", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
                        } else {
                            mFirebaseUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mUser)
                                    .build();
                            mFirebaseUser.updateProfile(profileUpdates);
                            myUserRef.child("users").child(mUser).setValue(new User(mUser, mMail));
                            startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                            finish();
                            return;
                        }
                    }
                });
    }

}
