package com.aifubook.book.regimental;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class HeadCenterActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.iv_head)
    RoundedImageView iv_head;
    @BindView(R.id.tv_userName)
    TextView tv_userName;
    @BindView(R.id.tv_totalRevenue)
    TextView tv_totalRevenue;
    @BindView(R.id.tv_commission)
    TextView tv_commission;
    @BindView(R.id.tv_commissionSettled)
    TextView tv_commissionSettled;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String chiefId;
    private String balance;
    private String settlementCommission;//可提现佣金
    private String unSettlementCommission;//未结算佣金

    @Override
    public int getLayoutId() {
        return R.layout.activity_head_center;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("编审中心");
        Map map = new HashMap();
        mPresenter.chiefDetail(map);
        productList();
    }

    private void productList() {
        Map map = new HashMap();
        map.put("zeroBuy", 1);
        map.put("pageSize", 20);
        mPresenter.productList(map);
    }

    @OnClick({R.id.tv_tuandui, R.id.tv_detail, R.id.tv_scan, R.id.tv_order, R.id.tv_withdrawal})
    void tv_detail(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("chiefId", chiefId);
        switch (view.getId()) {
            case R.id.tv_tuandui://我的团队
                startActivity(MyTeamActivity.class,bundle);
                break;
            case R.id.tv_detail://佣金明细
                startActivity(CommissionDetailsActivity.class, bundle);
                break;
            case R.id.tv_scan://核销
                startActivity(CancelVerificationActivity.class, bundle);
                break;
            case R.id.tv_order://团队订单
                startActivity(TeamOrdersActivity.class, bundle);
                break;
            case R.id.tv_withdrawal://申请提现
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type",1);
                bundle1.putString("settlementCommission",canGet);
                bundle1.putString("balance",canGet);
                startActivity(ApplyWithdrawalsActivity.class, bundle1);
                break;
        }
    }

    @Override
    public void ChiefApplySuc(String DataBean) {

    }

    String canGet = "0.0";
    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {
        chiefId = DataBean.getId();
        settlementCommission = Double.parseDouble(DataBean.getMemberAccount().getSettlementCommission()) / 100+"";
        balance = Double.parseDouble(DataBean.getMemberAccount().getBalance()) / 100+"";
        unSettlementCommission = Double.parseDouble(DataBean.getMemberAccount().getUnSettlementCommission()) / 100+"";

        canGet = unSettlementCommission == null ? "0.00" : unSettlementCommission;

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head); //圆角
        Glide.with(mContext)
                .load(DataBean.getMember().getLogo())
                .apply(options)
                .into(iv_head);
        tv_userName.setText(DataBean.getName() == null ? "未设置用户昵称" : DataBean.getName());
//        tv_totalRevenue.setText(DataBean.getMemberAccount().getTotalCommission() == null ? "0.00" : Double.parseDouble(DataBean.getMemberAccount().getTotalCommission()) / 100+"");
//        tv_commission.setText(settlementCommission == null ? "0.00" : settlementCommission);
        tv_commissionSettled.setText(unSettlementCommission == null ? "0.00" : unSettlementCommission);
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
        mProductList = new ArrayList<>();
        mProductList.addAll(DataBean.getList());
        initAdapter();
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    List<ProductListBean.list> mProductList;
    BaseRecyclerAdapter<ProductListBean.list> beanBaseRecyclerAdapter;
    private void initAdapter() {
        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ProductListBean.list>(this, mProductList, R.layout.home_product_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ProductListBean.list item, int position, boolean isScrolling) {
                holder.getView(R.id.bySelef).setVisibility(item.getShopId()==0?View.VISIBLE:View.INVISIBLE);
                holder.setText(R.id.mCount,"销量"+item.getSoldCount()+"");
                holder.setText(R.id.mPrice,"￥"+0+"");
                holder.setText(R.id.mBookName,item.getName()+"");
                holder.setImageByUrl(R.id.mImageView, ApiService.IMAGE+""+item.getImage());
                holder.getView(R.id.inProductDetails).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("productId",""+item.getId());
                        startActivity(ProductDetailsActivity.class,bundle);
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(beanBaseRecyclerAdapter);
    }
}