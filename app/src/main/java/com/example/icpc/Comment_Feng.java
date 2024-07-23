package com.example.icpc;

// 评论类，表示文章中的一条评论
public class Comment_Feng {
    // 评论的ID
    private int id;
    // 评论作者的名字
    private String authorName;
    // 评论的时间
    private String time;
    // 评论的内容
    private String content;
    // 评论的点赞数量
    private int starSum;
    // 构造方法，用于初始化评论对象
    public Comment_Feng(int id, String authorName, String time, String content, int starSum) {
        this.id = id;
        this.authorName = authorName;
        this.time = time;
        this.content = content;
        this.starSum = starSum;
    }
    public void setStarSum(int starSum) {
        this.starSum = starSum;
    }

    // 获取评论ID
    public int getId() {
        return id;
    }

    // 获取评论作者名字
    public String getAuthorName() {
        return authorName;
    }

    // 获取评论时间
    public String getTime() {
        return time;
    }

    // 获取评论内容
    public String getContent() {
        return content;
    }

    // 获取评论点赞数量
    public int getStarSum() {
        return starSum;
    }

}
