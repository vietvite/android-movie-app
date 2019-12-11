package com.example.movieapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText tvEmail;
    private EditText tvPassword;
    private Button btnLogin;
    private Button btnSignup;
    private LoadingDialog loadingDialog;

    SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin.setOnClickListener(v -> login());

        loadingDialog = new LoadingDialog(this);
        sp = getSharedPreferences("user", MODE_PRIVATE);
    }

    public void btnSignupClick(View v) {
        Intent intent = new Intent(getBaseContext(), SignupActivity.class);
        startActivity(intent);
    }


    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();

        loadingDialog.show();
        new UserService().execute(email, password);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_LONG).show();
            }
        });
        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();

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

        return valid;
    }

    class UserService extends AsyncTask<String, Void, Void> implements Callback {

        @Override
        protected Void doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://film-vietvite.herokuapp.com/api/login").newBuilder();

            String url = urlBuilder.build().toString();
            Log.e("url", url);


            RequestBody formBody = new FormBody.Builder()
                    .add("email", strings[0])
                    .add("password", strings[1])
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
            onLoginFailed();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String rawStr = response.body().string();

            User user = Parser.parseUser(rawStr);


            SharedPreferences.Editor edit = sp.edit();
            edit.putString("userId", user.get_id());
            edit.putString("email", user.getEmail());
            edit.putBoolean("logged", true);
            edit.putString("rawStr", rawStr);
            edit.apply();

            onLoginSuccess();
        }
    }
}
