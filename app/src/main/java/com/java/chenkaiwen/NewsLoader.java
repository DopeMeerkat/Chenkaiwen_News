//package com.java.chenkaiwen;
//
//import android.content.Context;
//import androidx.loader.content.AsyncTaskLoader;
//
//import java.util.List;
//
//public class NewsLoader extends AsyncTaskLoader<List<NewsObj>> {
//
//    private static final String LOG_TAG = NewsLoader.class.getName();
//
//    private String mUrl;
//
//    public NewsLoader(Context context, String url) {
//        super(context);
//        mUrl = url;
//    }
//
//    @Override
//    protected void onStartLoading() {
//        forceLoad();
//    }
//
//    @Override
//    public List<NewsObj> loadInBackground() {
//        if (mUrl == null) {
//            return null;
//        }
//
//        // Perform the network request, parse the response, and extract a list of news.
//        List<NewsObj> newsData = QueryUtils.fetchNewsData(mUrl);
//        return newsData;
//    }
//}
