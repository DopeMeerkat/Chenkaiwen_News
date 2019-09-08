package com.java.chenkaiwen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.content.Intent;
import com.java.chenkaiwen.News.NewsListAdapter;

import com.bumptech.glide.Glide;
import com.java.chenkaiwen.R;

public class ViewNewsActivity extends AppCompatActivity {

    private TextView newsTitleTextView;
    private TextView newsArticleTextView;
    private TextView newsAuthorTextView;
    private TextView newsDateTextView;
    private ImageView newsImageView;
    private VideoView newsVideoView;
    private Button newsOpenButton;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        newsTitleTextView = findViewById(R.id.news_title);
        newsArticleTextView = findViewById(R.id.news_article);
        newsAuthorTextView = findViewById(R.id.news_author);
        newsDateTextView = findViewById(R.id.news_date);
        newsImageView = findViewById(R.id.news_image);
        newsVideoView = findViewById(R.id.news_video);
        newsOpenButton = findViewById(R.id.news_open);
        newsTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.sp32));
        newsAuthorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.sp14));
        newsArticleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.sp16));
        newsDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.sp14));
        Intent intent = getIntent();
        String[] arr = intent.getStringArrayExtra(NewsListAdapter.EXTRA_MESSAGE);

        newsTitleTextView.setText(arr[0]);
        newsArticleTextView.setText(arr[1]);
        newsAuthorTextView.setText(arr[2]);
        newsDateTextView.setText(arr[3]);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (arr[4].equals("") && !arr[5].equals("")) {
            newsVideoView.setVisibility(View.VISIBLE);
        } else {
            newsVideoView.setVisibility(View.GONE);
            //TODO
        }
        if (arr[4].equals("")) {
            newsImageView.setVisibility(View.GONE);
        } else {
            newsImageView.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(arr[4])
//                    .override((int)getResources().getDimension(R.dimen.thumbnail_image_width),
//                            (int)getResources().getDimension(R.dimen.thumbnail_image_width))
                    .into(newsImageView);
        }
        newsTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.sp32));
        newsTitleTextView.setTextColor(Color.BLACK);
        newsArticleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.sp22));

        url = arr[6];
        newsOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri newsUri = Uri.parse(url);
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
    }

}
