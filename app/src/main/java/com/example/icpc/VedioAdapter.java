package com.example.icpc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.ViewHolder> {
    private List<DataItem> mDataList;
    private Context mContext;

    public VedioAdapter(Context context, List<DataItem> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem item = mDataList.get(position);

        holder.imageView.setImageResource(item.getImageResId());
        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView10);
            textView1 = itemView.findViewById(R.id.textView9);
            textView2 = itemView.findViewById(R.id.textView11);
        }
    }
}