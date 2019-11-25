package com.example.movieapp.models;

public class Movie {
    private String title;
    private String description;
    private String thumbnail;
    private String rating;
    private String movieUrl;
    private String releaseDate;
    private long viewNumber;

    public Movie(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Movie(String title, String description, String thumbnail, String rating, String movieUrl, String releaseDate, long viewNumber) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.movieUrl = movieUrl;
        this.releaseDate = releaseDate;
        this.viewNumber = viewNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(long viewNumber) {
        this.viewNumber = viewNumber;
    }
}
