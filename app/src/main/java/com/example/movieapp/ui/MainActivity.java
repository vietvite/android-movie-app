package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.movieapp.R;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Slider;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.adapters.MovieItemClickListener;
import com.example.movieapp.adapters.SliderPagerAdapter;
import com.example.movieapp.services.MovieService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {


    private List<Slider> lstSlides;
    private ViewPager sliderpager;
    private RecyclerView trendingRv;
    private RecyclerView phimLeRv;

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
        MovieAdapter trendingAdapter = new MovieAdapter(this, MovieService.getTrendingFilms(), this);
        trendingRv.setAdapter(trendingAdapter);
        trendingRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        MovieAdapter phimleAdapter = new MovieAdapter(this, MovieService.getActionFilms(), this);
        phimLeRv.setAdapter(phimleAdapter);
        phimLeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
        phimLeRv = findViewById(R.id.Rv_phimle);
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieThumbnail) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("movieThumb", movie.getThumbnail());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this, movieThumbnail, "movieThumb");

        startActivity(intent, options.toBundle());
    }
}
