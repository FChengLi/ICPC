package com.example.icpc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.ArticleContentActivity;
import com.example.icpc.R;
import com.example.icpc.Column;

import java.util.List;

public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.ViewHolder> {

    private List<Column> columns;
    private Context context;

    public ColumnAdapter(Context context, List<Column> columns) {
        this.context = context;
        this.columns = columns;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_column, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Column column = columns.get(position);
        holder.itemTitle.setText(column.getTitle());
        holder.itemSource.setText(column.getSource());
        holder.itemDate.setText(column.getDate());
        // Load image using your preferred image loading library (e.g., Glide or Picasso)
        // Glide.with(context).load(column.getImageUrl()).into(holder.itemImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleContentActivity.class);
            intent.putExtra("id", column.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return columns.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, itemSource, itemDate;
        ImageView itemImage;
        LinearLayout titleImageContainer;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemSource = itemView.findViewById(R.id.itemSource);
            itemDate = itemView.findViewById(R.id.itemDate);
            itemImage = itemView.findViewById(R.id.itemImage);
            titleImageContainer = itemView.findViewById(R.id.titleImageContainer);
        }
    }
}
