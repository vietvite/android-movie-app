package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailActivity extends AppCompatActivity {

    ImageView ivMovieCover;
    ImageView ivMovieImg;
    TextView tvMovieTitle;
    TextView tvMovieDesc;
    FloatingActionButton btnStartPlay;
    TextView tvViews;
    TextView tvRating;
    TextView tvDuration;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().setDuration(200);
            getWindow().getSharedElementReturnTransition().setDuration(200)
                    .setInterpolator(new DecelerateInterpolator());
        }

        initView();
    }

    private void initView() {
        ivMovieCover = findViewById(R.id.iv_detail_cover);
        tvMovieTitle = findViewById(R.id.tv_detail_title);
        ivMovieImg = findViewById(R.id.iv_detail_thumb);
        tvMovieDesc = findViewById(R.id.tv_detail_description);
        btnStartPlay = findViewById(R.id.btn_start_play);
        tvViews = findViewById(R.id.tv_views);
        tvDuration = findViewById(R.id.tv_duration);
        tvRating = findViewById(R.id.tv_rating);
        ratingBar = findViewById(R.id.ratingBar);

        String movieImg = getIntent().getExtras().getString("movieImg");
        String movieTitle = getIntent().getExtras().getString("title");
        String movieDesc = getIntent().getExtras().getString("desc");
        String linkMovie = getIntent().getExtras().getString("linkMovie");
        long viewNumber = getIntent().getExtras().getLong("viewNumber");
        int duration = getIntent().getExtras().getInt("duration");
        float rating = getIntent().getExtras().getFloat("rating");

        tvViews.setText(String.valueOf(viewNumber));
        tvDuration.setText(String.valueOf(duration));
        tvRating.setText(String.valueOf(rating));
        ratingBar.setRating(rating);
        Glide.with(this).load(movieImg).into(ivMovieImg);
        tvMovieTitle.setText(movieTitle);
        tvMovieDesc.setText(movieDesc);
        getSupportActionBar().setTitle(movieTitle);
        Glide.with(this)
                .load(movieImg)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(30, 3)))
                .into(ivMovieCover);

        btnStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(DetailActivity.this, PlayerActivity.class);
                mIntent.putExtra("linkMovie", linkMovie);
                startActivity(mIntent);
            }
        });
    }
}
