package com.felipe.twitchflix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {
    Button mLoginButton;
    EditText mUsername;
    EditText mPassword;
    String mUserText = "";
    String mPassText = "";
    final String TAG = "TwitchFlix";git c

    // Firebase instances variables
    FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mLoginButton = findViewById(R.id.login_button);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserText = mUsername.getText().toString();
                mPassText = mPassword.getText().toString();
                if(mUserText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please insert a valid username or email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPassText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please insert your password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO
                // CREDENTIAL CHECKING
                tryLogin(mUserText, mPassText);

                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
            }
        });

    }

    private void tryLogin(String user, String pass) {
        mFirebaseAuth.signInWithEmailAndPassword(mUserText, mPassText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "Login successful.");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        else {
                            Log.d(TAG, "Login Failed.");
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
