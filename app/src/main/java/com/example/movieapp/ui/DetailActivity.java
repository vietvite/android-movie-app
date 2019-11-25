package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
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

        String movieImg = getIntent().getExtras().getString("movieThumb");
        String movieTitle = getIntent().getExtras().getString("title");
        String movieDesc = getIntent().getExtras().getString("desc");

        Glide.with(this).load(movieImg).into(ivMovieImg);
        tvMovieTitle.setText(movieTitle);
        tvMovieDesc.setText(movieDesc);
        getSupportActionBar().setTitle(movieTitle);
        Glide.with(this)
                .load(movieImg)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(30, 3)))
                .into(ivMovieCover);
    }
}
