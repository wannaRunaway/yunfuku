package com.aifubook.book.mine.coupons;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseFragment;
import com.aifubook.book.adapter.CouponAdapter;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.view.MySwipeRefresh;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.android.material.card.MaterialCardView;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.TimeUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class CouponsFragment extends BaseFragment<CouponsPresenter> implements CouponsView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.root)
     View root;

    BaseRecyclerAdapter<MemberCouponsBean.ListBean> couponsAdapter;
    List<MemberCouponsBean.ListBean> couponsList = new ArrayList<>();

    private MySwipeRefresh mMySwipeRefresh;

    private CouponAdapter couponAdapter;

    private int type;//0.未使用 1.已使用 2.已过期

    public CouponsFragment(int type) {
        super();
        this.type = type;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.coupons_fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter = new CouponsPresenter(this);
    }

    @Override
    protected void initView() {

        mMySwipeRefresh=new MySwipeRefresh(root,null);
        couponAdapter = new CouponAdapter(type);
//        couponAdapter.addHeaderView(topView);
        mMySwipeRefresh.setAdapter(couponAdapter);
        mMySwipeRefresh.setOnRefreshListener(this);
        mMySwipeRefresh.setRefreshing(false);
        couponAdapter.addChildClickViewIds(R.id.tv_get);
        couponAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(type==0) {
                MessageEvent event = new MessageEvent(MessageEvent.TAB_SELECT);
                event.setMsg_content("0");
                EventBusUtil.post(event);
                (getActivity()).finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

//    /**
//     * 领券中心
//     */
//    @OnClick(R.id.tv_receive)
//    void tv_receive() {
//        startActivity(CouponRedemptionActivity.class);
//    }

    private void initData() {
        Map map = new HashMap();
        map.put("status", type + "");//0 :领取未使用 1 :使用 2 :过期
//        map.put("shopId", "0");//查平台自营的前端传 shopId = 0
        mPresenter.memberCoupons(map);
    }

    @Override
    public void ShopCouponsSuc(List<MyCouponsBean> DataBean) {

    }

    @Override
    public void CouponReceiveSuc(String DataBean) {

    }

    @Override
    public void MemberCouponsSuc(MemberCouponsBean DataBean) {
        mMySwipeRefresh.setRefreshing(false);
        List<MemberCouponsBean.ListBean> beanList =  DataBean.getList();
        couponsList.clear();
        if(beanList!=null && beanList.size()>0) {
            couponsList.addAll(beanList);
        }
        couponAdapter.setList(couponsList);

    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mActivity, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onRefresh() {
        initData();
    }
}
