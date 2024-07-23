package com.example.icpc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.database.ArticleDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter_Feng extends RecyclerView.Adapter<CommentAdapter_Feng.CommentViewHolder> {

    private List<Comment_Feng> comments = new ArrayList<>();

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment_Feng comment = comments.get(position);
        holder.commentAuthorName.setText(comment.getAuthorName());
        holder.commentContent.setText(comment.getContent());
        holder.commentTime.setText(comment.getTime());
        holder.commentSum.setText(String.valueOf(comment.getStarSum()));

        // 根据点赞数确定图标
        if (comment.getStarSum() > 0) { // 你可能需要调整这个条件，根据实际逻辑来判断是否已点赞
            holder.ivCommentStar.setImageResource(R.drawable.star_selected);
        } else {
            holder.ivCommentStar.setImageResource(R.drawable.article_star);
        }

        holder.ivCommentStar.setOnClickListener(v -> {
            int newStarSum = comment.getStarSum() + 1;
            comment.setStarSum(newStarSum);
            holder.commentSum.setText(String.valueOf(newStarSum));
            holder.ivCommentStar.setImageResource(R.drawable.star_selected);

            ArticleDatabase database = new ArticleDatabase(holder.itemView.getContext());
            database.incrementCommentStar(comment.getId());

            // 通知数据已经改变
            notifyItemChanged(position);
        });
    }



    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<Comment_Feng> comments) {
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
