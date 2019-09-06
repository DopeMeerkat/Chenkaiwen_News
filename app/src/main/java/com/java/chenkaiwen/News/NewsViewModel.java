package com.java.chenkaiwen.News;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;

    private LiveData<List<News>> mAllNews;

    public NewsViewModel(Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getAllNews();
    }
    //娱乐 军事 教育 文化 健康 财经 体育 汽车 科技 社会
    public LiveData<List<News>> getAllNews(int mode) {
        switch(mode)
        {
            case 1:{ //category:
                mAllNews = mRepository.getNewsByCategory("娱乐");
                break;
            }
            case 2:{
                mAllNews = mRepository.getNewsByCategory("军事");
                break;
            }
            case 3:{
                mAllNews = mRepository.getNewsByCategory("教育");
                break;
            }
            case 4:{ //category:
                mAllNews = mRepository.getNewsByCategory("文化");
                break;
            }
            case 5:{
                mAllNews = mRepository.getNewsByCategory("健康");
                break;
            }
            case 6:{
                mAllNews = mRepository.getNewsByCategory("财经");
                break;
            }
            case 7:{
                mAllNews = mRepository.getNewsByCategory("体育");
                break;
            }
            case 8:{ //category:
                mAllNews = mRepository.getNewsByCategory("汽车");
                break;
            }
            case 9:{
                mAllNews = mRepository.getNewsByCategory("科技");
                break;
            }
            case 10:{
                mAllNews = mRepository.getNewsByCategory("社会");
                break;
            }
            case 11:{ //history
                mAllNews = mRepository.getNewsByViewed(true);
                break;
            }

            default: //home
                mAllNews = mRepository.getAllNews();
        }
        return mAllNews;
    }

    public void insert(News news) {
        mRepository.insert(news);
    }
}