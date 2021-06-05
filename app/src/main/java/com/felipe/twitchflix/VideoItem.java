package com.felipe.twitchflix;

public class VideoItem {
    private String mThumbnailUrl;
    private String mTitle;
    private String mAuthor;

    public VideoItem(String thumbnailUrl, String title, String author) {
        mThumbnailUrl = thumbnailUrl;
        mTitle = title;
        mAuthor = author;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
