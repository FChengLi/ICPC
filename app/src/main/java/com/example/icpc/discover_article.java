package com.example.icpc;

// 文章类，表示一篇文章
public class discover_article {
    // 文章的ID
    private int postid;
    // 文章的作者
    private String author;
    // 文章发布时间
    private String time;
    // 文章内容
    private String content;
    // 评论总数
    private int commentSum;
    // 点赞总数
    private int starSum;
    // 文章标题
    private String title;
    // 文章来源
    private String source;
    // 文章日期
    private String date;
    // 文章图片资源ID
    private int imageResource;

    // 完整构造函数，初始化所有属性
    public discover_article(int postid, String author, String time, String content, int commentSum, int starSum, String title, String source, String date, int imageResource) {
        this.postid = postid;
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

    // 部分构造函数，只初始化必要属性
    public discover_article(int id, String author, String time, String content, int commentSum, int starSum) {
        this(id, author, time, content, commentSum, starSum, null, null, null, 0);
    }

    // 获取文章ID
    public int getPostid() {
        return postid;
    }

    // 获取文章作者
    public String getAuthor() {
        return author;
    }

    // 获取文章发布时间
    public String getTime() {
        return time;
    }

    // 获取文章内容
    public String getContent() {
        return content;
    }

    // 获取评论总数
    public int getCommentSum() {
        return commentSum;
    }

    // 获取点赞总数
    public int getStarSum() {
        return starSum;
    }

    // 设置点赞总数
    public void setStarSum(int starSum) {
        this.starSum = starSum;
    }

    // 获取文章标题
    public String getTitle() {
        return title;
    }

    // 获取文章来源
    public String getSource() {
        return source;
    }

    // 获取文章日期
    public String getDate() {
        return date;
    }

    // 获取文章图片资源ID
    public int getImageResource() {
        return imageResource;
    }
}
