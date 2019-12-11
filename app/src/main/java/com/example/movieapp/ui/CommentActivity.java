package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.CommentAdapter;
import com.example.movieapp.commons.Parser;
import com.example.movieapp.models.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    CommentAdapter commentAdapter;
    static List<Comment> lstComments;
    RecyclerView rvComment;
    EditText etComment;
    Button btnPostComment;

    static String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getSupportActionBar().hide();

        rvComment = findViewById(R.id.rv_comment);
        etComment = findViewById(R.id.tb_edit_comment);
        btnPostComment = findViewById(R.id.btn_send_comment);

        btnPostComment.setOnClickListener(v -> {
            btnPostComment.setEnabled(false);
//            TODO: get username from sharedPreference
            String username = "Viet";
            String comment = etComment.getText().toString();
            new PostCommentService().execute(new Comment(username, comment));
            etComment.setText("Posting...");
            etComment.setEnabled(false);

        });

//        TODO: fetch movie's comments from API
//        lstComments = new ArrayList<>();
//        lstComments.add(new Comment("asd", "Viet", "08/12/2019", "Such a nice movie. Love it"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));
//        lstComments.add(new Comment("asdasd", "Thanh", "08/12/2019", "This is comment of the movie. This is the movie I love most. Oh I love this so much. Do you know, sister?"));


        movieId = getIntent().getExtras().getString("movieId");
        new FetchCommentService().execute(movieId);

//        initFav(lstComments);
    }

    class PostCommentService extends AsyncTask<Comment, Void, Comment> {

        @Override
        protected Comment doInBackground(Comment... comments) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/comment").newBuilder();
            urlBuilder.addPathSegment(movieId);

            String url = urlBuilder.build().toString();
            Log.e("url", url);


            RequestBody formBody = new FormBody.Builder()
                    .add("comment", comments[0].getComment())
                    .add("username", comments[0].getUsername())
                    .build();

            Request request = new Request.Builder()
                    .post(formBody)
                    .url(url)
                    .build();

            Response response;
            try {
                response = client.newCall(request).execute();
                String rawStr = response.body().string();
                JSONObject o = new JSONObject(rawStr);
                Boolean success = o.getBoolean("success");
                if(success) {
                    return comments[0];
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Comment comment) {
            super.onPostExecute(comment);
            CommentActivity.this.runOnUiThread(() -> {
                if(lstComments == null) {
                    Log.e("lstComments", "DANG nullll");
                    lstComments = new ArrayList<>();
                }
                lstComments.add(comment);
                Log.e("lstComments.get(0", lstComments.get(0).getComment());
//                initFav(lstComments);
                btnPostComment.setEnabled(true);
                etComment.setText("");
                etComment.setEnabled(true);
                Toast.makeText(CommentActivity.this, "Đã đăng bình luận", Toast.LENGTH_SHORT);

            });
        }
    }

    class FetchCommentService extends AsyncTask<String, Void, List<Comment>> {

        @Override
        protected List<Comment> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/comment").newBuilder();

//            TODO: Implement SharedPreferences to get userId
            urlBuilder.addPathSegment(strings[0]); // movieId
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response;
            try {
                response = client.newCall(request).execute();
                String rawStr = response.body().string();
                if(!rawStr.equals("false")) {
                    lstComments = Parser.parseListComment(rawStr);
                    return lstComments;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Comment> comments) {
            super.onPostExecute(comments);
            runOnUiThread(() -> {
                if(comments != null && comments.size() != 0) {
                    initFav(comments);
                } else {
                    Toast.makeText(CommentActivity.this, "Hãy là người đầu tiên comment", Toast.LENGTH_SHORT);
                }
            });

        }
    }

    private void initFav(List<Comment> lstComments) {
        commentAdapter = new CommentAdapter(CommentActivity.this, lstComments);
        rvComment.setAdapter(commentAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false));
    }
}
