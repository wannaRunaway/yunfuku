package com.aifubook.book.mine.coupons;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.jiarui.base.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

public class CouponRedemptionActivity extends BaseActivity<CouponsPresenter> implements CouponsView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_nearby)
    RecyclerView recyclerView_nearby;
    @BindView(R.id.tv_morecoupons)
    TextView tv_morecoupons;
    @BindView(R.id.imageview_left)
    ImageView imageview_left;
    @BindView(R.id.header_textview)
    TextView header_textview;

    BaseRecyclerAdapter<MyCouponsBean> couponsAdapter;
    List<MyCouponsBean> couponsList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_redemption;
    }

    @Override
    public void initPresenter() {
        mPresenter = new CouponsPresenter(this);
    }

    @Override
    public void initView() {
//        setTitle("领券中心");
        header_textview.setText("领券中心");
        imageview_left.setOnClickListener(v -> finish());
        initData();
    }

    private void initData() {
        Map map = new HashMap();
        map.put("topK", "30");
        map.put("pageSize", "30");
        mPresenter.fullSiteReduceCoupons(map);
    }

    private void initRecyclerView(List<MyCouponsBean> couponsList) {
        couponsAdapter = new BaseRecyclerAdapter<>(mContext, couponsList, R.layout.item_coupon_shopcar) {
            @Override
            public void convert(BaseRecyclerHolder holder, MyCouponsBean item, int position, boolean isScrolling) {
//                MemberCouponsBean.ListBean.CouponBean items = item.get
//                String t = item.getType();
//                int type = Integer.parseInt(t);
//
//                if (0 == type) {
//                    holder.setText(R.id.tv_type, "店铺满减券");
//                    holder.setText(R.id.tv_dis, "满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
//                    holder.setText(R.id.tv_money,  "¥"+(Double.parseDouble(item.getReduceFee() + "") / 100));
//                    if (bean.getShop()!=null) {
//                        holder.setText(R.id.tv_info, bean.getShop().getName());
//                    }
//                } else if (1 == type) {
//                    holder.setText(R.id.tv_type, "无敌券");
//                    holder.setText(R.id.tv_dis, "无门槛红包");
//                    holder.setText(R.id.tv_money, "¥"+(Double.parseDouble(item.getReduceFee() + "") / 100));
//                    holder.setText(R.id.tv_info, "无门槛");
//                } else if (2 == type) {
//                    holder.setText(R.id.tv_type, "全站通用满减券");
//                    holder.setText(R.id.tv_dis, "满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
//                    holder.setText(R.id.tv_money, "¥"+(Double.parseDouble(item.getReduceFee() + "") / 100));
//                    holder.setText(R.id.tv_info, "全平台通用");
//                } else if (3 == type) {
//                    holder.setText(R.id.tv_type, "店铺折扣券");
//                    holder.setText(R.id.tv_money, (Double.parseDouble(item.getDiscountRate() + "") / 10) + "折");
//                    if (bean.getShop()!=null) {
//                        holder.setText(R.id.tv_info, bean.getShop().getName());
//                    }
//                }
//                holder.setText(R.id.tv_deadline, TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getStartTime())) + "--" + TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getEndTime())));
//                holder.getView(R.id.tv_immediateUse).setOnClickListener(v -> {
//                    TextView textView = holder.getView(R.id.tv_immediateUse);
//                    if (textView.getText().toString().equals("立即领取")) {
//                        holder.setText(R.id.tv_immediateUse, "已领取");
//                        holder.getView(R.id.tv_immediateUse).setBackgroundResource(R.drawable.shape_b4b4b4_10dp);
//                        Map map = new HashMap();
//                        map.put("couponId", item.getId() + "");
//                        mPresenter.couponReceive(map);
//                    }
//                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(couponsAdapter);
    }

    @Override
    public void ShopCouponsSuc(List<MyCouponsBean> DataBean) {
        couponsList.clear();
        couponsList.addAll(DataBean);
        initRecyclerView(couponsList);
    }

    @Override
    public void CouponReceiveSuc(String DataBean) {
        Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void MemberCouponsSuc(MemberCouponsBean DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}