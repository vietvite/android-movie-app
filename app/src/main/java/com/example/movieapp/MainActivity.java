package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.Slider;
import com.example.movieapp.ui.MovieAdapter;
import com.example.movieapp.ui.SliderPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Slider> lstSlides;
    private ViewPager sliderpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ;
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

        MovieAdapter trendingAdapter = new MovieAdapter(this, lstTrending);
        trendingRv.setAdapter(trendingAdapter);
        trendingRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        List<Movie> lstPhimLe = new ArrayList<>();
        lstPhimLe.add(new Movie("Dora và thành phố vàng mất tích", R.drawable.doravathanhphovangmattich));
        lstPhimLe.add(new Movie("Kẻ du hành trên mây", R.drawable.keduhanhtrenmay));
        lstPhimLe.add(new Movie("Lắng nghe giai điệu tình yêu", R.drawable.langnghegiaidieutinhyeu));
        lstPhimLe.add(new Movie("Đô vật chim ưng bơ đậu phộng", R.drawable.dovatchimungbodauphong));
        lstPhimLe.add(new Movie("Kamen Rider Movie War", R.drawable.kamenridermoviewar));

        MovieAdapter phimleAdapter = new MovieAdapter(this, lstPhimLe);
        phimLeRv.setAdapter(phimleAdapter);
        phimLeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }
}
