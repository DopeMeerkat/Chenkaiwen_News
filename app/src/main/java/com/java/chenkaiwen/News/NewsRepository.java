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