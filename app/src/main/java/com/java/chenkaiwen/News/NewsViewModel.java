package com.java.chenkaiwen.News;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;

    private LiveData<List<News>> mAllWords;

    public NewsViewModel(Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mAllWords = mRepository.getAllNews();
    }

    LiveData<List<News>> getAllWords() {
        return mAllWords;
    }

    void insert(News news) {
        mRepository.insert(news);
    }
}