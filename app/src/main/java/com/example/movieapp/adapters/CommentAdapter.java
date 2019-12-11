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
import com.example.movieapp.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    Context mContext;
    List<Comment> lstComments;

    public CommentAdapter(Context mContext, List<Comment> lstComments) {
        this.mContext = mContext;
        this.lstComments = lstComments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        holder.tvUsername.setText(lstComments.get(position).getUsername());
        holder.tvComment.setText(lstComments.get(position).getComment());
        holder.tvDate.setText(lstComments.get(position).getDate());
        Glide.with(holder.itemView.getContext()).load(R.drawable.user_image).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return lstComments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        TextView tvComment;
        ImageView ivAvatar;
        TextView tvDate;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvComment= itemView.findViewById(R.id.tv_comment);
            tvDate = itemView.findViewById(R.id.tv_comment_date);
        }
    }
}
