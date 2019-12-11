package com.example.movieapp.adapters;

import android.widget.ImageView;

import com.example.movieapp.models.Movie;

public interface FavoriteClickListener {
    void onFavoriteClick(Movie movie, ImageView movieThumbnail);
}
