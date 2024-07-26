package com.example.icpc.myinfor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class my_RecordAdapter extends FragmentPagerAdapter {

    public my_RecordAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new my_collection();
            case 1:
                return new my_browsing_history();
            default:
                return new my_collection();
        }
    }

    @Override
    public int getCount() {
        return 2; // Two tabs: Collection and Browsing History
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "收藏";
            case 1:
                return "浏览记录";
            default:
                return null;
        }
    }
}
