package com.example.icpc;

public class Comment {
    private int id;
    private String authorName;
    private String time;
    private String content;
    private int starSum;

    public Comment(int id, String authorName, String time, String content, int starSum) {
        this.id = id;
        this.authorName = authorName;
        this.time = time;
        this.content = content;
        this.starSum = starSum;
    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getStarSum() {
        return starSum;
    }
}
