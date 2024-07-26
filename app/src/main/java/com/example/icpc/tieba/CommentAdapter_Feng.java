package com.example.icpc.tieba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.R;

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
        holder.commentAuthorName.setText(comment.getUserid());
        holder.commentContent.setText(comment.getContent());
        holder.commentTime.setText(comment.getCommenttime());
        holder.commentSum.setText(String.valueOf(comment.getLikeSum()));

        // 可以根据需要设置其他属性
        // holder.commentHeadPic.setImageResource(R.drawable.some_image); // 示例：设置头像图片
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

            // 设置评论点赞按钮的点击事件
            llCommentStar.setOnClickListener(v -> {
                // 在这里处理评论点赞的逻辑
                // 比如更新评论的点赞状态
                if (ivCommentStar.isSelected()) {
                    ivCommentStar.setSelected(false);
                    // 执行取消点赞的逻辑，比如更新数据库
                } else {
                    ivCommentStar.setSelected(true);
                    // 执行点赞的逻辑，比如更新数据库
                }
                // 更新评论的点赞数显示
                // 注意需要通知数据更新
            });
        }
    }
}
