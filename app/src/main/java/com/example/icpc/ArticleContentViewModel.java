package com.example.icpc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class ArticleContentViewModel extends ViewModel {

    private MutableLiveData<Article> article;
    private MutableLiveData<List<Comment>> comments;
    private ArticleRepository repository;

    public void init(int articleId) {
        if (article != null) {
            return;
        }
        repository = ArticleRepository.getInstance();
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
        repository.addComment(commentText);
    }

    public void incrementStar() {
        repository.incrementStar();
    }
}
