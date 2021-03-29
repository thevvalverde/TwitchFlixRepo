package com.felipe.twitchflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.felipe.twitchflix.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String ANONYMOUS = "Anonymous";
    static ViewHolder mViewHolder = new ViewHolder();
    private DrawerLayout mDrawer;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.main_drawer);
        setNavigationViewListener();


        this.mViewHolder.watchButton = findViewById(R.id.watch_button);
        this.mViewHolder.streamButton = findViewById(R.id.stream_button);

        this.mViewHolder.watchButton.setOnClickListener(this);
        this.mViewHolder.streamButton.setOnClickListener(this);

        mDrawer = findViewById(R.id.drawer_layout);

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.stream_button) {
            intent = new Intent(this, StreamActivity.class);
        }
        if (v.getId() == R.id.watch_button) {
            intent = new Intent(this, WatchActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return true;
    }

    private static class ViewHolder {
        Button watchButton;
        Button streamButton;
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;
    }

    private void signOut() {
        mFirebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}