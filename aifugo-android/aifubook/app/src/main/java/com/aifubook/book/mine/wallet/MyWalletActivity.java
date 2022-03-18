package com.aifubook.book.mine.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.mine.member.MemberInfoPresenter;
import com.aifubook.book.mine.member.MemberInfoView;
import com.aifubook.book.mine.setting.PriviteActivity;
import com.aifubook.book.regimental.ApplyWithdrawalsActivity;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class MyWalletActivity extends BaseActivity<MemberInfoPresenter> implements MemberInfoView {

    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_rechargeRecord)
    TextView tv_rechargeRecord;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tv_expenseCalendar)
    TextView tv_expenseCalendar;
    @BindView(R.id.line2)
    View line2;


    BaseRecyclerAdapter<CommissionDetailsBean.ListBean> walleAdapter;
    List<CommissionDetailsBean.ListBean> walleList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MemberInfoPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("我的钱包");
        Map map = new HashMap();
        mPresenter.getCanUsedBalance(map);
        initData();
    }

    private int index = 2;//2.充值记录 4.余额消费

    @OnClick({R.id.ll_rechargeRecord, R.id.ll_expenseCalendar,R.id.tv_result})
    void record(View view) {
        switch (view.getId()) {
            case R.id.tv_result:
                Bundle bundle = new Bundle();
                bundle.putString("fig","https://api.aifubook.com/bookstatic/html/balance-rule.html");
                bundle.putString("title","提现规则");
                startActivity(PriviteActivity.class,bundle);

                break;
            case R.id.ll_rechargeRecord:
                index = 2;
                tv_rechargeRecord.setTextColor(getResources().getColor(R.color.view_color_FC5739));
                tv_expenseCalendar.setTextColor(getResources().getColor(R.color.gray));
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_expenseCalendar:
                index = 4;
                tv_rechargeRecord.setTextColor(getResources().getColor(R.color.gray));
                tv_expenseCalendar.setTextColor(getResources().getColor(R.color.view_color_FC5739));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                break;
        }
        Map map = new HashMap();
        map.put("scene", index + "");
        mPresenter.recordList(map);
    }

    @OnClick(R.id.tv_recharge)
    void tv_recharge() {
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 0);
        bundle1.putString("balance", Double.parseDouble(tv_balances) / 100+"");
        startActivity(ApplyWithdrawalsActivity.class, bundle1);
    }

    private void initData() {
        Map map = new HashMap();
        map.put("scene", index + "");
        mPresenter.recordList(map);
    }

    private void initRecyclerView(List<CommissionDetailsBean.ListBean> walleList) {
        walleAdapter = new BaseRecyclerAdapter<CommissionDetailsBean.ListBean>(mContext, walleList, R.layout.my_wallet_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, CommissionDetailsBean.ListBean item, int position, boolean isScrolling) {
                if (item.getScene().equals("2")) {
                    holder.setText(R.id.tv_title, "充值成功");
                    holder.setText(R.id.tv_price, "+¥" + StringUtils.isNull((Double.parseDouble(item.getValue() + "") / 100) + ""));
                } else {
                    holder.setText(R.id.tv_title, "订单消费："+item.getObjId());
                    holder.setText(R.id.tv_price, "-¥" + StringUtils.isNull((Double.parseDouble(item.getValue() + "") / 100) + ""));
                }
                holder.setText(R.id.tv_balance, "余额：¥" + StringUtils.isNull((Double.parseDouble(item.getBalance() + "") / 100) + ""));
                holder.setText(R.id.tv_time, TimeUtil.formatMsecConvertTime(item.getCreateTime()) + "");
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(walleAdapter);
    }

    @Override
    public void GetSendImageSuc(String notDataBean) {

    }

    @Override
    public void GetSendImageFail(String Message) {

    }

    @Override
    public void GetShopSuc(ShopBean DataBean) {

    }

    @Override
    public void GetShopFail(String Message) {

    }

    @Override
    public void MemberInfoSuc(MemberInfoBean DataBean) {

    }

    @Override
    public void appliedShop(ShopBean DataBean) {

    }

    @Override
    public void UpdateMemberInfoSuc(String DataBean) {

    }

    String tv_balances = "0.0";
    @Override
    public void GetCanUsedBalanceSuc(String DataBean) {
        tv_balances = DataBean;
        tv_balance.setText(Double.parseDouble(DataBean) / 100 + "");
    }

    @Override
    public void GetAccountInfoSuc(GetAccountInfoBean DataBean) {

    }

    @Override
    public void HasPayPasswordSuc(String DataBean) {

    }

    @Override
    public void SetPayPasswordSuc(String DataBean) {

    }

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {

    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {
        walleList.clear();
        walleList.addAll(DataBean.getList());
        initRecyclerView(walleList);
    }

    @Override
    public void GetHomePageFail(String Message) {

    }

    @Override
    public void GetDataSuc(ProductListBean DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void getOrderCountResult(OrderCountVO orderCountVO) {

    }

    @Override
    public void getOrderCountError(String msg) {

    }

    @Override
    public void getWeChatToken(String token) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}