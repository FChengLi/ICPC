package com.example.icpc;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.os.AsyncTask;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private String userId = "unique_user_id"; // Replace with actual user id
    private MessageDao messageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.msglist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageDao = new MessageDao(this);

        adapter = new MessageAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    public void onReturnClicked(View view) {
        finish();
    }

    public void showReplies(View view) {
        loadMessages("replies");
    }

    public void showLikes(View view) {
        loadMessages("likes");
    }

    public void showNotifications(View view) {
        loadMessages("notifications");
    }

    private void loadMessages(String type) {
        new LoadMessagesTask(messageDao, userId, type, new LoadMessagesTask.Callback() {
            @Override
            public void onResult(List<Message> messages) {
                adapter.setMessages(messages);
                adapter.notifyDataSetChanged();
            }
        }).execute();
    }

    private static class LoadMessagesTask extends AsyncTask<Void, Void, List<Message>> {
        private MessageDao messageDao;
        private String userId;
        private String type;
        private Callback callback;

        public interface Callback {
            void onResult(List<Message> messages);
        }

        LoadMessagesTask(MessageDao messageDao, String userId, String type, Callback callback) {
            this.messageDao = messageDao;
            this.userId = userId;
            this.type = type;
            this.callback = callback;
        }

        @Override
        protected List<Message> doInBackground(Void... voids) {
            return messageDao.getMessagesByType(userId, type);
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            callback.onResult(messages);
        }
    }
}
