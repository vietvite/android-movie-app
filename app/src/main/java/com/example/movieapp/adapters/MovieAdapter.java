package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context;
    List<Movie> lstMovies;
    MovieItemClickListener movieItemClickListener;


    public MovieAdapter(Context context, List<Movie> data, MovieItemClickListener clickListener) {
        this.context = context;
        this.lstMovies = data;
        movieItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mTitle.setText(lstMovies.get(position).getTitle());
        Glide.with(context).load(lstMovies.get(position).getThumbnail()).into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return lstMovies != null ? lstMovies.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private ImageView mThumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.movie_item_title);
            mThumbnail = itemView.findViewById(R.id.movie_item_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(lstMovies.get(getAdapterPosition()), mThumbnail);
                }
            });
        }
    }
}
