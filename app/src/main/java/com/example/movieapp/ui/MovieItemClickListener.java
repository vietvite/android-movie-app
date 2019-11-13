package com.example.movieapp.ui;

import android.widget.ImageView;

import com.example.movieapp.models.Movie;

public interface MovieItemClickListener {
    void onMovieClick(Movie movie, ImageView movieThumbnail);
}
