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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.adapters.MovieItemClickListener;
import com.example.movieapp.adapters.TrendingAdapter;
import com.example.movieapp.commons.Parser;
import com.example.movieapp.models.Movie;
import com.example.movieapp.ui.DetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements MovieItemClickListener {

    private HomeViewModel homeViewModel;

    private ViewPager vpTrending;
    private RecyclerView rvSeeMost;
    private RecyclerView rvAction;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initView(root);

        homeViewModel.getTrending().observe(this, lstTrending -> {
            if(lstTrending == null) {
                new MovieService().execute();
            } else {
                initSlider(lstTrending);
            }
        });

        homeViewModel.getSeeMost().observe(this, lstSeeMost -> {
            if(lstSeeMost == null) {
                new MovieService().execute();
            } else {
                updateSeeMostMovies(lstSeeMost);
            }
        });

        homeViewModel.getAction().observe(this, lstAction -> {
            if(lstAction == null) {
                new MovieService().execute();
            } else {
                updateActionMovies(lstAction);
            }
        });


        return root;
    }

    class MovieService extends AsyncTask<String, Void, List<Movie>> implements Callback {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://film-vietvite.herokuapp.com/api/movie")
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

                String trending = jsonObject.getString("trending");
                String seeMost = jsonObject.getString("seeMost");
                String action = jsonObject.getString("action");

                List<Movie> lstTrending = Parser.parseListMovie(trending);
                List<Movie> lstSeeMost = Parser.parseListMovie(seeMost);
                List<Movie> lstAction = Parser.parseListMovie(action);

                getActivity().runOnUiThread(() -> {
                    initSlider(lstTrending);
                    updateSeeMostMovies(lstSeeMost);
                    updateActionMovies(lstAction);
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSeeMostMovies(List<Movie> lstMovies) {
        TextView tvTrendingTitle = getActivity().findViewById(R.id.tv_trending_title);
        tvTrendingTitle.setText("See most");
        MovieAdapter trendingAdapter = new MovieAdapter(getActivity(), lstMovies, this);
        rvSeeMost.setAdapter(trendingAdapter);
        rvSeeMost.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void updateActionMovies(List<Movie> lstMovies) {
        TextView tvTrendingTitle = getActivity().findViewById(R.id.tv_list1_title);
        tvTrendingTitle.setText("Hành động");
        MovieAdapter trendingAdapter = new MovieAdapter(getActivity(), lstMovies, this);
        rvAction.setAdapter(trendingAdapter);
        rvAction.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void initSlider(List<Movie> lstTrending) {
        TrendingAdapter adapter = new TrendingAdapter(getActivity(), lstTrending);
        vpTrending.setAdapter(adapter);
    }

    private void initView(View root) {
        vpTrending = root.findViewById(R.id.slider_pager);
        rvSeeMost = root.findViewById(R.id.Rv_trending);
        rvAction = root.findViewById(R.id.Rv_phimle);
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieThumbnail) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movieId", movie.get_id());
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