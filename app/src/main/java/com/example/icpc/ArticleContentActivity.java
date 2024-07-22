package com.example.icpc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ArticleContentActivity extends AppCompatActivity {

    private ArticleContentViewModel viewModel;
    private Toolbar toolbar;
    private LinearLayout llContent, llComment, llBottom, llShoucang, llStar, comment;
    private EditText commentContent;
    private TextView sendComment, author, registerTime, content, commentSum, starSum, tvShoucang, tvStarSum;
    private ImageView headPic, ivShoucang, ivStar;
    private RecyclerView commentList;
    private int articleId;
    private Bundle data;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        data = getIntent().getExtras();
        articleId = data.getInt("id");

        initView();
        setupToolbar();
        setupCommentList();

        viewModel = new ViewModelProvider(this).get(ArticleContentViewModel.class);
        viewModel.init(articleId);
        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentAdapter.setComments(comments);
            }
        });

        viewModel.getArticle().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(Article article) {
                updateArticleView(article);
            }
        });

        sendComment.setOnClickListener(v -> {
            String commentText = commentContent.getText().toString();
            if (!commentText.isEmpty()) {
                viewModel.addComment(commentText);
                commentContent.setText("");
                Toast.makeText(ArticleContentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
            }
        });

        headPic.setOnClickListener(v -> navigateToAuthorDetail(data.getInt("authorid")));
        author.setOnClickListener(v -> navigateToAuthorDetail(data.getInt("authorid")));

        llShoucang.setOnClickListener(v -> {
            ivShoucang.setSelected(true);
            Toast.makeText(ArticleContentActivity.this, "收藏成功!", Toast.LENGTH_SHORT).show();
        });

        llStar.setOnClickListener(v -> {
            ivStar.setSelected(true);
            starSum.setText(String.valueOf(data.getInt("starSum") + 1));
            viewModel.incrementStar();
            Toast.makeText(ArticleContentActivity.this, "点赞成功!", Toast.LENGTH_SHORT).show();
        });

        comment.setOnClickListener(v -> {
            llBottom.setVisibility(View.GONE);
            llComment.setVisibility(View.VISIBLE);
        });

        llContent.setOnClickListener(v -> {
            llBottom.setVisibility(View.VISIBLE);
            llComment.setVisibility(View.GONE);
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.content_toolbar);
        llComment = findViewById(R.id.ll_comment);
        commentContent = findViewById(R.id.comment_content);
        sendComment = findViewById(R.id.send_comment);
        llBottom = findViewById(R.id.ll_bottom);
        comment = findViewById(R.id.comment);
        llContent = findViewById(R.id.ll_content);
        headPic = findViewById(R.id.article_head_pic);
        author = findViewById(R.id.contet_userName);
        registerTime = findViewById(R.id.content_release_time);
        content = findViewById(R.id.content_article_content);
        commentSum = findViewById(R.id.content_comment_sum);
        starSum = findViewById(R.id.star_sum);
        llShoucang = findViewById(R.id.ll_shoucang);
        ivShoucang = findViewById(R.id.iv_shoucang);
        llStar = findViewById(R.id.ll_star);
        ivStar = findViewById(R.id.iv_star);
        tvShoucang = findViewById(R.id.tv_shoucang);
        tvStarSum = findViewById(R.id.tv_starSum);
        commentList = findViewById(R.id.content_comment_list);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupCommentList() {
        commentList.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter();
        commentList.setAdapter(commentAdapter);
    }

    private void updateArticleView(Article article) {
        author.setText(article.getAuthor());
        registerTime.setText(article.getTime());
        content.setText(article.getContent());
        commentSum.setText(String.valueOf(article.getCommentSum()));
        starSum.setText(String.valueOf(article.getStarSum()));
    }

    private void navigateToAuthorDetail(int authorId) {
        Intent intent = new Intent(ArticleContentActivity.this, PersonalInformationDetailsPageActivity.class);
        intent.putExtra("id", authorId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
