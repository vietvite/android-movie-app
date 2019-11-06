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
import com.example.movieapp.model.Slider;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Slider> mList;

    public SliderPagerAdapter(Context mContext, List<Slider> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slider_item, null);

        ImageView slideImage = slideLayout.findViewById(R.id.slider_img);
        TextView slideText = slideLayout.findViewById(R.id.slider_title);
        slideImage.setImageResource(mList.get(position).getImage());
        slideText.setText(mList.get(position).getTitle());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
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
