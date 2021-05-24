package com.felipe.twitchflix;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.felipe.twitchflix.LoginActivity.KEY_CHECKBOX;
import static com.felipe.twitchflix.LoginActivity.SHAREDPREF_KEY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    // Static values
    private static final String ANONYMOUS = "Anonymous";

    // Views & Layouts
    static ViewHolder mViewHolder = new ViewHolder();
    private DrawerLayout mDrawer;

    // Value holders
    private String mUsername;
    private String m_Text;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    // Shared Preferences
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is logged
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.main_drawer);
        setNavigationViewListener();

        sharedPref = getSharedPreferences(SHAREDPREF_KEY, MODE_PRIVATE);

        // Firebase instantiation
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser.getDisplayName();

        Log.d(CreateAccountActivity.TAG, "mUsername: " + mUsername);

        // View linking
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_title);

        // For some reason this doesn't work
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navUsername.setText(mUsername);
            }
        });
        // navUsername.setText(mUsername);

        this.mViewHolder.watchButton = findViewById(R.id.watch_button);
        this.mViewHolder.streamButton = findViewById(R.id.stream_button);
        this.mViewHolder.deleteAccount = findViewById(R.id.delete_account);


        this.mViewHolder.watchButton.setOnClickListener(this);
        this.mViewHolder.streamButton.setOnClickListener(this);

        mDrawer = findViewById(R.id.drawer_layout);

        this.mViewHolder.deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Are you sure?");
                builder.setMessage("Account deletion cannot be undone!");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this, DeleteActivity.class));
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    // If drawer is open, back button closes it
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
        switch (item.getItemId()) {
            case R.id.logout:
                signOut();
                break;
        }
        return true;
    }

    private static class ViewHolder {
        Button watchButton;
        Button streamButton;
        TextView deleteAccount;
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }

        return ANONYMOUS;
    }

    private void signOut() {
        editor = sharedPref.edit();
        editor.putBoolean(KEY_CHECKBOX, false);
        editor.apply();
        mFirebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.signOut();
    }
}