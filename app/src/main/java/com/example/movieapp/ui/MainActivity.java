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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {


    private List<Slider> lstSlides;
    private ViewPager sliderpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        app logo & title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_movie_filter_black_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setTitle("Ricemovie");



        sliderpager = findViewById(R.id.slider_pager);
        RecyclerView trendingRv = findViewById(R.id.Rv_trending);
        RecyclerView phimLeRv = findViewById(R.id.Rv_phimle);


        lstSlides = new ArrayList<Slider>();
        lstSlides.add(new Slider(R.drawable.avengers, "Avengers..."));
        lstSlides.add(new Slider(R.drawable.hobbit, "Hobbit..."));
        lstSlides.add(new Slider(R.drawable.inception, "Inception..."));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, lstSlides);
        sliderpager.setAdapter(adapter);


//        Movie setup
        List<Movie> lstTrending = new ArrayList<>();
        lstTrending.add(new Movie("Fast and Furious", R.drawable.fastandfurious));
        lstTrending.add(new Movie("Joker", R.drawable.joker));
        lstTrending.add(new Movie("Lời nguyên trên biển", R.drawable.loinguyentrenbien));
        lstTrending.add(new Movie("Kẻ hủy diệt 6", R.drawable.kehuydiet6));
        lstTrending.add(new Movie("Vua sư tử", R.drawable.vuasutu));

        MovieAdapter trendingAdapter = new MovieAdapter(this, lstTrending, this);
        trendingRv.setAdapter(trendingAdapter);
        trendingRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        List<Movie> lstPhimLe = new ArrayList<>();
        lstPhimLe.add(new Movie("Dora và thành phố vàng mất tích", R.drawable.doravathanhphovangmattich));
        lstPhimLe.add(new Movie("Kẻ du hành trên mây", R.drawable.keduhanhtrenmay));
        lstPhimLe.add(new Movie("Lắng nghe giai điệu tình yêu", R.drawable.langnghegiaidieutinhyeu));
        lstPhimLe.add(new Movie("Đô vật chim ưng bơ đậu phộng", R.drawable.dovatchimungbodauphong));
        lstPhimLe.add(new Movie("Kamen Rider Movie War", R.drawable.kamenridermoviewar));

        MovieAdapter phimleAdapter = new MovieAdapter(this, lstPhimLe, this);
        phimLeRv.setAdapter(phimleAdapter);
        phimLeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
