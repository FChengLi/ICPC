package com.example.icpc;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.ViewHolder> {
    private List<DataItem> dataItemList;
    private OnItemClickListener onItemClickListener;

    public VedioAdapter(List<DataItem> dataItemList) {
        this.dataItemList = dataItemList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dataItem = dataItemList.get(position);

        holder.coverImageView.setImageResource(dataItem.getCoverpath());
        holder.titleTextView.setText(dataItem.getTitle());
        holder.authorTextView.setText(dataItem.getAuthor());

    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView titleTextView;
        TextView authorTextView; // Ensure this matches the ID in XML

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.cover);
            titleTextView = itemView.findViewById(R.id.title);
            authorTextView = itemView.findViewById(R.id.author); // Ensure this matches the ID in XML

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
