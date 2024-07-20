package com.example.icpc;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aides_Activity extends AppCompatActivity {

    private static final String API_URL = "https://api.oaipro.com/v1/chat/completions";
    private static final String API_KEY = "sk-NW1S9pufv0oNMbOXE38eA5D2419649Bc8d20463f9d71911d";

    private RecyclerView rvChat;
    private EditText etMessage;
    private Button btnSend;
    private ImageView ivBack;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aides);

        rvChat = findViewById(R.id.rv_chat);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        ivBack = findViewById(R.id.iv_back);

        chatAdapter = new ChatAdapter(chatMessages);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(chatAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                if (!message.isEmpty()) {
                    etMessage.setText("");
                    addMessage("You", message);
                    sendMessageToAI(message);
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 返回上一页
            }
        });
    }

    private void addMessage(String sender, String message) {
        chatMessages.add(new ChatMessage(sender, message));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        rvChat.scrollToPosition(chatMessages.size() - 1);
    }

    private void sendMessageToAI(String userMessage) {
        OkHttpClient client = new OkHttpClient();

        try {
            // 创建请求内容
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", "gpt-3.5-turbo");

            // 添加默认提示词和用户消息
            JSONArray messagesArray = new JSONArray();
            messagesArray.put(new JSONObject().put("role", "system").put("content", "你是一个党史小助手，你的名字是“i党史”小助手，你服务于i党史app，你主要回答用户关于党政和党史的问题，注意要避免回答过于敏感的问题（中国敏感的内容）"));
            messagesArray.put(new JSONObject().put("role", "user").put("content", userMessage));

            jsonObject.put("messages", messagesArray);

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addMessage("Error", e.getMessage());
                        }
                    });
                }

                @Override
                public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                    String responseData = response.body().string();
                    if (response.isSuccessful()) {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            JSONArray choices = json.getJSONArray("choices");
                            final String aiMessage = choices.getJSONObject(0).getJSONObject("message").getString("content");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    typeMessage(aiMessage, "AI");
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addMessage("Error", "Error parsing JSON: " + e.getMessage());
                                    addMessage("Response", responseData);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addMessage("Error", response.message());
                                addMessage("Response", responseData);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            addMessage("Error", e.getMessage());
        }
    }

    private void typeMessage(final String message, final String sender) {
        final int delay = 100; // 每个字母的延迟时间（毫秒）
        final int[] index = {0};
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (index[0] < message.length()) {
                    if (index[0] == 0) {
                        addMessage(sender, message.substring(0, index[0] + 1));
                    } else {
                        chatAdapter.updateMessage(chatMessages.size() - 1, message.substring(0, index[0] + 1));
                    }
                    index[0]++;
                    handler.postDelayed(this, delay);
                }
            }
        };

        handler.post(runnable);
    }
}
