package com.example.icpc.myinfor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.icpc.R;
import com.google.android.material.tabs.TabLayout;

public class Profile_Fragment extends Fragment {

    private TextView usernameTextView;
    private TextView phoneNumberTextView;
    private ImageView settingImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // 找到“编辑资料”按钮
        TextView editProfileButton = view.findViewById(R.id.edit_profile_button);

        // 初始化TextView
        usernameTextView = view.findViewById(R.id.username);
        phoneNumberTextView = view.findViewById(R.id.id);
        settingImageView = view.findViewById(R.id.setting);

        // 设置点击事件监听器
        editProfileButton.setOnClickListener(v -> {
            // 启动 EditingActivity
            Intent intent = new Intent(getActivity(), EditingActivity.class);
            startActivity(intent);
        });

        // 设置点击事件监听器
        settingImageView.setOnClickListener(v -> {
            // 启动 my_setting 活动
            Intent intent = new Intent(getActivity(), my_setting.class);
            startActivity(intent);
        });

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // 加载用户信息
        loadUserInfo();

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        my_RecordAdapter adapter = new my_RecordAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void loadUserInfo() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("currentUsername", "昵称");
        String phoneNumber = sharedPreferences.getString("currentPhoneNumber", "手机号");

        usernameTextView.setText(username);
        phoneNumberTextView.setText(phoneNumber);
    }
}
