package com.example.icpc.myinfor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.R;
import com.example.icpc.discover.discover_article;
import com.example.icpc.discover.discover_article_content;

import java.util.List;

public class my_CollectionAdapter extends RecyclerView.Adapter<my_CollectionAdapter.CollectionViewHolder> {
    private List<discover_article> collectionList;
    private Context context;

    public my_CollectionAdapter(Context context, List<discover_article> collectionList) {
        this.context = context;
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_article, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        discover_article article = collectionList.get(position);
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource() != null ? article.getSource() : "未知来源");
        holder.date.setText(article.getDate() != null ? article.getDate() : "未知日期");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, discover_article_content.class);
            intent.putExtra("article_id", String.valueOf(article.getId()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        TextView title, source, date;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            source = itemView.findViewById(R.id.itemSource);
            date = itemView.findViewById(R.id.itemDate);
        }
    }
}
