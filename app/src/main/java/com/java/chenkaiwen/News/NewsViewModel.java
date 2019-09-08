package com.java.chenkaiwen.News;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.java.chenkaiwen.R;

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
    public LiveData<List<News>> getAllNews(int mode, String category, String word) {
        switch(mode)
        {
            case 1:{ //category:
                mAllNews = mRepository.getNewsByCategory(category);

                break;
            }
            case 2:{ //history
                mAllNews = mRepository.getNewsByViewed(true);
                break;
            }
            case 4:{ //favorites
                mAllNews = mRepository.getNewsBySaved(true);
                break;
            }
            case 5: { //search
                String keyword = "%" + word + "%";
                mAllNews = mRepository.getAllNews();
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getBaseContext());
                String notWord = sharedPrefs.getString(getApplication().getBaseContext().getString(R.string.settings_omit_key), " ");
                Log.d("ViewModel", notWord);
                if(!notWord.equals(" ")) {
                    notWord = "%" + notWord + "%";
                    mAllNews = mRepository.getNewsByNotKeyword(notWord);
                }
                break;
            }
            default: //home
                mAllNews = mRepository.getAllNews();
        }
        return mAllNews;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
    public void deleteAllUnsaved() {mRepository.deleteAllUnsaved();}
    public void update(News news) { mRepository.updateNews(news);}
    public void insert(News news) {
        mRepository.insert(news);
    }
}