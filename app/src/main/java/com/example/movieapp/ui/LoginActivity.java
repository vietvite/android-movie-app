package com.example.movieapp.ui;

import android.app.ProgressDialog;
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

import org.json.JSONObject;

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(v -> login());

    }

    private void login() {
        Log.d(TAG,"login");
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Checking");
        btnSignup.setEnabled(false);

        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();

        new UserService().execute("viet@gmail.com", "zzzz");
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
////                        progressDialog.dismiss();
//                    }
//                }, 5000);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        Toast.makeText(getBaseContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Đăng nhập không thành công", Toast.LENGTH_LONG).show();

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
//            urlBuilder.encodedUsername(strings[0]);
//            urlBuilder.encodedPassword(strings[1]);

            String url = urlBuilder.build().toString();
            Log.e("url", url);


            RequestBody formBody = new FormBody.Builder()
                    .add("email", strings[0])
                    .add("password", strings[1])
//                    .add("customCredential", "")
//                    .add("isPersistent", "true")
//                    .add("setCookie", "true")
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

            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();

            edit.putString("userId", user.get_id());
            edit.putString("email", user.getEmail());
            edit.putBoolean("logged", true);
            edit.putString("rawStr", rawStr);
            edit.apply();

//            getSupportFragmentManager().findFragmentById(R.id.navigation_home)
        }
    }
}
