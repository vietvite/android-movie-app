package com.example.movieapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;
import com.example.movieapp.commons.Parser;
import com.example.movieapp.models.User;
import com.example.movieapp.ui.dialog.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity   extends AppCompatActivity {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPassword;
    private Button btnSignup;
    SharedPreferences sp;
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        tvEmail = findViewById(R.id.tvEmail);
        tvName = findViewById(R.id.tvName);
        tvPassword = findViewById(R.id.tvPassword);
        btnSignup = findViewById(R.id.btnSignup);

        setContentView(R.layout.activity_signup);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        loadingDialog = new LoadingDialog(this);
    }

    public void backClick(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void SignupClick(View v){

        if (!validate()) {
            onSignupFail();
            return;
        }
        tvEmail = findViewById(R.id.tvEmail);
        tvName = findViewById(R.id.tvName);
        tvPassword = findViewById(R.id.tvPassword);
        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();
        String name = tvName.getText().toString();
        loadingDialog.show();
        new UserService().execute(email, password,name);
    }

    public boolean validate() {
        tvEmail = findViewById(R.id.tvEmail);
        tvName = findViewById(R.id.tvName);
        tvPassword = findViewById(R.id.tvPassword);
        boolean valid = true;

        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();
        String name = tvName.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tvEmail.setError("Email không đúng");
            valid = false;
        } else {
            tvEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            tvPassword.setError("Mật khẩu phải ít nhất 4 ký tự");
            valid = false;
        } else {
            tvPassword.setError(null);
        }

        if(name.isEmpty() || name.length() > 100){
            tvName.setError("Tên không vượt quá 100 ký tự!");
            valid = false;
        } else {
            tvName.setError(null);
        }
        return valid;
    }

    public void onSignupSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Đăng ký không thành công!", Toast.LENGTH_LONG).show();
            }
        });
    }

    class UserService extends AsyncTask<String, Void, Void> implements Callback {

        @Override
        protected Void doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/signup").newBuilder();

            String url = urlBuilder.build().toString();
            Log.e("url", url);


            RequestBody formBody = new FormBody.Builder()
                    .add("email", strings[0])
                    .add("password", strings[1])
                   .add("name",strings[2])
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();


            client.newCall(request).enqueue(this);
            return null;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            onSignupFail();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String rawStr = response.body().string();


            onSignupSuccess();
        }
    }
}
