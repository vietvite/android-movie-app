package com.example.movieapp.ui.favorite;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.adapters.MovieItemClickListener;
import com.example.movieapp.commons.Parser;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.User;
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
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteViewModel extends AndroidViewModel {

    static List<Movie> movies = null;
    static User user;
    private MutableLiveData<List<Movie>> lstFav;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        lstFav = new MutableLiveData<>();
    }


    public LiveData<List<Movie>> getListFavorites() {
        lstFav.setValue(movies);
        return lstFav;
    }

    class MovieService extends AsyncTask<String, Void, List<Movie>> implements Callback {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            SharedPreferences sharedPrefs = getApplication().getSharedPreferences("user", MODE_PRIVATE);
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();
            urlBuilder.addPathSegment(sharedPrefs.getString("user","userId"));
            String url = urlBuilder.build().toString();
            Log.e("url", url);

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
        public void onResponse(Call call, Response response) throws IOException {
            try {
                String resData = response.body().string();
                JSONObject o = new JSONObject(resData);
                String favStr = o.getString("favorite");
                movies = Parser.parseListMovie(favStr);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}