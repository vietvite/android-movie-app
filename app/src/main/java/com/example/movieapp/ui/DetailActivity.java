package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    ImageButton ibFav;
    TextView tvComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().setDuration(200);
            getWindow().getSharedElementReturnTransition().setDuration(200)
                    .setInterpolator(new DecelerateInterpolator());
        }

        getSupportActionBar().hide();

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
        ibFav = findViewById(R.id.btn_fav_border);
        tvComment = findViewById(R.id.btn_comment);

        String movieId = getIntent().getExtras().getString("movieId");
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

        tvComment.setOnClickListener(v -> {
            Intent mIntent = new Intent(DetailActivity.this, CommentActivity.class);
//            mIntent.putExtra("linkMovie", linkMovie);
            startActivity(mIntent);
        });

        ((ImageButton) ibFav).setBackgroundResource(R.drawable.ic_favorite_border_pink_24dp);

        View.OnClickListener favClickHander = v -> {
            ((ImageButton) ibFav).setBackgroundResource(R.drawable.ic_favorite_solid_pink_24dp);
            new UpdateFavorite().execute(movieId);
            Toast.makeText(DetailActivity.this, "Added to you favorite", Toast.LENGTH_SHORT).show();
        };

        ibFav.setOnClickListener(favClickHander);
    }

    public class UpdateFavorite extends AsyncTask<String, Void, Void> implements Callback {

        @Override
        protected Void doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();

//            TODO: Implement SharedPreferences to get userId
            urlBuilder.addPathSegment("add");
            urlBuilder.addPathSegment("5def5b80c2bd5c8b261e9e8e"); // userId
            urlBuilder.addPathSegment(strings[0]); // movieId
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(this);
            return null;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Error", "Network Error");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String resData = response.body().string();
            Log.i("asd", resData);
        }
    }
}
