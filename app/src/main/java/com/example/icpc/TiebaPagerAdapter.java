package com.example.icpc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TiebaPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public TiebaPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        initFragments();
    }

    protected void initFragments() {
        fragments.add(new Myba_Fragment());
        fragments.add(new Basquare_Fragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "我的吧";
            case 1:
                return "吧广场";
            default:
                return null;
        }
    }
}
