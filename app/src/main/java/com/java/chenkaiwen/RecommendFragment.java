package com.java.chenkaiwen;

public class RecommendFragment extends BaseArticlesFragment {

    public static final String LOG_TAG = RecommendFragment.class.getName();

    @Override
    String getWords() {
        return Recommended.getInstance().getKey();
    }

    @Override
    int getMode() {
        return 3;
    }
}
