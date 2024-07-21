package com.example.icpc;

public class Article {
    private String title;
    private String source;
    private String date;
    private int imageResId;

    public Article(String title, String source, String date, int imageResId) {
        this.title = title;
        this.source = source;
        this.date = date;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getSource() { return source; }
    public String getDate() { return date; }
    public int getImageResId() { return imageResId; }
}
