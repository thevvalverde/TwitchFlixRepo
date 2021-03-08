package com.felipe.twitchflix;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button mLoginButton;
    EditText mUsername;
    EditText mPassword;
    String mUserText = "";
    String mPassText = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = findViewById(R.id.login_button);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mUsername.getText())){
                    Toast.makeText(getApplicationContext(), "Please insert a valid username or email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPassword.getText())){
                    Toast.makeText(getApplicationContext(), "Please insert your password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
