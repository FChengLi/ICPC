package com.example.icpc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    private List<IconItem> iconList;

    public IconAdapter(List<IconItem> iconList) {
        this.iconList = iconList;
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_icon, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        IconItem iconItem = iconList.get(position);
        holder.iconImageView.setImageResource(iconItem.getIconResId());
        holder.iconTextView.setText(iconItem.getIconName());
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public static class IconViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView iconTextView;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            iconTextView = itemView.findViewById(R.id.iconTextView);
        }
    }
}
