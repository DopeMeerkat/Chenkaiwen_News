package com.java.chenkaiwen;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SearchFragment extends BaseArticlesFragment {

    public static final String LOG_TAG = SearchFragment.class.getName();

    @Override
    String getWords() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        return sharedPrefs.getString(getString(R.string.settings_search_key), " ");
    }

    @Override
    int getMode() {
        return 3;
    }
}
