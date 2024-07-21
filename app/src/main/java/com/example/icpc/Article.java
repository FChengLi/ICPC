package com.example.icpc;

public class Article {
    private int id;
    private String author;
    private String time;
    private String content;
    private int commentSum;
    private int starSum;
    private String title;
    private String source;
    private String date;
    private int imageResource;

    // 完整构造函数
    public Article(int id, String author, String time, String content, int commentSum, int starSum, String title, String source, String date, int imageResource) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.content = content;
        this.commentSum = commentSum;
        this.starSum = starSum;
        this.title = title;
        this.source = source;
        this.date = date;
        this.imageResource = imageResource;
    }

    // 部分构造函数
    public Article(int id, String author, String time, String content, int commentSum, int starSum) {
        this(id, author, time, content, commentSum, starSum, null, null, null, 0);
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getCommentSum() {
        return commentSum;
    }

    public int getStarSum() {
        return starSum;
    }

    public void setStarSum(int starSum) {
        this.starSum = starSum;
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
