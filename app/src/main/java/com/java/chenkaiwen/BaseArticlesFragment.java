package com.java.chenkaiwen;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.java.chenkaiwen.News.News;
import com.java.chenkaiwen.News.NewsDao;
import com.java.chenkaiwen.News.NewsDatabase;
import com.java.chenkaiwen.News.NewsListAdapter;
import com.java.chenkaiwen.News.NewsViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BaseArticlesFragment extends Fragment
{
    private static final String LOG_TAG = BaseArticlesFragment.class.getName();
    protected NewsListAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsViewModel mNewsViewModel;
    private SharedPreferences sharedPrefs;
    private List<News> mNews;

    private int mode = 1;
    private String size = "60";
    private String startDate;
    private String endDate;
    private String words;
    private String categories;

    int getMode()
    {
//        if(getCategories() == null) return 0;
//        return 1;
        return mode;
    }
    String getSize()
    {
        return size;
    }
    String getStartDate()
    {
        String date = sharedPrefs.getString(getContext().getString(R.string.settings_date_key),
                getContext().getString(R.string.settings_date_key));
        return date;
    }
    String getEndDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate);
    }
    String getWords()
    {
        return words;
    }

    String getCategories()
    {
        String category = sharedPrefs.getString(getContext().getString(R.string.settings_filter_key),
                getContext().getString(R.string.settings_all_label));
        if (category.equals(getContext().getString(R.string.settings_all_label))) return null;
        return category;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsDatabase db = NewsDatabase.getDatabase(getActivity().getApplication());

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        //final NewsListAdapter adapter = new NewsListAdapter(getActivity());
        mAdapter = new NewsListAdapter(getActivity(), mNewsViewModel);
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mNewsViewModel.getAllNews(getMode(), getCategories(), getWords()).observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable final List<News> News) {
                mAdapter.setNews(News);
            }
        });
        //updateList();
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh1),
                getResources().getColor(R.color.refresh2),
                getResources().getColor(R.color.refresh3),
                getResources().getColor(R.color.refresh4));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                Runnable runnable_update = () -> {
                    try {
//                        if (getMode() != 3 && getMode() != 2){ mNewsViewModel.deleteAll();}
                        mNewsViewModel.deleteAllUnsaved();
                        Log.d("Refresh", "Start refresh");
                        WebService webService = new WebService();
                        if (getSize() != null) { webService.setSize(getSize()); }
                        if (getStartDate() != null) { webService.setStartDate(getStartDate()); }
                        if (getEndDate() != null) { webService.setEndDate(getEndDate()); }
                        if (getWords() != null) { webService.setWords(getWords()); }
                        if (getCategories() != null) { webService.setCategories(getCategories()); }
                        String response = webService.connect();
                        List<News> list = webService.makeNewsList(response);
                        //List<News> insertList = new ArrayList<>();
                        NewsDatabase newsDatabase = NewsDatabase.getDatabase(getContext());
                        for (int i = 0; i < list.size(); i ++) {
                            //Log.d("refresh", "" + i);
                            if (newsDatabase.newsDao().getNewsByNewsID(list.get(i).newsID) == null) {
                                //Log.d("Refresh", "saved " + list.get(i).newsID);
                                newsDatabase.newsDao().insertNews(list.get(i));
                            }
                        }
//                        mNewsViewModel = new NewsViewModel(getActivity().getApplication());
//                        mNews = mNewsViewModel.getAllNews(getMode());
                        Log.d("Refresh", "done");
                    } catch (Exception e){
                        Log.d("Refresh", e.toString());
                    }
                };
                mSwipeRefreshLayout.setRefreshing(false);
                if (getMode() != 4 && getMode() != 2){
                    new Thread(runnable_update).start();
                    Toast.makeText(getActivity(), "Connection Success",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mLoadingIndicator.setVisibility(View.GONE); //not using for now

        return rootView;
    }

//    public void updateList(){
//        mAdapter.setNews(mNews);
//        mAdapter.changed();
//        return;
//    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
