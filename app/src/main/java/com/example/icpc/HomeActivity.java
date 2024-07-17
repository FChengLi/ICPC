package com.example.icpc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

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

        // 如果 savedInstanceState 为空，表示是第一次创建活动，加载默认的 Fragment
        if (savedInstanceState == null) {
            // 使用 HomeFragment 替换 fragment_container 容器
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fast_Learning_Fragment()).commit();
        }
    }

    // 定义底部导航栏的选择监听器
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                // 定义一个变量来存储选中的 Fragment
                Fragment selectedFragment = null;

                // 根据选择的导航项 ID，实例化相应的 Fragment
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    selectedFragment = new Fast_Learning_Fragment();
                } else if (itemId == R.id.nav_search) {
                    selectedFragment = new History_Fragment();
                } else if (itemId == R.id.nav_notifications) {
                    selectedFragment = new Discover_Fragment();
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = new Tieba_Fragment();
                } else if (itemId == R.id.nav_settings) {
                    selectedFragment = new Profile_Fragment();
                }

                // 使用选中的 Fragment 替换 fragment_container 容器
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                // 返回 true 表示处理了这个选择事件
                return true;
            };
}
