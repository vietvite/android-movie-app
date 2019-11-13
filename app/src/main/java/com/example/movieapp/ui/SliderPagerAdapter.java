package com.example.movieapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.movieapp.R;
import com.example.movieapp.models.Slider;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Slider> lstSlides;

    public SliderPagerAdapter(Context context, List<Slider> mList) {
        this.context = context;
        this.lstSlides = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View slideLayout = LayoutInflater.from(context).inflate(R.layout.slider_item, null);
        ImageView slideImage = slideLayout.findViewById(R.id.slider_img);
        TextView slideText = slideLayout.findViewById(R.id.slider_title);

        slideImage.setImageResource(lstSlides.get(position).getImage());
        slideText.setText(lstSlides.get(position).getTitle());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return lstSlides.size();
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
