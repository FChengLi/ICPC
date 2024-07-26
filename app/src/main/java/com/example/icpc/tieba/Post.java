package com.example.icpc.tieba;

// 帖子类，表示一个帖子
public class Post {
    // 帖子的ID
    private String postid;
    // 帖子的发布者
    private String userid;
    // 帖子所属的论坛
    private  int forumid;
    // 评论总数
    private int commentSum;
    // 点赞总数
    private int likeSum;
    // 帖子标题
    private String title;
    // 帖子发布日期
    private String publishtime;

    // 完整构造函数，初始化所有属性
    public Post(String postid, String userid, int forumid, int commentSum, int likeSum, String title, String publishtime) {
        this.postid = postid;
        this.userid = userid;
        this.forumid = forumid;
        this.commentSum = commentSum;
        this.likeSum = likeSum;
        this.title = title;
        this.publishtime = publishtime;
    }

    // 部分构造函数，只初始化必要属性
    public Post(String postid, String title, int forumid, String publishtime) {
        this(postid, null, forumid, 0, 0,title, publishtime ); // 调用全参构造函数
    }

    // 获取文章ID
    public String getPostid() {
        return postid;
    }

    // 获取文章作者
    public String getUserid() {
        return userid;
    }

    // 获取帖子所属论坛
    public int getForumid() {return forumid;}

    // 获取评论总数
    public int getCommentSum() {
        return commentSum;
    }

    // 获取点赞总数
    public int getLikeSum() {
        return likeSum;
    }

    // 设置点赞总数
    public void setLikeSum(int starSum) {
        this.likeSum = starSum;
    }

    // 获取文章标题
    public String getTitle() {
        return title;
    }

    // 获取文章日期
    public String getPublishtime() {
        return publishtime;
    }
}
