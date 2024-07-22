package com.example.icpc;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ArticleContentViewModel extends AndroidViewModel {

    private MutableLiveData<Article> article;
    private MutableLiveData<List<Comment>> comments;
    private ArticleRepository repository;

    public ArticleContentViewModel(Application application) {
        super(application);
        repository = ArticleRepository.getInstance(application.getApplicationContext());
    }

    public void init(int articleId) {
        if (article != null && comments != null) {
            return;
        }
        article = repository.getArticle(articleId);
        comments = repository.getComments(articleId);
    }

    public LiveData<Article> getArticle() {
        return article;
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public void addComment(String commentText) {
        repository.addComment(commentText, article.getValue().getId());
    }

    public void incrementStar() {
        repository.incrementStar(article.getValue().getId());
    }
    public void incrementComment() {
        repository.incrementComment(article.getValue().getId());
    }
    public int getCommentCount(int postId) {
        return repository.getCommentCount(postId);
    }
}
