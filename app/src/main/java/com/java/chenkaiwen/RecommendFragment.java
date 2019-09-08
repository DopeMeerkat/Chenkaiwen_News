package com.java.chenkaiwen;

import android.util.Log;

public class RecommendFragment extends BaseArticlesFragment {

    public static final String LOG_TAG = RecommendFragment.class.getName();

    @Override
    String getWords() {
        Log.d("Recommend", Recommended.getInstance().getKey());
        return Recommended.getInstance().getKey();
    }

    @Override
    int getMode() {
        return 3;
    }
}
