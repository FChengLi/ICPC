package com.example.icpc;

public class Message {
    private int id;
    private String userId;
    private String type;
    private String messageName;
    private String messageText;
    private String time;

    public Message(String userId, String type, String messageName, String messageText, String time) {
        this.userId = userId;
        this.type = type;
        this.messageName = messageName;
        this.messageText = messageText;
        this.time = time;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getTime() {
        return time;
    }
}
