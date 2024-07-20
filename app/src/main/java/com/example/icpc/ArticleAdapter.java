package com.example.icpc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articleList;

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        Log.d("ArticleAdapter", "onBindViewHolder: Binding article at position " + position + " with title " + currentArticle.getTitle());
        holder.itemTitle.setText(currentArticle.getTitle());
        holder.itemSource.setText(currentArticle.getSource());
        holder.itemDate.setText(currentArticle.getDate());
        holder.itemImage.setImageResource(currentArticle.getImageResource());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView itemSource;
        public TextView itemDate;
        public ImageView itemImage;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemSource = itemView.findViewById(R.id.itemSource);
            itemDate = itemView.findViewById(R.id.itemDate);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
