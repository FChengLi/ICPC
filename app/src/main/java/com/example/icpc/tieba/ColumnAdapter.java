package com.example.icpc.tieba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.R;

import java.util.List;

public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.ViewHolder> {

    private final Context context;
    private final List<Post> columns;
    private final int forumId; // 添加 forumId

    public ColumnAdapter(Context context, List<Post> columns, int forumId) {
        this.context = context;
        this.columns = columns;
        this.forumId = forumId; // 初始化 forumId
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_column, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post column = columns.get(position);
        holder.itemTitle.setText(column.getTitle());
        holder.itemDate.setText(column.getPublishtime());

        // 如果需要加载图片
        // Glide.with(context).load(column.getImageUrl()).into(holder.itemImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostContentActivity.class);
            intent.putExtra("postId", column.getPostid()); // 传递文章 ID
            intent.putExtra("forumId", forumId); // 传递论坛 ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return columns.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, itemDate;
        ImageView itemImage;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDate = itemView.findViewById(R.id.itemDate);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}

