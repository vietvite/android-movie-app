package com.example.movieapp.models;

import java.util.List;

public class User {
    String _id;
    String email;
    String password;
    List<Movie> favorite;

    public User(String _id, String email, List<Movie> favorite) {
        this._id = _id;
        this.email = email;
        this.favorite = favorite;
    }

    public User(String _id, String email, String password, List<Movie> favorite) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.favorite = favorite;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Movie> favorite) {
        this.favorite = favorite;
    }
}
