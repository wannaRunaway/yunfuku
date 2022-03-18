package com.aifubook.book.groupon;

import android.os.Bundle;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.base.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.jiarui.base.baserx.bean.MessageEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class GrouponOrderListActivity extends BaseActivity {



    private int type;//0.全部 1.待付款 2.待配送  3.待提货 4.已提货 5.售后
    public List<Fragment> listFragment = new ArrayList<>();
    public String[] title = new String[]{"全部", "待付款", "拼团中", "待发货", "待收货", "待核销", "交易完成", "售后"};

    @Override
    protected int setContentView() {
        return R.layout.activity_groupon_order;
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.ORDER_ADD_CAR) {
            GrouponOrderListActivity.this.finish();
        }

    }

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

//    @Override
//    protected void onInit(Bundle bundle) {
//        super.onInit(bundle);
//        viewPager=findViewById(R.id.viewpager);
//        tabLayout=findViewById(R.id.tab_layout);
//        initViewPage();
//    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tab_layout);
        initViewPage();
        var leftimageview = findViewById(R.id.imageview_left);
        leftimageview.setOnClickListener(v -> finish());
        var titlname = (TextView)findViewById(R.id.header_textview);
        titlname.setText("我的订单");
    }

    private void initViewPage() {
        listFragment.add(new GrouponOrderFragment(0));
        listFragment.add(new GrouponOrderFragment(1));
        listFragment.add(new GrouponOrderFragment(2));
        listFragment.add(new GrouponOrderFragment(3));
        listFragment.add(new GrouponOrderFragment(4));
        listFragment.add(new GrouponOrderFragment(5));
        listFragment.add(new GrouponOrderFragment(6));
        listFragment.add(new GrouponOrderFragment(7));
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
