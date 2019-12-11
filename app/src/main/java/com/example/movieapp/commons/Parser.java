package com.example.movieapp.commons;

import com.example.movieapp.models.Comment;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.User;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Parser {

    public static List<Movie> parseListMovie(String s) {
        Moshi moshi = new Moshi.Builder().build();
        Type movieType = Types.newParameterizedType(List.class, Movie.class);

        final JsonAdapter<List<Movie>> jsonAdapter = moshi.adapter(movieType);
        List<Movie> movies = null;
        try {
            movies = jsonAdapter.fromJson(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static User parseUser(String s) {
        Moshi moshi = new Moshi.Builder().build();
        Type movieType = Types.newParameterizedType(User.class);
        final JsonAdapter<User> jsonAdapter = moshi.adapter(movieType);
        User user = null;
        try {
            user = jsonAdapter.fromJson(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<Comment> parseListComment(String s) {
        Moshi moshi = new Moshi.Builder().build();
        Type commentType = Types.newParameterizedType(List.class, Comment.class);

        final JsonAdapter<List<Comment>> jsonAdapter = moshi.adapter(commentType);
        List<Comment> comments = null;
        try {
            comments = jsonAdapter.fromJson(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
