package com.example.movieapp.models;

public class Comment {
    private String _id;
    private String date;
    private String comment;

    public Comment(String _id, String date, String comment) {
        this._id = _id;
        this.date = date;
        this.comment = comment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
