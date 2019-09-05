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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class BaseArticlesFragment extends Fragment
//        implements LoaderManager.LoaderCallbacks<List<NewsObj>>
{

    private static final String LOG_TAG = BaseArticlesFragment.class.getName();

    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh1),
                getResources().getColor(R.color.refresh2),
                getResources().getColor(R.color.refresh3),
                getResources().getColor(R.color.refresh4));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                Toast.makeText(getActivity(), "Updated",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        return rootView;
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

//    @NonNull
//    @Override
//    public Loader<List<NewsObj>> onCreateLoader(int i, Bundle bundle) {
//
//        Uri.Builder uriBuilder = NewsPreferences.getPreferredUri(getContext());
//
//        Log.e(LOG_TAG,uriBuilder.toString());
//
//        // Create a new loader for the given URL
//        return new NewsLoader(getActivity(), uriBuilder.toString());
//    }

}
