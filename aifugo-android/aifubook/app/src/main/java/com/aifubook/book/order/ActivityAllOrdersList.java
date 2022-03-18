package com.aifubook.book.order;


import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.jiarui.base.baserx.bean.MessageEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class ActivityAllOrdersList extends BaseActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.header_textview)
    TextView header_textview;
    @BindView(R.id.imageview_left)
    ImageView imageView_left;

    private int type;//0.全部 1.待付款 2.待配送  3.待提货 4.已提货 5.售后
    public List<Fragment> listFragment = new ArrayList<>();
    public String[] title = new String[]{"全部", "待付款","待发货","待收货","待自提","交易完成","退款/售后"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_order_list;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.ORDER_ADD_CAR){
            ActivityAllOrdersList.this.finish();
        }

    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type",0);
        header_textview.setText("我的订单");
        imageView_left.setOnClickListener(v -> finish());
        initViewPage();

    }

    private void initViewPage() {
        listFragment.add(new MyOrdersFragment(0));
        listFragment.add(new MyOrdersFragment(1));
        listFragment.add(new MyOrdersFragment(2));
        listFragment.add(new MyOrdersFragment(3));
        listFragment.add(new MyOrdersFragment(4));
        listFragment.add(new MyOrdersFragment(5));
        listFragment.add(new MyOrdersFragment(6));
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), listFragment));
        tabLayout.setViewPager(viewPager);
        switch (type){
            case 0:
                viewPager.setCurrentItem(0);//设置初始选中页面
                break;
            case 1:
                viewPager.setCurrentItem(1);//设置初始选中页面
                break;
            case 2:
                viewPager.setCurrentItem(2);//设置初始选中页面
                break;
            case 3:
                viewPager.setCurrentItem(3);//设置初始选中页面
                break;
            case 4:
                viewPager.setCurrentItem(4);//设置初始选中页面
                break;
            case 5:
                viewPager.setCurrentItem(5);//设置初始选中页面
                break;
            case 6:
                viewPager.setCurrentItem(6);//设置初始选中页面
                break;
        }
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