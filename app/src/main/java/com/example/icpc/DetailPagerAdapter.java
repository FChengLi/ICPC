package com.example.icpc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DetailPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_FRAGMENTS = 2;
    private Comment_Fragment commentFragment;

    public DetailPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new IntroduceFragment();
        } else if (position == 1) {
            commentFragment = new Comment_Fragment();
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
