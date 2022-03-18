package com.aifubook.book.mine.coupons;


import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class CouponsActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int type;//0.未使用 1.已使用 2.已过期
    public List<Fragment> listFragment = new ArrayList<>();
    public String[] title = new String[]{"已使用", "已过期"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupons;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTitle("失效券");
        initViewPage();
    }

    private void initViewPage() {
        listFragment.add(new CouponsFragment(1));
        listFragment.add(new CouponsFragment(2));
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), listFragment));
        tabLayout.setViewPager(viewPager);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //返回每个Tab的标题，当要自定义Tab的时候不应该重写该方法
        @Override

        public CharSequence getPageTitle(int position) {
            return title[position];
        }

    }
}