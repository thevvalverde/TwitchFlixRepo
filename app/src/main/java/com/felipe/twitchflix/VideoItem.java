package com.felipe.twitchflix;

public class VideoItem {
    private String mUrl;
    private String mTitle;
    private String mType;

    public VideoItem(String url, String title, String type) {
        mUrl = url;
        mTitle = title;
        mType = type;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getType() {
        return mType;
    }
}
