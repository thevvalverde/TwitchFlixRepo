package com.felipe.twitchflix;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    Button mCreateButton;
    EditText mUserText;
    EditText mMailText;
    EditText mPassText;
    EditText mPassCheckText;
    FirebaseAuth mAuth;

    private String mUser;
    private String mMail;
    private String mPass;
    private String mPassCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                if(mPass.length()<6) {
                    Toast.makeText(CreateAccountActivity.this, "Please insert a password of at least 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!mPass.equals(mPassCheck)) {
                    Toast.makeText(CreateAccountActivity.this, "Please make sure you typed the same password twice!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }
}
