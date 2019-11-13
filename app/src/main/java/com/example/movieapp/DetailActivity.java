package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

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

        ivMovieImg = findViewById(R.id.iv_detail_thumb);
        tvMovieTitle = findViewById(R.id.tv_detail_title);

        int movieThumbResId = getIntent().getExtras().getInt("movieThumb");
        String movieTitle = getIntent().getExtras().getString("title");

        Toast.makeText(this, "Title: " + movieTitle, Toast.LENGTH_SHORT).show();

        ivMovieImg.setImageResource(movieThumbResId);
        tvMovieTitle.setText(movieTitle);
    }
}
