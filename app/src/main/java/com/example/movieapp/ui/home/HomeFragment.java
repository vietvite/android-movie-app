package com.example.movieapp.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.adapters.MovieItemClickListener;
import com.example.movieapp.adapters.SliderPagerAdapter;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Slider;
import com.example.movieapp.ui.DetailActivity;
import com.example.movieapp.ui.MainActivity;
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

public class HomeFragment extends Fragment implements MovieItemClickListener {

    private HomeViewModel homeViewModel;

    private List<Slider> lstSlides;
    private ViewPager sliderpager;
    private RecyclerView trendingRv;
    private RecyclerView list1Rv;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initView(root);
        initSlider();

        homeViewModel.getMovies().observe(this, lstMovies -> {
            if(lstMovies == null) {
                new MovieService().execute();
            } else {
                updateTrendingMovies(lstMovies);
                updateActionMovies(lstMovies);
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

                getActivity().runOnUiThread(() -> {
                    updateTrendingMovies(movies);
                    updateActionMovies(movies);
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTrendingMovies(List<Movie> lstMovies) {
        TextView tvTrendingTitle = getActivity().findViewById(R.id.tv_trending_title);
        tvTrendingTitle.setText("Trending now");
        MovieAdapter trendingAdapter = new MovieAdapter(getActivity(), lstMovies, this);
        trendingRv.setAdapter(trendingAdapter);
        trendingRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void updateActionMovies(List<Movie> lstMovies) {
        TextView tvTrendingTitle = getActivity().findViewById(R.id.tv_list1_title);
        tvTrendingTitle.setText("Hành động");
        MovieAdapter trendingAdapter = new MovieAdapter(getActivity(), lstMovies, this);
        list1Rv.setAdapter(trendingAdapter);
        list1Rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void initSlider() {
        lstSlides = new ArrayList<Slider>();
        lstSlides.add(new Slider(R.drawable.avengers, "Avengers..."));
        lstSlides.add(new Slider(R.drawable.hobbit, "Hobbit..."));
        lstSlides.add(new Slider(R.drawable.inception, "Inception..."));

        SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), lstSlides);
        sliderpager.setAdapter(adapter);
    }

    private void initView(View root) {
        sliderpager = root.findViewById(R.id.slider_pager);
        trendingRv = root.findViewById(R.id.Rv_trending);
        list1Rv = root.findViewById(R.id.Rv_phimle);
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieThumbnail) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("movieThumb", movie.getThumbnail());
        intent.putExtra("desc", movie.getDescription());
        intent.putExtra("linkMovie", movie.getMovieUrl());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), movieThumbnail, "movieThumb");

        startActivity(intent, options.toBundle());
    }
}