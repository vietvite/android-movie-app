package com.example.movieapp.ui.favorite;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import static android.content.Context.MODE_PRIVATE;

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

        new MovieService().execute();

        return root;
    }

    class MovieService extends AsyncTask<String, Void, List<Movie>> implements Callback {
        List<Movie> movies;

        @Override
        protected List<Movie> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            SharedPreferences sharedPrefs = getContext().getSharedPreferences("user", MODE_PRIVATE);
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();
            urlBuilder.addPathSegment(sharedPrefs.getString("userId","123"));
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
        favAdapter.notifyDataSetChanged();
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

    class RemoveFavorite extends AsyncTask<String, Void, Void> implements Callback {
        List<Movie> movies;

        @Override
        protected Void doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            SharedPreferences sharedPrefs = getContext().getSharedPreferences("user", MODE_PRIVATE);
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();
            urlBuilder.addPathSegment("remove");
            urlBuilder.addPathSegment(sharedPrefs.getString("userId","123"));
            urlBuilder.addPathSegment(strings[0]);
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

        }
    }

    @Override
    public boolean onMovileLongClick(Movie movie) {
        new RemoveFavorite().execute(movie.get_id());
        Toast.makeText(getContext(),"Remove Successfully!",Toast.LENGTH_LONG).show();
        // Reload current fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
        return true;
    }




}