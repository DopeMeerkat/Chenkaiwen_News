package com.java.chenkaiwen;

public class NewsObj {
    private String mTitle;
    private String mAuthor;
    private String mDate;
    private String mUrl;
    private String mThumbnail;
    private String mTrailTextHtml;
    private boolean read;

    public NewsObj(String title, String author, String date, String url, String thumbnail, String trailText) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
        mTrailTextHtml = trailText;
        read = false;
    }
    public String getTitle() {
        return mTitle;
    }
    public String getAuthor() { return mAuthor; }
    public String getDate() { return mDate; }
    public String getUrl() {
        return mUrl;
    }
    public String getThumbnail() {
        return mThumbnail;
    }
    public String getTrailTextHtml() {
        return mTrailTextHtml;
    }
    public boolean getRead() { return read; }
}
