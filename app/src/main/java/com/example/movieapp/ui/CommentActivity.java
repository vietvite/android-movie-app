package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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
import com.example.movieapp.ui.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    LoadingDialog loadingDialog;

    static String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getSupportActionBar().hide();

        rvComment = findViewById(R.id.rv_comment);
        etComment = findViewById(R.id.tb_edit_comment);
        btnPostComment = findViewById(R.id.btn_send_comment);

        loadingDialog = new LoadingDialog(this);

        SharedPreferences sharedPrefs = getSharedPreferences("user", MODE_PRIVATE);

        btnPostComment.setOnClickListener(v -> {
//            TODO: get username from sharedPreference
            String email = sharedPrefs.getString("email","");
            if(email.equalsIgnoreCase("")) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Đăng nhập để bình luận", Toast.LENGTH_LONG).show();
                });
                return;
            }

            String username = email.split("[@]")[0];
            String comment = etComment.getText().toString();
            btnPostComment.setEnabled(false);
            new PostCommentService().execute(new Comment(username, comment));
            etComment.setText("Posting...");
            etComment.setEnabled(false);

        });


        movieId = getIntent().getExtras().getString("movieId");
        new FetchCommentService().execute(movieId);
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
                    lstComments = new ArrayList<>();
                }

//                Update temporary date used for ui
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                comment.setDate(formatter.format(new Date()));

                lstComments.add(comment);
                initFav(lstComments);
                rvComment.scrollToPosition(lstComments.size() - 1);

                btnPostComment.setEnabled(true);
                etComment.setText("");
                etComment.setEnabled(true);

            });
        }
    }

    class FetchCommentService extends AsyncTask<String, Void, List<Comment>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

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
            loadingDialog.dismiss();
            if(comments != null && comments.size() != 0) {
                initFav(comments);
            } else {
                Toast.makeText(CommentActivity.this, "Hãy là người đầu tiên comment", Toast.LENGTH_SHORT);
            }
//            runOnUiThread(() -> {
//            });

        }
    }

    private void initFav(List<Comment> lstComments) {
        commentAdapter = new CommentAdapter(CommentActivity.this, lstComments);
        rvComment.setAdapter(commentAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false));
    }
}
