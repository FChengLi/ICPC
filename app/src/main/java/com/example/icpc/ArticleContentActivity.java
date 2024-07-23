package com.example.icpc;

import android.content.Context;
import android.content.Intent;
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
    private CommentAdapter_Feng commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        // 获取传递的数据并初始化articleId
        articleId = getIntent().getIntExtra("id", -1);
        if (articleId == -1) {
            Toast.makeText(this, "无效的文章ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 初始化视图
        initView();
        // 设置工具栏
        setupToolbar();
        // 设置评论列表
        setupCommentList();

        // 初始化ViewModel并观察数据变化
        viewModel = new ViewModelProvider(this).get(ArticleContentViewModel.class);
        viewModel.init(articleId);

        viewModel.getComments().observe(this, new Observer<List<Comment_Feng>>() {
            @Override
            public void onChanged(List<Comment_Feng> comments) {
                commentAdapter.setComments(comments);
            }
        });

        viewModel.getArticle().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(Article article) {
                updateArticleView(article);
            }
        });

        // 发送评论按钮点击事件
        sendComment.setOnClickListener(v -> {
            String commentText = commentContent.getText().toString().trim();
            if (!commentText.isEmpty()) {
                viewModel.addComment(commentText);
                commentContent.setText("");
                Toast.makeText(ArticleContentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                viewModel.incrementComment();
            } else {
                Toast.makeText(ArticleContentActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            }
        });

        // 作者头像点击事件，导航到作者详情页
        headPic.setOnClickListener(v -> navigateToAuthorDetail(getIntent().getIntExtra("authorid", -1)));
        author.setOnClickListener(v -> navigateToAuthorDetail(getIntent().getIntExtra("authorid", -1)));

        // 收藏按钮点击事件
        llShoucang.setOnClickListener(v -> {
            ivShoucang.setSelected(!ivShoucang.isSelected());
            Toast.makeText(ArticleContentActivity.this, ivShoucang.isSelected() ? "收藏成功!" : "取消收藏", Toast.LENGTH_SHORT).show();
        });

        // 点赞按钮点击事件
        llStar.setOnClickListener(v -> {
            ivStar.setSelected(!ivStar.isSelected());
            if (ivStar.isSelected()) {
                starSum.setText(String.valueOf(Integer.parseInt(starSum.getText().toString()) + 1));
                viewModel.incrementStar();
                Toast.makeText(ArticleContentActivity.this, "点赞成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ArticleContentActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
            }
        });

        // 显示评论输入框
        comment.setOnClickListener(v -> {
            llBottom.setVisibility(View.GONE);
            llComment.setVisibility(View.VISIBLE);
        });

        // 隐藏评论输入框
        llContent.setOnClickListener(v -> {
            llBottom.setVisibility(View.VISIBLE);
            llComment.setVisibility(View.GONE);
        });
    }

    // 初始化视图控件
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

    // 设置工具栏
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // 设置评论列表
    private void setupCommentList() {
        commentList.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter_Feng();
        commentList.setAdapter(commentAdapter);
    }

    // 更新文章视图
    private void updateArticleView(Article article) {
        author.setText(article.getAuthor());
        registerTime.setText(article.getTime());
        content.setText(article.getContent());
        commentSum.setText(String.valueOf(article.getCommentSum()));
        starSum.setText(String.valueOf(article.getStarSum()));
    }

    // 导航到作者详情页
    private void navigateToAuthorDetail(int authorId) {
        Intent intent = new Intent(ArticleContentActivity.this, PersonalInformationDetailsPageActivity.class);
        intent.putExtra("id", authorId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
    }

    // 处理触摸事件，隐藏输入法键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判断是否应该隐藏输入法键盘
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    // 重写finish方法，添加动画效果
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
