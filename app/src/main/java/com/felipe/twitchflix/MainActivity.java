package com.felipe.twitchflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.felipe.twitchflix.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.watchButton = findViewById(R.id.watch_button);
        this.mViewHolder.streamButton = findViewById(R.id.stream_button);

        this.mViewHolder.watchButton.setOnClickListener(this);
        this.mViewHolder.streamButton.setOnClickListener(this);

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

    private static class ViewHolder {
        Button watchButton;
        Button streamButton;
    }
}