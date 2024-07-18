package com.example.icpc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AnotherActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.oaipro.com/v1/chat/completions";
    private static final String API_KEY = "sk-NW1S9pufv0oNMbOXE38eA5D2419649Bc8d20463f9d71911d";

    private TextView tvChat;
    private EditText etMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aides);

        tvChat = findViewById(R.id.tv_chat);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                if (!message.isEmpty()) {
                    etMessage.setText("");
                    appendChat("You: " + message);
                    sendMessageToAI(message);
                }
            }
        });
    }

    private void appendChat(String message) {
        String currentText = tvChat.getText().toString();
        tvChat.setText(currentText + "\n" + message);
    }

    private void sendMessageToAI(String message) {
        OkHttpClient client = new OkHttpClient();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", "gpt-3.5-turbo");  // 确保使用正确的模型
            jsonObject.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", message)));

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
                            appendChat("Error: " + e.getMessage());
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
                                    appendChat("AI: " + aiMessage);
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    appendChat("Error parsing JSON: " + e.getMessage());
                                    appendChat("Response: " + responseData);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                appendChat("Error: " + response.message());
                                appendChat("Response: " + responseData);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            appendChat("Error: " + e.getMessage());
        }
    }
}
