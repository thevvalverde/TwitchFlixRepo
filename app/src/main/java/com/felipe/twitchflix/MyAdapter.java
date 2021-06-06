package com.felipe.twitchflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<VideoItem> mVideoList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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

        String url = curItem.getUrl();
        String title = curItem.getTitle();
        String type = curItem.getType();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewType.setText(type);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewTitle;
        public TextView mTextViewType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.video_title);
            mTextViewType = itemView.findViewById(R.id.video_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
