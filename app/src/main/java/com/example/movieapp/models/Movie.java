package com.example.movieapp.models;


import java.util.List;

public class Movie {
    private String _id;
    private String title;
    private String description;
    private String thumbnail;
    private float rate;
    private long vote;
    private int duration;
    private String quality;
    private String releaseDate;
    private long viewNumber;
    private String movieUrl;
    private List<Comment> comments;
    private String coverImg;

    public Movie(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Movie(String _id, String title, String description, String thumbnail, float rate, long vote, int duration, String quality, String releaseDate, long viewNumber, String movieUrl, String coverImg) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.rate = rate;
        this.vote = vote;
        this.duration = duration;
        this.quality = quality;
        this.releaseDate = releaseDate;
        this.viewNumber = viewNumber;
        this.movieUrl = movieUrl;
        this.coverImg = coverImg;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getVote() {
        return vote;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
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
