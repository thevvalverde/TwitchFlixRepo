package com.felipe.twitchflix;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VodActivity extends AppCompatActivity {
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
        //getItems();
    }

    private void getItems() {
        // TODO
        // retrieve data and insert into list

        //    for(int i = 0; i < ?; i++) {
        //        String title = ?;
        //        String author = ?;
        //        String thumbnailUrl = ?;
        //
        //        mList.add(new VideoItem(title, author, thumbnailUrl))
        //    }

        mAdapter = new MyAdapter(VodActivity.this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
