package com.example.icpc.tieba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icpc.R;

import java.util.List;

public class BaIconAdapter extends RecyclerView.Adapter<BaIconAdapter.ViewHolder> {
    private List<IconItem> iconList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(IconItem iconItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public BaIconAdapter(List<IconItem> iconList) {
        this.iconList = iconList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IconItem iconItem = iconList.get(position);
        holder.iconImageView.setImageResource(iconItem.getIconResId());
        holder.iconNameTextView.setText(iconItem.getIconName());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(iconItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView iconNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            iconNameTextView = itemView.findViewById(R.id.iconTextView);
        }
    }
}
