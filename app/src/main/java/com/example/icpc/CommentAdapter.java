package com.example.icpc;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments = new ArrayList<>();

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.commentAuthorName.setText(comment.getAuthorName());
        holder.commentContent.setText(comment.getContent());
        holder.commentTime.setText(comment.getTime());
        holder.commentSum.setText(String.valueOf(comment.getStarSum()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentAuthorName, commentContent, commentTime, commentSum;
        LinearLayout llCommentStar;
        ImageView ivCommentStar, commentHeadPic;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthorName = itemView.findViewById(R.id.comment_authorName);
            commentContent = itemView.findViewById(R.id.commentContent);
            commentTime = itemView.findViewById(R.id.commentTime);
            commentSum = itemView.findViewById(R.id.commentStarSum);
            llCommentStar = itemView.findViewById(R.id.ll_comment_star);
            ivCommentStar = itemView.findViewById(R.id.iv_comment_star);
            commentHeadPic = itemView.findViewById(R.id.comment_head_pic);
        }
    }
}
