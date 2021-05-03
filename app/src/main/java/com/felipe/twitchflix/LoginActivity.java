package com.felipe.twitchflix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Keys
    public static final String SHAREDPREF_KEY = "com.felipe.twitchflix.PREFERENCE_FILE_KEY";
    public static final String KEY_CHECKBOX = "RememberMeKey";
    public static final String KEY_MAILLOGIN = "LoginEmailKey";
    public static final String KEY_PASSLOGIN = "LoginPassKey";

    // Views
    Button mLoginButton;
    Button mCreateAccount;
    Button mResetButton;
    CheckBox mCheckBox;
    EditText mUsername;
    EditText mPassword;

    // Value holders
    String mUserText = "";
    String mPassText = "";

    // Log Tag
    final static String TAG = "TwitchFlix";

    // Shared Preferences
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    // Firebase instances variables
    FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = this.getSharedPreferences(SHAREDPREF_KEY, MODE_PRIVATE);

        // Firebase instantiation
        mFirebaseAuth = FirebaseAuth.getInstance();

        // View linking
        mLoginButton = findViewById(R.id.login_button);
        mCreateAccount = findViewById(R.id.new_account_button);
        mResetButton = findViewById(R.id.forgot_pass_button);
        mCheckBox = findViewById(R.id.remember_me);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

        // Check if user asked to be remembered
        if (sharedPref.getBoolean(KEY_CHECKBOX, false)) {
            mCheckBox.setChecked(true);
            Log.d(TAG, "Checkbox should be checked");
            mUserText = sharedPref.getString(KEY_MAILLOGIN, null);
            mPassText = sharedPref.getString(KEY_PASSLOGIN, null);
            tryLogin(mUserText, mPassText);
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserText = mUsername.getText().toString();
                mPassText = mPassword.getText().toString();
                if (mUserText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert a valid username or email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPassText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert your password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                tryLogin(mUserText, mPassText);

            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
            }
        });

    }

    private void tryLogin(String user, String pass) {
        if (user == null || pass == null) {
            Toast.makeText(this, "There has been an error.", Toast.LENGTH_SHORT).show();
            return;
        }
        mFirebaseAuth.signInWithEmailAndPassword(mUserText, mPassText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Login successful.");
                            if (mCheckBox.isChecked()) {                     // Save user information
                                editor = sharedPref.edit();
                                editor.putBoolean(KEY_CHECKBOX, true);
                                editor.putString(KEY_MAILLOGIN, mUserText);
                                editor.putString(KEY_PASSLOGIN, mPassText);
                                editor.apply();
                            } else {                                        // Make sure checkbox is not ticked
                                editor = sharedPref.edit();
                                editor.putBoolean(KEY_CHECKBOX, false);
                                editor.apply();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            Log.d(TAG, "Login Failed.");
                            editor = sharedPref.edit();
                            editor.putBoolean(KEY_CHECKBOX, false);
                            editor.apply();
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
