package com.felipe.twitchflix;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String url = getIntent().getStringExtra(VodActivity.EXTRA_URL);
        Log.d(CreateAccountActivity.TAG, url);
        Uri uri = Uri.parse(url);

        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoURI(uri);
        MediaController ctlr = new MediaController(this);
        ctlr.setMediaPlayer(videoView);
        videoView.setMediaController(ctlr);
        videoView.requestFocus();
        videoView.start();
    }
}
