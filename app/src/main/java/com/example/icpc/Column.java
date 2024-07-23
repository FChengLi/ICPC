package com.example.icpc;

public class Column {
    private int id;
    private String title;
    private String imageUrl;
    private String source;
    private String date;

    public Column(int id, String title, String imageUrl, String source, String date) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.source = source;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }
}
