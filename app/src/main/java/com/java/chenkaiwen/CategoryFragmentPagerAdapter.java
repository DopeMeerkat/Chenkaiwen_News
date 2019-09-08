package com.java.chenkaiwen;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new RecommendFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new FavoritesFragment();
            case 4:
                return new SearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int titleResId;
        switch (position) {
            case 0:
                titleResId = R.string.menu_home;
                break;
            case 1:
                titleResId = R.string.menu_recommend;
                break;
//            case 2:
//                titleResId = R.string.menu_history;
//                break;
            case 3:
                titleResId = R.string.menu_favorites;
                break;
            case 4:
                titleResId = R.string.menu_search;
                break;
            default:
                titleResId = R.string.menu_history;
                break;
        }
        return mContext.getString(titleResId);
    }
}