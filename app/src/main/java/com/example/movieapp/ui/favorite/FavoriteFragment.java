package com.example.movieapp.ui.favorite;

import android.app.ActivityOptions;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.FavoriteAdapter;
import com.example.movieapp.adapters.MovieItemClickListener;
import com.example.movieapp.commons.Parser;
import com.example.movieapp.models.Movie;
import com.example.movieapp.ui.DetailActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
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
                initFav(lstFavs);
            } else {
                new MovieService().execute();
            }
        });

        return root;
    }

    class MovieService extends AsyncTask<String, Void, List<Movie>> implements Callback {
        List<Movie> movies;

        @Override
        protected List<Movie> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();
            urlBuilder.addPathSegment("5def5b80c2bd5c8b261e9e8e");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(this);
            return null;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Error", "Network Error");
        }

        @Override
        public void onResponse(Call call, Response response) {
            try {
                String resData = response.body().string();
                JSONObject o = new JSONObject(resData);
                String favStr = o.getString("favorite");
                Log.e("favStr", favStr);
                movies = Parser.parseListMovie(favStr);

                getActivity().runOnUiThread(() -> initFav(movies));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFav(List<Movie> lstFavorite) {
        favAdapter = new FavoriteAdapter(getActivity(),lstFavorite, this);
        rvFav.setAdapter(favAdapter);
        rvFav.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieThumbnail) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("movieImg", movie.getThumbnail());
        intent.putExtra("desc", movie.getDescription());
        intent.putExtra("linkMovie", movie.getMovieUrl());
        intent.putExtra("rating", movie.getRate());
        intent.putExtra("duration", movie.getDuration());
        intent.putExtra("viewNumber", movie.getViewNumber());


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), movieThumbnail, "movieThumb");

        startActivity(intent, options.toBundle());
    }
}