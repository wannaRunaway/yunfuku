package com.aifubook.book.regimental;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseFragment;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.bumptech.glide.Glide;
import com.jiarui.base.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.aifubook.book.api.ApiService.IMAGE;

@SuppressLint("ValidFragment")
public class TeamOrdersFragment extends BaseFragment<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<OrderListBean.ListBean> teamAdapter;
    List<OrderListBean.ListBean> teamList = new ArrayList<>();

    private int type = 0;
    private String chiefId;

    public TeamOrdersFragment(int type, String chiefId) {
        this.type = type;
        this.chiefId = chiefId;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_team_orders_fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    protected void initView() {
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Map map = new HashMap();
        if (type == 0) {
            map.put("status", "20");
        } else if (type == 1) {
            map.put("status", "30");
        }
        map.put("chiefOrder", "true");
        map.put("chiefId", chiefId);
        mPresenter.orderList(map);
    }

    private void initRecyclerView(List<OrderListBean.ListBean> teamList) {
        teamAdapter = new BaseRecyclerAdapter<OrderListBean.ListBean>(mActivity, teamList, R.layout.layout_team_orders_fragment_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, OrderListBean.ListBean item, int position, boolean isScrolling) {
                TextView tvScan = holder.getView(R.id.tv_scan);
                if (type == 0) {
                    holder.setText(R.id.tv_scan, "确认核销");
                    holder.getView(R.id.tv_scan).setBackgroundResource(R.drawable.shape_index_red_sale);
                } else {
                    holder.setText(R.id.tv_scan, "已提货");
                    holder.getView(R.id.tv_scan).setBackgroundResource(R.drawable.shape_bababa_16dp);
                }

                holder.getView(R.id.tv_scan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == 0) {
                            Map map = new HashMap();
                            map.put("code", item.getCode() + "");
                            map.put("id", item.getId() + "");
                            mPresenter.setFetched(map);
                        }
                    }
                });
                Glide.with(mActivity)
                        .load(IMAGE + item.getItems().get(0).getProductImage())
                        .into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_product_name, item.getItems().get(0).getProductName());
                holder.setText(R.id.tv_sp, item.getItems().get(0).getProductSubName());
                holder.setText(R.id.tv_orderNo, item.getId() + "");
                holder.setText(R.id.tv_time, TimeUtil.formatMsecConvertTime(item.getCreateTime()) + "");
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(teamAdapter);
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
        Toast.makeText(mActivity, "核销成功", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void OrderListSuc(OrderListBean DataBean) {
        teamList.clear();
        teamList.addAll(DataBean.getList());
        initRecyclerView(teamList);
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
        Toast.makeText(mActivity, Message + "", Toast.LENGTH_SHORT).show();
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
