package com.felipe.twitchflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<VideoItem> mVideoList;

    public MyAdapter(Context context, ArrayList<VideoItem> list) {
        mContext = context;
        mVideoList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.video_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoItem curItem = mVideoList.get(position);

        String thumbnailUrl = curItem.getThumbnailUrl();
        String title = curItem.getTitle();
        String author = curItem.getAuthor();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewAuthor.setText(author);
        Picasso.get().load(thumbnailUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewAuthor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.video_thumbnail);
            mTextViewTitle = itemView.findViewById(R.id.video_title);
            mTextViewAuthor = itemView.findViewById(R.id.video_author);
        }
    }
}
