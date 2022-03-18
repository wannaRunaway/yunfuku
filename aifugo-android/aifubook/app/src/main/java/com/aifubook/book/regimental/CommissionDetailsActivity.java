package com.aifubook.book.regimental;

import android.view.View;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CommissionDetailsActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<CommissionDetailsBean.ListBean> CommissionAdapter;
    List<CommissionDetailsBean.ListBean> CommissionList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_commission_details;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("佣金明细");

        Map map = new HashMap();
        map.put("scene", "1");
        mPresenter.recordList(map);
    }

    private void initRecyclerView(List<CommissionDetailsBean.ListBean> CommissionList) {
        CommissionAdapter = new BaseRecyclerAdapter<CommissionDetailsBean.ListBean>(mContext, CommissionList, R.layout.layout_commission_details_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, CommissionDetailsBean.ListBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_orderNo, "订单编号:" + item.getObjId());
                if (item.getScene().equals("1")) {
                    holder.setText(R.id.tv_status2, "订单收入");
                    holder.getView(R.id.tv_status).setVisibility(View.GONE);
                    holder.setText(R.id.tv_price, "+¥" + StringUtils.isNull((Double.parseDouble(item.getValue() + "") / 100) + ""));
                } else {
                    holder.setText(R.id.tv_status2, "提现");
                    holder.getView(R.id.tv_status).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_price, "-¥" + StringUtils.isNull((Double.parseDouble(item.getValue() + "") / 100) + ""));
                }
                holder.setText(R.id.tv_time, TimeUtil.formatMsecConvertTime(item.getCreateTime()) + "");

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(CommissionAdapter);
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

    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {
        CommissionList.clear();
        CommissionList.addAll(DataBean.getList());
        initRecyclerView(CommissionList);
    }

    @Override
    public void CommissionApplySuc(String DataBean) {

    }

    @Override
    public void OrderCreateSuc(RechargeBean DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
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
}