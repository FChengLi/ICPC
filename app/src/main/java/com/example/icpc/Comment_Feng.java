package com.example.icpc;

// 评论类，表示文章中的一条评论
public class Comment_Feng {
    // 评论的ID
    private String commentid;
    // 评论作者的ID
    private String userid;
    // 评论的时间
    private String commenttime;
    // 评论的内容
    private String content;
    // 评论的点赞数量
    private int likeSum;
    // 构造方法，用于初始化评论对象
    public Comment_Feng(String commentid, String userid, String commenttime, String content, int likeSum) {
        this.commentid = commentid;
        this.userid = userid;
        this.commenttime = commenttime;
        this.content = content;
        this.likeSum = likeSum;
    }

    // 获取评论ID
    public String getCommentid() {
        return commentid;
    }

    // 获取评论作者名字
    public String getUserid() {
        return userid;
    }

    // 获取评论时间
    public String getCommenttime() {
        return commenttime;
    }

    // 获取评论内容
    public String getContent() {
        return content;
    }

    // 获取评论点赞数量
    public int getLikeSum() {
        return likeSum;
    }

}
