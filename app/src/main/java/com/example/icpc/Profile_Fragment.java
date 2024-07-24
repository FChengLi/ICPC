package com.example.icpc;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Profile_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // 找到“编辑资料”按钮
        TextView editProfileButton = view.findViewById(R.id.edit_profile_button);

        // 设置点击事件监听器
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 EditingActivity
                Intent intent = new Intent(getActivity(), EditingActivity.class);
                startActivity(intent);
            }
        });

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        RecordPagerAdapter adapter = new RecordPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
