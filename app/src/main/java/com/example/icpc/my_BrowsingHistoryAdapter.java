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

public class my_BrowsingHistoryAdapter extends RecyclerView.Adapter<my_BrowsingHistoryAdapter.HistoryViewHolder> {
    private List<discover_article> historyList;
    private Context context;

    public my_BrowsingHistoryAdapter(Context context, List<discover_article> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_article, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        discover_article article = historyList.get(position);
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
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView title, source, date;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            source = itemView.findViewById(R.id.itemSource);
            date = itemView.findViewById(R.id.itemDate);
        }
    }
}
