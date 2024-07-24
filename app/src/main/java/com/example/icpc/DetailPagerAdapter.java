package com.example.icpc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DetailPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_FRAGMENTS = 2;
    private Comment_Fragment commentFragment;
    private DataItem dataItem;

    public DetailPagerAdapter(@NonNull FragmentManager fm, DataItem dataItem) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return IntroduceFragment.newInstance(dataItem);
        } else if (position == 1) {
            commentFragment = Comment_Fragment.newInstance(dataItem);
            return commentFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    public Fragment getCommentFragment() {
        return commentFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "介绍";
            case 1:
                return "评论";
            default:
                return null;
        }
    }
}
