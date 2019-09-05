package com.java.chenkaiwen.News;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM News")
    public List<News> getAll();

    @Query("SELECT * FROM News WHERE category = :category")
    public List<News> getNewsesByCategory(String category);

    @Query("SELECT * FROM News WHERE newsID = :newsID")
    public News getNewsByNewsID(String newsID);

    @Query("SELECT * FROM News WHERE newsID IN (:newsIDs)")
    public List<News> getAllNewsByNewsIDs(String[] newsIDs);

    @Query("SELECT * FROM News WHERE saved = :saved")
    public List<News> getAllNewsByFavorites(boolean saved);

    @Query("SELECT * FROM News WHERE viewed = :viewed")
    public List<News> getAllNewsByHistory(boolean viewed);

    @Insert
    public void insertNews(News news);

    @Insert
    public void insertAllNewses(List<News> newses);

    @Update
    public void updateNews(News news);

    @Update
    public void updateAllNewses(List<News> newses);

    @Delete
    public void deleteNews(News news);

    @Delete
    public void deleteAllNews(List<News> newses);
}
