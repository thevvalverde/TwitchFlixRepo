package com.felipe.twitchflix;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VodActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "videoUrl";

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<VideoItem> mList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);

        mRecyclerView = findViewById(R.id.vod_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                List<String> list = new ArrayList<>();
                try {
                    list = OpenHttpConnection("http://34.141.247.38/watch/vods/");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<String> finalList = list;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < finalList.size(); i++) {
                            String[] parts = finalList.get(i).split(";");

                            String title = parts[0];
                            String url = parts[1];
                            String type = parts[2];
                            Log.d(CreateAccountActivity.TAG, "Adding title " + title);
                            mList.add(new VideoItem(url, title, type));
                        }
                        mAdapter = new MyAdapter(VodActivity.this, mList);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(VodActivity.this);
                    }
                });
            }
        });


    }

    private List<String> OpenHttpConnection(String strURL)
            throws IOException {
        Log.d(CreateAccountActivity.TAG, "Reading data");
        List<String> response = new ArrayList<>();
        URL url = new URL(strURL);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while((inputLine=in.readLine())!=null) {
            response.add(inputLine);
        }
        return response;
    }

    @Override
    public void OnItemClick(int pos) {
        String url = mList.get(pos).getUrl();
        Log.d(CreateAccountActivity.TAG, url);

        Intent intent = new Intent(VodActivity.this, VideoActivity.class);
        intent.putExtra(EXTRA_URL, url);
        startActivity(intent);
    }
}
