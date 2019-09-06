package com.java.chenkaiwen.News;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM News")
    LiveData<List<News>> getAll();

    @Query("SELECT * FROM News WHERE newsID = :newsID")
    public News getNewsByNewsID(String newsID);

    @Insert //(onConflict = OnConflictStrategy.IGNORE)
    void insert(News news);

    @Insert
    public void insertNews(News news);

    @Query("DELETE FROM News")
    void deleteAll();
}