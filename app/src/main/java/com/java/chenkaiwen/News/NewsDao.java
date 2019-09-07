package com.java.chenkaiwen.News;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM News")
    LiveData<List<News>> getAll();

    @Query("SELECT * FROM News WHERE category = :category")
    LiveData<List<News>> getNewsByCategory(String category);

//    @Query("SELECT * FROM News WHERE keywords ") //where keywords contain keywords
//    LiveData<List<News>> getNewsByKeywords(String keyword);

    @Query("SELECT * FROM News WHERE viewed = :viewed")
    LiveData<List<News>> getNewsByViewed(boolean viewed);

    @Query("SELECT * FROM News WHERE viewed = :saved")
    LiveData<List<News>> getNewsBySaved(boolean saved);

    @Query("SELECT * FROM News WHERE newsID = :newsID")
    public News getNewsByNewsID(String newsID);

    @Insert //(onConflict = OnConflictStrategy.IGNORE)
    void insert(News news);

    @Insert
    public void insertNews(News news);

    @Update
    public void updateNews(News news);

    @Query("DELETE FROM News")
    void deleteAll();
}