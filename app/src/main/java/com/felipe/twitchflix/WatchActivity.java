package com.felipe.twitchflix;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WatchActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
    }

    public void StartVod(View view) {
        startActivity(new Intent(WatchActivity.this, LiveActivity.class));
    }

    public void StartLive(View view) {
        startActivity(new Intent(WatchActivity.this, VodActivity.class));
    }
}
