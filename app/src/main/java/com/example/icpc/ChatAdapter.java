package com.example.icpc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        if (message.getSender().equals("You")) {
            holder.leftChatBubble.setVisibility(View.GONE);
            holder.rightChatBubble.setVisibility(View.VISIBLE);
            holder.rightMessage.setText(message.getMessage());
        } else {
            holder.rightChatBubble.setVisibility(View.GONE);
            holder.leftChatBubble.setVisibility(View.VISIBLE);
            holder.leftMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void updateMessage(int position, String newMessage) {
        if (position >= 0 && position < chatMessages.size()) {
            chatMessages.get(position).setMessage(newMessage);
            notifyItemChanged(position);
        }
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatBubble;
        LinearLayout rightChatBubble;
        TextView leftMessage;
        TextView rightMessage;
        ImageView leftAvatar;
        ImageView rightAvatar;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatBubble = itemView.findViewById(R.id.left_chat_bubble);
            rightChatBubble = itemView.findViewById(R.id.right_chat_bubble);
            leftMessage = itemView.findViewById(R.id.left_message);
            rightMessage = itemView.findViewById(R.id.right_message);
            leftAvatar = itemView.findViewById(R.id.left_avatar);
            rightAvatar = itemView.findViewById(R.id.right_avatar);
        }
    }
}
