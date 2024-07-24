package com.example.icpc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MybaForumAdapter extends RecyclerView.Adapter<MybaForumAdapter.ForumViewHolder> {

    private List<String> forumNames;
    private List<Integer> forumIds; // 论坛ID列表，用于点击事件
    private Context context;

    public MybaForumAdapter(Context context, List<String> forumNames, List<Integer> forumIds) {
        this.context = context;
        this.forumNames = forumNames;
        this.forumIds = forumIds;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myba_item, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        final String forumName = forumNames.get(position);
        final String forumId = String.valueOf(forumIds.get(position));
        holder.forumNameTextView.setText(forumName);

        // 点击事件处理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 ColumnActivity，并传递论坛ID等信息
                Intent intent = new Intent(context, ColumnActivity.class);
                intent.putExtra("iconName",forumName);
                intent.putExtra("forumId", forumId); // 传递论坛ID
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forumNames.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        TextView forumNameTextView;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            forumNameTextView = itemView.findViewById(R.id.mybatitle);
        }
    }
}
