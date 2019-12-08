package com.example.movieapp.ui.favorite;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FavoriteAdapter;
import com.example.movieapp.adapters.MovieItemClickListener;
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

public class FavoriteFragment extends Fragment implements MovieItemClickListener {

    private FavoriteViewModel favoriteViewModel;

    private RecyclerView rvFav;
    private FavoriteAdapter favAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFav = root.findViewById(R.id.rv_favs);

        favoriteViewModel.getListFavorites().observe(this, lstFavs -> {
            if(lstFavs != null) {
                Log.e("VO THANG INIT LUON NE", "HIHI");
                initFav(lstFavs);
            } else {
                Log.e("VO MOVIESERVICE", "NOT VO INIT");
                new MovieService().execute();
            }
        });

        return root;
    }

    class MovieService extends AsyncTask<String, Void, List<Movie>> implements Callback {
        Moshi moshi = new Moshi.Builder().build();
        Type movieType = Types.newParameterizedType(List.class, Movie.class);
        final JsonAdapter<List<Movie>> jsonAdapter = moshi.adapter(movieType);

        List<Movie> movies;

        @Override
        protected List<Movie> doInBackground(String... strings) {
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

                Log.e("lstMovieStr", lstMovieStr);
                getActivity().runOnUiThread(() -> {
                    initFav(movies);
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFav(List<Movie> lstFavorite) {
        Log.e("++++++ TITLE +++++", lstFavorite.get(0).getTitle());
        favAdapter = new FavoriteAdapter(lstFavorite);
        rvFav.setAdapter(favAdapter);
//        rvFav.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieThumbnail) {

    }
}