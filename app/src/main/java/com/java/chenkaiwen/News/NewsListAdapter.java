package com.java.chenkaiwen.News;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.java.chenkaiwen.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import com.bumptech.glide.Glide;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private Context mContext;
    private SharedPreferences sharedPrefs;
    private List<News> mNews = Collections.emptyList();
    public NewsListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_item, parent, false);
        return new ViewHolder(v);
    }

//    @Override
//    public int getItemCount() {
//        return mNews.size();
//    }
    public void setNews(List<News> news) {
        mNews = news;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mNews != null)
            return mNews.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView articleTextView;
        private TextView authorTextView;
        private TextView dateTextView;
        private ImageView thumbnailImageView;
        private Button shareButton;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.card_title);
            articleTextView = itemView.findViewById(R.id.card_article);
            authorTextView = itemView.findViewById(R.id.card_author);
            dateTextView = itemView.findViewById(R.id.card_date);
            thumbnailImageView = itemView.findViewById(R.id.card_image);
            shareButton = itemView.findViewById(R.id.card_share);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);

//        setColorTheme(holder);
//
//        setTextSize(holder);

        final News currentNews = mNews.get(position);
        holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp22));
        holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp14));
        holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp14));

        holder.titleTextView.setText(currentNews.getTitle());
        //holder.articleTextView.setText(currentNews.getContent());
        holder.articleTextView.setVisibility(View.GONE);

        if (currentNews.getPublisher() == null) {
            holder.authorTextView.setVisibility(View.GONE);
        } else {
            holder.authorTextView.setVisibility(View.VISIBLE);
            holder.authorTextView.setText(currentNews.getPublisher());
        }
        if(currentNews.isViewed()) {
            holder.titleTextView.setTextColor(Color.GRAY);
        } else {
            holder.titleTextView.setTextColor(Color.BLACK);
        }
        //holder.dateTextView.setText(getTimeDifference(formatDate(currentNews.getPublishTime())));
        holder.dateTextView.setText(currentNews.getPublishTime());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });

        if (currentNews.getImage() == "") {
            holder.thumbnailImageView.setVisibility(View.GONE);
        } else {
            holder.thumbnailImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext.getApplicationContext())
                    .load(currentNews.getImage())
                    .into(holder.thumbnailImageView);
        }


        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(currentNews);
            }
        });
    }

//    private void setColorTheme(ViewHolder holder) {
//        // Get the color theme string from SharedPreferences and check for the value associated with the key
//        String colorTheme = sharedPrefs.getString(
//                mContext.getString(R.string.settings_color_key),
//                mContext.getString(R.string.settings_color_default));
//
//        // Change the background color of titleTextView by using the user's stored preferences
//        if (colorTheme.equals(mContext.getString(R.string.settings_color_white_value))) {
//            holder.titleTextView.setBackgroundResource(R.color.white);
//            holder.titleTextView.setTextColor(Color.BLACK);
//        }else if (colorTheme.equals(mContext.getString(R.string.settings_color_sky_blue_value))) {
//            holder.titleTextView.setBackgroundResource(R.color.nav_bar_start);
//            holder.titleTextView.setTextColor(Color.WHITE);
//        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_dark_blue_value))) {
//            holder.titleTextView.setBackgroundResource(R.color.color_app_bar_text);
//            holder.titleTextView.setTextColor(Color.WHITE);
//        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_violet_value))) {
//            holder.titleTextView.setBackgroundResource(R.color.violet);
//            holder.titleTextView.setTextColor(Color.WHITE);
//        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_light_green_value))) {
//            holder.titleTextView.setBackgroundResource(R.color.light_green);
//            holder.titleTextView.setTextColor(Color.WHITE);
//        } else if (colorTheme.equals(mContext.getString(R.string.settings_color_green_value))) {
//            holder.titleTextView.setBackgroundResource(R.color.color_section);
//            holder.titleTextView.setTextColor(Color.WHITE);
//        }
//    }


//    private void setTextSize(ViewHolder holder) {
//        String textSize = sharedPrefs.getString(
//                mContext.getString(R.string.settings_text_size_key),
//                mContext.getString(R.string.settings_text_size_default));
//
//        if(textSize.equals(mContext.getString(R.string.settings_text_size_medium_value))) {
//            holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp22));
//            holder.sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp14));
//            holder.trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp16));
//            holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp14));
//            holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp14));
//        } else if(textSize.equals(mContext.getString(R.string.settings_text_size_small_value))) {
//            holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp20));
//            holder.sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp12));
//            holder.trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp14));
//            holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp12));
//            holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp12));
//        } else if(textSize.equals(mContext.getString(R.string.settings_text_size_large_value))) {
//            holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp24));
//            holder.sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp16));
//            holder.trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp18));
//            holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp16));
//            holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    mContext.getResources().getDimension(R.dimen.sp16));
//        }
//    }

    private void shareData(News news) {
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
//                news.getTitle() + " : " + news.getUrl());
//        mContext.startActivity(Intent.createChooser(sharingIntent,
//                mContext.getString(R.string.share_article)));
    }

//    public void clearAll() {
//        mNewsList.clear();
//        notifyDataSetChanged();
//    }
//
//    public void addAll(List<News> newsList) {
//        mNewsList.clear();
//        mNewsList.addAll(newsList);
//        notifyDataSetChanged();
//    }


    private String formatDate(String dateStringUTC) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateStringUTC);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH);
        String formattedDateUTC = df.format(dateObject);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(formattedDateUTC);
            df.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(date);
    }

    private static long getDateInMillis(String formattedDate) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("MMM d, yyyy  h:mm a");
        long dateInMillis;
        Date dateObject;
        try {
            dateObject = simpleDateFormat.parse(formattedDate);
            dateInMillis = dateObject.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            Log.e("Problem parsing date", e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private CharSequence getTimeDifference(String formattedDate) {
        long currentTime = System.currentTimeMillis();
        long publicationTime = getDateInMillis(formattedDate);
        return DateUtils.getRelativeTimeSpanString(publicationTime, currentTime,
                DateUtils.SECOND_IN_MILLIS);
    }
}
