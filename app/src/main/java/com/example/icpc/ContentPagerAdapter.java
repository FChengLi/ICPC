package com.example.icpc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ContentPagerAdapter extends FragmentStateAdapter {

    private List<ContentFragment> fragmentList;

    public ContentPagerAdapter(@NonNull FragmentActivity fa, List<ContentFragment> fragmentList) {
        super(fa);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
