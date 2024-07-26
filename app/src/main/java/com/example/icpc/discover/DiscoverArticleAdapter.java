package com.example.icpc.discover;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.example.icpc.R;

import java.util.List;

public class DiscoverArticleAdapter extends RecyclerView.Adapter<DiscoverArticleAdapter.ArticleViewHolder> {
    private List<discover_article> articleList;
    private Context context;

    public DiscoverArticleAdapter(Context context, List<discover_article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        discover_article article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource() != null ? article.getSource() : "未知来源");
        holder.date.setText(article.getDate() != null ? article.getDate() : "未知日期");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, discover_article_content.class);
            intent.putExtra("article_id", String.valueOf(article.getId()));
            Log.d("ArticleAdapter", "Sending article ID to detail view: " + article.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void updateData(List<discover_article> newArticleList) {
        this.articleList = newArticleList;
        notifyDataSetChanged();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title, source, date;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            source = itemView.findViewById(R.id.itemSource);
            date = itemView.findViewById(R.id.itemDate);
        }
    }
}
