package com.example.movieapp.services;

import com.example.movieapp.R;
import com.example.movieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    public static List<Movie> getTrendingFilms() {
        List<Movie> lstTrending = new ArrayList<>();
        lstTrending.add(new Movie("Fast and Furious", R.drawable.fastandfurious));
        lstTrending.add(new Movie("Joker", R.drawable.joker));
        lstTrending.add(new Movie("Lời nguyên trên biển", R.drawable.loinguyentrenbien));
        lstTrending.add(new Movie("Kẻ hủy diệt 6", R.drawable.kehuydiet6));
        lstTrending.add(new Movie("Vua sư tử", R.drawable.vuasutu));

        return lstTrending;
    }

    public static List<Movie> getActionFilms() {
        List<Movie> lstPhimLe = new ArrayList<>();
        lstPhimLe.add(new Movie("Dora và thành phố vàng mất tích", R.drawable.doravathanhphovangmattich));
        lstPhimLe.add(new Movie("Kẻ du hành trên mây", R.drawable.keduhanhtrenmay));
        lstPhimLe.add(new Movie("Lắng nghe giai điệu tình yêu", R.drawable.langnghegiaidieutinhyeu));
        lstPhimLe.add(new Movie("Đô vật chim ưng bơ đậu phộng", R.drawable.dovatchimungbodauphong));
        lstPhimLe.add(new Movie("Kamen Rider Movie War", R.drawable.kamenridermoviewar));

        return lstPhimLe;
    }
}
