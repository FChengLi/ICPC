package com.example.icpc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentItem> commentList;

    public CommentAdapter(List<CommentItem> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentItem commentItem = commentList.get(position);
        holder.usernameTextView.setText(commentItem.getUsername());
        holder.commentTextView.setText(commentItem.getComment());

        // 设置初始的点赞图标
        holder.likeImageView.setImageResource(commentItem.isLiked() ? R.drawable.liked : R.drawable.like);

        // 设置点击事件处理
        holder.likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLiked = commentItem.isLiked();
                commentItem.setLiked(!isLiked);
                holder.likeImageView.setImageResource(commentItem.isLiked() ? R.drawable.liked : R.drawable.like);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView commentTextView;
        ImageView likeImageView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
            commentTextView = itemView.findViewById(R.id.comment);
            likeImageView = itemView.findViewById(R.id.like);
        }
    }
}
