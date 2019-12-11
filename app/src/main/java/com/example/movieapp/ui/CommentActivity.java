package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.movieapp.R;
import com.example.movieapp.adapters.CommentAdapter;
import com.example.movieapp.models.Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    CommentAdapter commentAdapter;
    List<Comment> lstComments;
    RecyclerView rvComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getSupportActionBar().hide();

        rvComment = findViewById(R.id.rv_comment);

//        TODO: fetch movie's comments from API
        lstComments = new ArrayList<>();
        lstComments.add(new Comment("asd", "Viet", "08/12/2019", "Such a nice movie. Love it"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));

        initFav(lstComments);
    }

    class CommentService extends AsyncTask<String, Void, List<Comment>> {

        @Override
        protected List<Comment> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/favorite").newBuilder();

//            TODO: Implement SharedPreferences to get userId
            urlBuilder.addPathSegment("add");
            urlBuilder.addPathSegment("5def5b80c2bd5c8b261e9e8e"); // userId
            urlBuilder.addPathSegment(strings[0]); // movieId
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void initFav(List<Comment> lstComments) {
        commentAdapter = new CommentAdapter(CommentActivity.this, lstComments);
        rvComment.setAdapter(commentAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false));
    }
}
