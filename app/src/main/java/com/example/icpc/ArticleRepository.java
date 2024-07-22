package com.example.icpc;

import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章仓库类，用于管理和提供文章及评论数据。
 */
public class ArticleRepository {

    // 单例实例
    private static ArticleRepository instance;

    // 用于存储单篇文章的 MutableLiveData
    private MutableLiveData<Article> article;

    // 用于存储评论列表的 MutableLiveData
    private MutableLiveData<List<Comment>> comments;

    /**
     * 获取 ArticleRepository 的单例实例。
     * @return ArticleRepository 实例
     */
    public static ArticleRepository getInstance() {
        if (instance == null) {
            instance = new ArticleRepository();
        }
        return instance;
    }

    /**
     * 获取指定文章的 MutableLiveData 对象。
     * @param articleId 文章 ID
     * @return 文章的 MutableLiveData 对象
     */
    public MutableLiveData<Article> getArticle(int articleId) {
        // 模拟数据
        if (article == null) {
            article = new MutableLiveData<>();
            // 创建一个模拟的文章对象
            Article mockArticle = new Article(articleId, "MeihuTETL", "2022.5.20", "hhhh", 0, 0);
            article.setValue(mockArticle);
        }
        return article;
    }

    /**
     * 获取指定文章的评论列表的 MutableLiveData 对象。
     * @param articleId 文章 ID
     * @return 评论列表的 MutableLiveData 对象
     */
    public MutableLiveData<List<Comment>> getComments(int articleId) {
        // 模拟数据
        if (comments == null) {
            comments = new MutableLiveData<>();
            // 创建一个模拟的评论列表
            List<Comment> mockComments = new ArrayList<>();
            mockComments.add(new Comment(1, "user1", "2022.5.20", "Great article!", 5));
            comments.setValue(mockComments);
        }
        return comments;
    }

    /**
     * 添加一条评论到评论列表中。
     * @param commentText 评论内容
     */
    public void addComment(String commentText) {
        // 判断评论列表是否存在
        if (comments.getValue() != null) {
            List<Comment> currentComments = comments.getValue();
            // 添加新评论到当前评论列表中
            currentComments.add(new Comment(currentComments.size() + 1, "user2", "2022.5.21", commentText, 0));
            // 更新评论列表
            comments.setValue(currentComments);
        }
    }

    /**
     * 增加文章的评分总数。
     */
    public void incrementStar() {
        // 判断文章是否存在
        if (article.getValue() != null) {
            Article currentArticle = article.getValue();
            // 增加评分总数
            currentArticle.setStarSum(currentArticle.getStarSum() + 1);
            // 更新文章数据
            article.setValue(currentArticle);
        }
    }
}
