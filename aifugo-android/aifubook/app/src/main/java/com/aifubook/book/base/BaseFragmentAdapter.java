package com.aifubook.book.base;


import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> title) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        }
        return super.getPageTitle(position);
    }


    @Override
    public int getItemPosition(Object object) {
     //POSITION_NONE 是一个PagerAdapter的内部常量，值是-2，
        return POSITION_NONE;
    }

}
