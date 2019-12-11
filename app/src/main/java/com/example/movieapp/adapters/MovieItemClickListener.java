package com.example.movieapp.adapters;

import android.widget.ImageView;

import com.example.movieapp.models.Movie;

public interface MovieItemClickListener {
    void onMovieClick(Movie movie, ImageView movieThumbnail);

    boolean onMovileLongClick(Movie movie);
}
