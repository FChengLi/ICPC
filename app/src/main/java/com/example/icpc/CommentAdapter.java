package com.example.icpc;

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

// 评论适配器类，用于RecyclerView展示评论列表
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    // 存储评论的列表
    private List<Comment> comments = new ArrayList<>();

    // 创建ViewHolder对象并初始化
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载评论项布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_comment, parent, false);
        return new CommentViewHolder(view);
    }

    // 绑定数据到ViewHolder
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // 获取当前评论对象
        Comment comment = comments.get(position);
        // 设置评论作者名字
        holder.commentAuthorName.setText(comment.getAuthorName());
        // 设置评论内容
        holder.commentContent.setText(comment.getContent());
        // 设置评论时间
        holder.commentTime.setText(comment.getTime());
        // 设置评论点赞数量
        holder.commentSum.setText(String.valueOf(comment.getStarSum()));
    }

    // 获取评论数量
    @Override
    public int getItemCount() {
        return comments.size();
    }

    // 设置评论列表并刷新数据
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    // ViewHolder类，用于缓存评论项的视图
    static class CommentViewHolder extends RecyclerView.ViewHolder {
        // 声明视图控件
        TextView commentAuthorName, commentContent, commentTime, commentSum;
        LinearLayout llCommentStar;
        ImageView ivCommentStar, commentHeadPic;

        // 构造方法，初始化视图控件
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
