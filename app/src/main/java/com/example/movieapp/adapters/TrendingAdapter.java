package com.example.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.Movie;
import com.example.movieapp.ui.PlayerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TrendingAdapter extends PagerAdapter {
    private Context context;
    private List<Movie> lstTrending;

    public TrendingAdapter(Context context, List<Movie> mList) {
        this.context = context;
        this.lstTrending = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View slideLayout = LayoutInflater.from(context).inflate(R.layout.slider_item, null);
        ImageView slideImage = slideLayout.findViewById(R.id.slider_img);
        TextView slideText = slideLayout.findViewById(R.id.slider_title);
        FloatingActionButton btnPlay = slideLayout.findViewById(R.id.btn_hot_play);

        Glide.with(container).load(lstTrending.get(position).getCoverImg()).into(slideImage);
        slideText.setText(lstTrending.get(position).getTitle());

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, PlayerActivity.class);
                mIntent.putExtra("linkMovie", "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1920_18MG.mp4");
                context.startActivity(mIntent);
            }
        });

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return lstTrending.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
