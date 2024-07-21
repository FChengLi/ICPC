package com.example.icpc;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private FrameLayout customFab;
    private FrameLayout fabContainer;
    private int margin = 16; // 边距
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Setup the top toolbar
        Toolbar topToolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);

        // 获取底部导航栏视图
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // 设置导航栏的选择监听器
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Default fragment
        // 初始化自定义悬浮按钮
        customFab = findViewById(R.id.custom_fab);
        fabContainer = findViewById(R.id.fab_container);

        customFab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Aides_Activity.class);
            startActivity(intent);
        });

        // 设置自定义悬浮按钮的初始位置
        customFab.post(() -> {
            int fabContainerHeight = fabContainer.getHeight();
            int fabHeight = customFab.getHeight();
            int centerY = (fabContainerHeight - fabHeight) / 2;
            customFab.setX(fabContainer.getWidth() - customFab.getWidth() - 16);
            customFab.setY(centerY);
        });

        // 设置自定义悬浮按钮的触摸监听器
        customFab.setOnTouchListener(new View.OnTouchListener() {
            private float dX, dY;
            private int lastAction;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX() + dX;
                        float newY = event.getRawY() + dY;

                        // 检查是否在边界内
                        if (newX < 0) newX = 0;
                        if (newX > fabContainer.getWidth() - view.getWidth()) newX = fabContainer.getWidth() - view.getWidth();
                        if (newY < 0) newY = 0;
                        if (newY > fabContainer.getHeight() - view.getHeight()) newY = fabContainer.getHeight() - view.getHeight();

                        view.setX(newX);
                        view.setY(newY);
                        lastAction = MotionEvent.ACTION_MOVE;
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            view.performClick();
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

        // 为搜索框设置点击监听器
        EditText searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.setOnClickListener(this::onSearchClick);

        View whiteRectangle = findViewById(R.id.white_rectangle);
        whiteRectangle.setOnClickListener(this::onSearchClick);

        View searchIcon = findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(this::onSearchClick);
    }
    // 定义搜索框和图标的点击事件处理函数
    public void onSearchClick(View view) {
        Intent intent = new Intent(HomeActivity.this, Search_Fragment.class);
        startActivity(intent);
    }
    // 定义底部导航栏的选择监听器
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                // 定义一个变量来存储选中的 Fragment
                Fragment selectedFragment = null;
                // 根据选择的导航项 ID，实例化相应的 Fragment
                int itemId = item.getItemId();
                if (itemId == R.id.nav_fast_learning) {
                    selectedFragment = new Fast_Learning_Fragment();
                } else if (itemId == R.id.nav_history) {
                    selectedFragment = new History_Fragment();
                } else if (itemId == R.id.nav_discover) {
                    selectedFragment = new Discover_Fragment();
                } else if (itemId == R.id.nav_tieba) {
                    selectedFragment = new Tieba_Fragment();
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = new Profile_Fragment();
                }

                // 使用选中的 Fragment 替换 fragment_container 容器
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                // 返回 true 表示处理了这个选择事件
                return true;
            };
}
