package com.example.icpc.discover;

public class discover_article {
    private int id;
    private String author;
    private String publishTime;
    private String content;
    private int likes;
    private int star;
    private String title;
    private String source;
    private String date;

    public discover_article(int id, String author, String publishTime, String content, int likes, int star, String title, String source, String date) {
        this.id = id;
        this.author = author;
        this.publishTime = publishTime;
        this.content = content;
        this.likes = likes;
        this.star = star;
        this.title = title;
        this.source = source;
        this.date = date;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public int getStar() {
        return star;
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
}
