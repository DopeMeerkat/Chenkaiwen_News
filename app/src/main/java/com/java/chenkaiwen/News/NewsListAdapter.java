package com.java.chenkaiwen.News;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.R.drawable;
import com.java.chenkaiwen.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import com.bumptech.glide.Glide;
import com.java.chenkaiwen.Recommended;
import com.java.chenkaiwen.ViewNewsActivity;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    public static final String EXTRA_MESSAGE = "com.java.chenkaiwen.MESSAGE";
    private Context mContext;
    private SharedPreferences sharedPrefs;
    private List<News> mNews = Collections.emptyList();
    private NewsViewModel mNewsViewModel;

    public NewsListAdapter(Context context, NewsViewModel newsViewModel) {
        mContext = context;
        mNewsViewModel = newsViewModel;
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_item, parent, false);
        return new ViewHolder(v);
    }

    public void setNews(List<News> news) {
        mNews = news;
        notifyDataSetChanged();
    }
    public void changed() {
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
        private VideoView videoView;
        private Button shareButton;
        private Button favoriteButton;
        private RelativeLayout bodyLayout;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.card_title);
            articleTextView = itemView.findViewById(R.id.card_article);
            authorTextView = itemView.findViewById(R.id.card_author);
            dateTextView = itemView.findViewById(R.id.card_date);
            thumbnailImageView = itemView.findViewById(R.id.card_image);
            videoView = itemView.findViewById(R.id.card_video);
            shareButton = itemView.findViewById(R.id.card_share);
            favoriteButton = itemView.findViewById(R.id.card_favorite);
            bodyLayout = itemView.findViewById(R.id.card_body_layout);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        final News currentNews = mNews.get(position);
        setColorTheme(holder, currentNews);
        holder.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp22));
        holder.authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp14));
        holder.articleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp16));
        holder.dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.sp14));

        holder.titleTextView.setText(currentNews.getTitle());
        holder.articleTextView.setText(currentNews.getTrailText());
        //holder.articleTextView.setVisibility(View.VISIBLE);
//        Log.d("NewsList", "Author = " + currentNews.getPublisher());
        holder.authorTextView.setVisibility(View.VISIBLE);
        holder.authorTextView.setText(currentNews.getPublisher());
        //holder.dateTextView.setText(getTimeDifference(formatDate(currentNews.getPublishTime())));
        holder.dateTextView.setText(currentNews.getPublishTime());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable_update = () -> {
                    try {
                        News temp = mNews.get(position);
                        temp.setViewed(true);
                        mNewsViewModel.update(temp);
                    } catch (Exception e){
                        Log.d("Card Click", e.toString());
                    }
                };
                Thread t = new Thread(runnable_update);
                t.start();
                notifyDataSetChanged();
                Recommended.getInstance().insertKeys(currentNews.getKeywords());

                Intent viewNewsIntent = new Intent(mContext, ViewNewsActivity.class);
                String[] arr = new String[7];
                arr[0] = currentNews.getTitle();
                arr[1] = currentNews.getContent();
                arr[2] = currentNews.getPublisher();
                arr[3] = currentNews.getPublishTime();
                arr[4] = currentNews.getImage();
                arr[5] = currentNews.getVideo();
                arr[6] = currentNews.getUrl();
                viewNewsIntent.putExtra(EXTRA_MESSAGE, arr);
                mContext.startActivity(viewNewsIntent);
            }
        });
        if (!currentNews.getVideo().equals("") && currentNews.getImage().equals("")) {
            holder.videoView.setVisibility(View.VISIBLE);
        } else {
            holder.videoView.setVisibility(View.GONE);
            holder.videoView.setVideoPath(currentNews.getVideo());
            holder.videoView.start();
        }
        if (!currentNews.getImage().equals("")) {
            //Log.d("Adapter", currentNews.getNewsID() + " has image");
            holder.thumbnailImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext.getApplicationContext())
                    .load(currentNews.getImage())
                    .override((int)mContext.getResources().getDimension(R.dimen.thumbnail_image_width), (int)mContext.getResources().getDimension(R.dimen.thumbnail_image_width))
                    .into(holder.thumbnailImageView);
        } else {
            holder.thumbnailImageView.setVisibility(View.GONE);
        }



        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(currentNews);
            }
        });
        if(currentNews.isSaved()) holder.favoriteButton.setBackgroundResource(drawable.btn_star_big_on);
        else holder.favoriteButton.setBackgroundResource(drawable.btn_star_big_off);
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable_update = () -> {
                    try {
                        News temp = mNews.get(position);
                        if(temp.isSaved()) temp.setSaved(false);
                        else temp.setSaved(true);
                        mNewsViewModel.update(temp);
                    } catch (Exception e){
                        Log.d("Favorite Click", e.toString());
                    }
                };
                Thread t = new Thread(runnable_update);
                t.start();
                notifyDataSetChanged();
            }
        });
    }

    private void setColorTheme(ViewHolder holder, News currentNews) {
        if (sharedPrefs.getBoolean(mContext.getString(R.string.settings_dark_key), false)) {
            holder.cardView.setCardBackgroundColor(Color.DKGRAY);
            holder.articleTextView.setTextColor(Color.LTGRAY);
            holder.authorTextView.setTextColor(Color.LTGRAY);
            holder.dateTextView.setTextColor(Color.GRAY);
            holder.bodyLayout.setBackgroundResource(R.color.colorPrimary);
            if(currentNews.isViewed()) {
                holder.titleTextView.setTextColor(Color.GRAY);
            } else {
                holder.titleTextView.setTextColor(Color.WHITE);
            }
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.articleTextView.setTextColor(Color.GRAY);
            holder.authorTextView.setTextColor(Color.GRAY);
            holder.dateTextView.setTextColor(Color.DKGRAY);
            holder.bodyLayout.setBackgroundResource(R.color.colorPrimaryLight);
            if(currentNews.isViewed()) {
                holder.titleTextView.setTextColor(Color.GRAY);
            } else {
                holder.titleTextView.setTextColor(Color.BLACK);
            }
        }
    }


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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, news.getUrl());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        mContext.startActivity(shareIntent);
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
