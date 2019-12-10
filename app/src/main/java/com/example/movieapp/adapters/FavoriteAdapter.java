package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.Movie;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    Context mContext;
    List<Movie> lstMovies;
    MovieItemClickListener movieItemClickListener;

    public FavoriteAdapter(Context mContext, List<Movie> lstMovies, MovieItemClickListener movieItemClickListener) {
        this.mContext = mContext;
        this.lstMovies = lstMovies;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fav_item, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.tvTitle.setText(lstMovies.get(position).getTitle());
        holder.tvRating.setText(String.valueOf(lstMovies.get(position).getRate()));
        holder.ratingBar.setRating(lstMovies.get(position).getRate());
        Glide.with(holder.itemView.getContext()).load(lstMovies.get(position).getThumbnail()).into(holder.ivMovieImg);
    }

    @Override
    public int getItemCount() {
        return lstMovies != null ? lstMovies.size() : 0;
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvRating;
        RatingBar ratingBar;
        ImageView ivMovieImg;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImg = itemView.findViewById(R.id.iv_fav_item_movieImg);
            tvTitle = itemView.findViewById(R.id.tv_fav_item_title);
            tvRating = itemView.findViewById(R.id.tv_fav_item_rating);
            ratingBar = itemView.findViewById(R.id.fav_item_ratingBar);

            itemView.setOnClickListener(v -> movieItemClickListener.onMovieClick(lstMovies.get(getAdapterPosition()), ivMovieImg));
        }
    }
}
