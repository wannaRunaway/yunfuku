package com.aifubook.book.regimental;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class TeamOrdersActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public List<Fragment> listFragment = new ArrayList<>();
    public String[] title = new String[]{"待提货", "已提货"};

    private int selectTab = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_orders;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("团队订单");
        initViewPage();
        initData();
    }

    private void initData() {
        Map map = new HashMap();
        map.put("status", "20");
        map.put("chiefOrder", "true");
        map.put("chiefId", getIntent().getStringExtra("chiefId"));
        mPresenter.orderList(map);
    }

    private void initViewPage() {
        listFragment.add(new TeamOrdersFragment(0, getIntent().getStringExtra("chiefId")));
        listFragment.add(new TeamOrdersFragment(1, getIntent().getStringExtra("chiefId")));
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), listFragment));
        tabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);//设置初始选中页面
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    selectTab = 0;
                    Map map = new HashMap();
                    map.put("status", "20");
                    map.put("chiefOrder", "true");
                    map.put("chiefId", getIntent().getStringExtra("chiefId"));
                    mPresenter.orderList(map);
                } else {
                    selectTab = 1;
                    Map map = new HashMap();
                    map.put("status", "30");
                    map.put("chiefOrder", "true");
                    map.put("chiefId", getIntent().getStringExtra("chiefId"));
                    mPresenter.orderList(map);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void ChiefApplySuc(String DataBean) {

    }

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {

    }

    @Override
    public void ChiefMembersSuc(ChiefMembersBean DataBean) {

    }

    @Override
    public void ChiefMyChiefSuc(ChiefMyChiefBean DataBean) {

    }

    @Override
    public void GetDataSuc(List<SchoolBean> DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetListDataSuc(List<FindSchoolClassesBean> DataBean) {

    }

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {

    }

    @Override
    public void UploadImageSuc(String DataBean) {

    }

    @Override
    public void GetChiefOrderByCodeSuc(ChiefOrderByCodeBean DataBean) {

    }

    @Override
    public void SetFetchedSuc(String DataBean) {

    }

    @Override
    public void OrderListSuc(OrderListBean DataBean) {
        String[] titles;
        if (selectTab == 0) {
            titles = new String[]{"待提货(" + DataBean.getList().size() + ")", "已提货"};
        } else {
            titles = new String[]{"待提货", "已提货(" + DataBean.getList().size() + ")"};
        }
        tabLayout.setViewPager(viewPager, titles);
    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {

    }

    @Override
    public void CommissionApplySuc(String DataBean) {

    }

    @Override
    public void OrderCreateSuc(RechargeBean DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void GetDataSucs(ProductListBean DataBean) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

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