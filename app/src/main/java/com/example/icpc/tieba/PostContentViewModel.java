package com.example.icpc.tieba;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class PostContentViewModel extends AndroidViewModel {

    private MutableLiveData<Post> post;
    private MutableLiveData<List<Comment_Feng>> comments;
    private PostRepository repository;

    public PostContentViewModel(Application application) {
        super(application);
        repository = PostRepository.getInstance(application.getApplicationContext());
    }

    public void init(String postId) {
        if (post != null && comments != null) {
            return;
        }
        post = repository.getPost(postId);
        comments = repository.getComments(postId);
    }

    public LiveData<Post> getPost() {
        return post;
    }

    public LiveData<List<Comment_Feng>> getComments() {
        return comments;
    }

    public void addComment(String commentText) {
        if (post.getValue() != null) {
            repository.addComment(commentText, post.getValue().getPostid());
        }
    }

    public void incrementStar() {
        if (post.getValue() != null) {
            repository.incrementStar(post.getValue().getPostid());
        }
    }

    public void incrementComment() {
        if (post.getValue() != null) {
            repository.incrementComment(post.getValue().getPostid());
        }
    }

    public int getCommentCount(String postId) {
        return repository.getCommentCount(postId); // 使用 PostRepository 中的 getCommentCount 方法
    }
}
