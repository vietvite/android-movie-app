package com.example.movieapp.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.Movie;

import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Movie>> lstFav;


    public FavoriteViewModel() {
        lstFav = new MutableLiveData<>();
//        lstFav.setValue();
    }

    public LiveData<List<Movie>> getListFavorites() {
        return lstFav;
    }
}