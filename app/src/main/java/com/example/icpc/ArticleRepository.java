package com.example.icpc;

import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private static ArticleRepository instance;
    private MutableLiveData<Article> article;
    private MutableLiveData<List<Comment>> comments;

    public static ArticleRepository getInstance() {
        if (instance == null) {
            instance = new ArticleRepository();
        }
        return instance;
    }

    public MutableLiveData<Article> getArticle(int articleId) {
        // Mock data
        if (article == null) {
            article = new MutableLiveData<>();
            Article mockArticle = new Article(articleId, "MeihuTETL", "2022.5.20", "hhhh", 0, 0);
            article.setValue(mockArticle);
        }
        return article;
    }

    public MutableLiveData<List<Comment>> getComments(int articleId) {
        // Mock data
        if (comments == null) {
            comments = new MutableLiveData<>();
            List<Comment> mockComments = new ArrayList<>();
            mockComments.add(new Comment(1, "user1", "2022.5.20", "Great article!", 5));
            comments.setValue(mockComments);
        }
        return comments;
    }

    public void addComment(String commentText) {
        if (comments.getValue() != null) {
            List<Comment> currentComments = comments.getValue();
            currentComments.add(new Comment(currentComments.size() + 1, "user2", "2022.5.21", commentText, 0));
            comments.setValue(currentComments);
        }
    }

    public void incrementStar() {
        if (article.getValue() != null) {
            Article currentArticle = article.getValue();
            currentArticle.setStarSum(currentArticle.getStarSum() + 1);
            article.setValue(currentArticle);
        }
    }
}
