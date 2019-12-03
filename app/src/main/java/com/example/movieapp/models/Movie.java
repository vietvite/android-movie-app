package com.example.movieapp.models;

public class Movie {
    private String title;
    private String description;
    private String thumbnail;
    private float rating;
    private int duration;
    private String releaseDate;
    private long viewNumber;
    private String movieUrl;

    public Movie(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Movie(String title, String description, String thumbnail, float rating, int duration, String releaseDate, long viewNumber, String movieUrl) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.viewNumber = viewNumber;
        this.movieUrl = movieUrl;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }
}
