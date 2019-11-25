package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Slider;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.adapters.MovieItemClickListener;
import com.example.movieapp.adapters.SliderPagerAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private List<Slider> lstSlides;
    private ViewPager sliderpager;
    private RecyclerView trendingRv;
    private RecyclerView list1Rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        app logo & title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_movie_filter_black_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setTitle("RiceFilm");

        initView();
        initSlider();
        initMovies();
    }

    private void initMovies() {
        OkHttpClient client = new OkHttpClient();

        Moshi moshi = new Moshi.Builder().build();
        Type movieType = Types.newParameterizedType(List.class, Movie.class);
        final JsonAdapter<List<Movie>> jsonAdapter = moshi.adapter(movieType);


        Request request = new Request.Builder()
                .url("https://film-vietvite.herokuapp.com/api/movie/trending")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resData = response.body().string();
                    JSONObject jsonObject = new JSONObject(resData);
                    String lstMovieStr = jsonObject.getString("data");
                    List<Movie> lstMovies = jsonAdapter.fromJson(lstMovieStr);
                    Log.e("lstMovies", lstMovieStr);

                    MainActivity.this.runOnUiThread(() -> {
                        updateTrendingMovies(lstMovies);
                        updateActionMovies(lstMovies);
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateTrendingMovies(List<Movie> lstMovies) {
        TextView tvTrendingTitle = findViewById(R.id.tv_trending_title);
        tvTrendingTitle.setText("Trending now");
        MovieAdapter trendingAdapter = new MovieAdapter(MainActivity.this, lstMovies, MainActivity.this);
        trendingRv.setAdapter(trendingAdapter);
        trendingRv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void updateActionMovies(List<Movie> lstMovies) {
        TextView tvTrendingTitle = findViewById(R.id.tv_list1_title);
        tvTrendingTitle.setText("Hành động");
        MovieAdapter trendingAdapter = new MovieAdapter(MainActivity.this, lstMovies, MainActivity.this);
        list1Rv.setAdapter(trendingAdapter);
        list1Rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initSlider() {
        lstSlides = new ArrayList<Slider>();
        lstSlides.add(new Slider(R.drawable.avengers, "Avengers..."));
        lstSlides.add(new Slider(R.drawable.hobbit, "Hobbit..."));
        lstSlides.add(new Slider(R.drawable.inception, "Inception..."));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, lstSlides);
        sliderpager.setAdapter(adapter);
    }

    private void initView() {
        sliderpager = findViewById(R.id.slider_pager);
        trendingRv = findViewById(R.id.Rv_trending);
        list1Rv = findViewById(R.id.Rv_phimle);

    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieThumbnail) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("movieThumb", movie.getThumbnail());
        intent.putExtra("desc", movie.getDescription());
        intent.putExtra("linkMovie", movie.getMovieUrl());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this, movieThumbnail, "movieThumb");

        startActivity(intent, options.toBundle());
    }
}
