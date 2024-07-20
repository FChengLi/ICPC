package com.example.icpc;

public class Article {
    private String title;
    private String source;
    private String date;
    private int imageResource;

    public Article(String title, String source, String date, int imageResource) {
        this.title = title;
        this.source = source;
        this.date = date;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public int getImageResource() {
        return imageResource;
    }
}
