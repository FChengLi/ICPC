package com.example.icpc;

public class Column {
    private int id;
    private String title;
    private int imageUrl; // 修改为int类型
    private String source;
    private String date;

    public Column(int id, String title, int imageUrl, String source, String date) {
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

    public int getImageUrl() {
        return imageUrl; // 修改为返回int类型
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }
}
