package com.java.chenkaiwen.News;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import java.util.List;

@Entity(tableName = "news")
public class News {
    @PrimaryKey
    @NonNull
    public String newsID;
    public String url;
    public String image;
    public String publishTime;
    public String language;
    public String video;
    public String title;
    public String content;
    public String publisher;
    public String category;
    public boolean saved;
    public boolean viewed;

    @TypeConverters({NewsConverters.class})
    public List<Keywords> keywords;

    @NonNull
    public String getNewsID() {
        return newsID;
    }
    public String getPublishTime() {
        return publishTime;
    }
    public String getImage() {
        return image;
    }
    public String getCategory() {
        return category;
    }
    public String getVideo() {
        return video;
    }
    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public String getContent() {
        return content;
    }
    public String getLanguage() {
        return language;
    }
    public String getPublisher() { return publisher; }
    public boolean isSaved() {
        return saved;
    }
    public boolean isViewed() {
        return viewed;
    }
    public List<Keywords> getKeywords() {
        return keywords;
    }

    public void setNewsID(@NonNull String newsID) {
        this.newsID = newsID;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setVideo(String video) {
        this.video = video;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
    public void setKeywords(List<Keywords> keywords) {
        this.keywords = keywords;
    }

    public News(
            String newsID,
            String publishTime,
            String image,
            String category,
            String video,
            String title,
            String url,
            String content,
            String language,
            boolean saved,
            boolean viewed,
            List<Keywords> keywords
    ) {
        this.setNewsID(newsID);
        this.setPublishTime(publishTime);
        this.setImage(image);
        this.setCategory(category);
        this.setVideo(video);
        this.setTitle(title);
        this.setUrl(url);
        this.setContent(content);
        this.setLanguage(language);
        this.setSaved(saved);
        this.setViewed(viewed);
        this.setKeywords(keywords);
    }

    @Ignore
    public News(){}
}
