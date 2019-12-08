package com.example.movieapp.models;

import java.util.List;

public class Trending {
    List<Movie> trending;

    public Trending(List<Movie> trending) {
        this.trending = trending;
    }

    public List<Movie> getTrending() {
        return trending;
    }

    public void setTrending(List<Movie> trending) {
        this.trending = trending;
    }
}
