package com.java.chenkaiwen.News;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


class NewsRepository {

    private NewsDao mNewsDao;
    private LiveData<List<News>> mAllNews;

    NewsRepository(Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.getAll();
    }


    LiveData<List<News>> getAllNews() {
        return mAllNews;
    }
    LiveData<List<News>> getNewsByCategory(String category) {
        if(category == null) return getAllNews();
        mAllNews = mNewsDao.getNewsByCategory(category);
        return mAllNews;
    }
    LiveData<List<News>> getNewsByViewed(boolean viewed) {
        mAllNews = mNewsDao.getNewsByViewed(viewed);
        return mAllNews;
    }
    LiveData<List<News>> getNewsBySaved(boolean saved) {
        mAllNews = mNewsDao.getNewsBySaved(saved);
        return mAllNews;
    }
    LiveData<List<News>> getNewsByKeyword(String keyword) {
        mAllNews = mNewsDao.getNewsByKeyword(keyword);
        return mAllNews;
    }
    LiveData<List<News>> getNewsByNotKeyword(String notWord) {
        mAllNews = mNewsDao.getNewsByNotKeyword(notWord);
        return mAllNews;
    }

    void updateNews(News news) { mNewsDao.updateNews(news);}

    void deleteAll(){
        mNewsDao.deleteAll();
    }

    void deleteAllUnsaved(){
        mNewsDao.deleteAllUnsaved(true);
    }

    void insert(News News) {
        new insertAsyncTask(mNewsDao).execute(News);
    }

    private static class insertAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao mAsyncTaskDao;

        insertAsyncTask(NewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final News... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}