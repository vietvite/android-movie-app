package com.example.movieapp.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.Movie;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    static List<Movie> movies = null;
    public MutableLiveData<List<Movie>> lstMovies;

    public HomeViewModel() {
        lstMovies = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getMovies() {
        if(movies == null)
            getMovieFromApi();

        lstMovies.setValue(movies);
        return lstMovies;
    }

    public void getMovieFromApi() {
        OkHttpClient client = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        Type movieType = Types.newParameterizedType(List.class, Movie.class);
        final JsonAdapter<List<Movie>> jsonAdapter = moshi.adapter(movieType);

        Request request = new Request.Builder()
                .url("https://film-vietvite.herokuapp.com/api/movie/trending")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resData = response.body().string();
                    JSONObject jsonObject = new JSONObject(resData);
                    String lstMovieStr = jsonObject.getString("data");
                    movies = jsonAdapter.fromJson(lstMovieStr);
                    Log.e("lstMovies", lstMovieStr);

//                    HomeFragment asd = new HomeFragment();
//                    asd.getActivity().runOnUiThread(() -> {
//                        asd.updateTrendingMovies(movies);
//                        asd.updateActionMovies(movies);
//                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}