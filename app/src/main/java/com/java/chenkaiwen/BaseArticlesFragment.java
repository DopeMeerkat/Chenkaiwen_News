package com.java.chenkaiwen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.java.chenkaiwen.News.News;
import com.java.chenkaiwen.News.NewsDatabase;
import com.java.chenkaiwen.News.NewsListAdapter;
import com.java.chenkaiwen.News.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class BaseArticlesFragment extends Fragment
{

    private static final String LOG_TAG = BaseArticlesFragment.class.getName();
    private NewsListAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsViewModel mNewsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        //final NewsListAdapter adapter = new NewsListAdapter(getActivity());
        mAdapter = new NewsListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        mNewsViewModel.getAllNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable final List<News> News) {
                mAdapter.setNews(News);
            }
        });

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
                        Log.d("Refresh", "Start refresh");
                        WebService webService = new WebService();
                        webService.setSize("60");
                        //webService.setWords("科技");
                        String response = webService.connect();
                        List<News> list = webService.makeNewsList(response);
                        List<News> insertList = new ArrayList<>();
                        NewsDatabase newsDatabase = NewsDatabase.getDatabase(getContext());
                        for (int i = 0; i < list.size(); i ++)
                        {
                            //Log.d("refresh", "" + i);
                            if(newsDatabase.newsDao().getNewsByNewsID(list.get(i).newsID) == null) {
                                Log.d("Refresh", "save " + list.get(i).newsID);
                                newsDatabase.newsDao().insertNews(list.get(i));
                            }
                        }
                        //adapter.setNews()
                    } catch (Exception e){
                        Log.d("Refresh", e.toString());
                    }
                };
                mSwipeRefreshLayout.setRefreshing(false);
                new Thread(runnable_update).start();
                Toast.makeText(getActivity(), "Updated",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mLoadingIndicator.setVisibility(View.GONE); //not using for now

        return rootView;
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
