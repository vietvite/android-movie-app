package com.example.movieapp.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.movieapp.R;
import com.example.movieapp.ui.DetailActivity;
import com.example.movieapp.ui.LoginActivity;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);

        View root = inflater.inflate(R.layout.fragment_community, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
//        accountViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        SharedPreferences sharedPrefs = inflater.getContext().getSharedPreferences("user", MODE_PRIVATE);
        if(sharedPrefs.contains("email")){
            Toast.makeText(getContext(),"kaka",Toast.LENGTH_LONG);
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }


        return root;
    }
}