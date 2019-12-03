package com.example.movieapp.ui.home;

import android.os.AsyncTask;
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
import java.util.ArrayList;
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
        if(movies == null) {
            new MovieService().execute();
        }
        lstMovies.setValue(movies);
        return lstMovies;
    }

    class MovieService extends AsyncTask<String, Void, ArrayList<Movie>> implements Callback {
        Moshi moshi = new Moshi.Builder().build();
        Type movieType = Types.newParameterizedType(List.class, Movie.class);
        final JsonAdapter<List<Movie>> jsonAdapter = moshi.adapter(movieType);

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://film-vietvite.herokuapp.com/api/movie/trending")
                    .build();

            client.newCall(request).enqueue(this);
            return null;
        }

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}