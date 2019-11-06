package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.movieapp.model.Slider;
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

        sliderpager = findViewById(R.id.slider_pager);

        lstSlides = new ArrayList<Slider>();
        lstSlides.add(new Slider(R.drawable.avengers, "Avengers..."));
        lstSlides.add(new Slider(R.drawable.hobbit, "Hobbit..."));
        lstSlides.add(new Slider(R.drawable.inception, "Inception..."));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, lstSlides);
        sliderpager.setAdapter(adapter);
    }
}
