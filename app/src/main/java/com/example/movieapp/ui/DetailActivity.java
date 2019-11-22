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

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailActivity extends AppCompatActivity {

    ImageView ivMovieHeader;
    ImageView ivMovieImg;
    TextView tvMovieTitle;

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
        ivMovieImg = findViewById(R.id.iv_detail_thumb);
        tvMovieTitle = findViewById(R.id.tv_detail_title);
        ivMovieHeader = findViewById(R.id.iv_detail_header);

        int movieImgResId = getIntent().getExtras().getInt("movieThumb");
        String movieTitle = getIntent().getExtras().getString("title");

        Glide.with(this).load(movieImgResId).into(ivMovieImg);
        tvMovieTitle.setText(movieTitle);
        getSupportActionBar().setTitle(movieTitle);
        Glide.with(this)
                .load(movieImgResId)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(30, 3)))
                .into(ivMovieHeader);
    }
}
