package com.aifubook.book.regimental;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
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

import static com.aifubook.book.api.ApiService.IMAGE;

public class CancelVerificationActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.et_scanCode)
    EditText et_scanCode;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<OrderListBean.ListBean> teamAdapter;
    List<OrderListBean.ListBean> teamList = new ArrayList<>();

    BaseRecyclerAdapter<ChiefOrderByCodeBean> teamAdapter2;
    List<ChiefOrderByCodeBean> teamList2 = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancel_verification;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("核销");
        Map map = new HashMap();
        map.put("status", "20");
        map.put("chiefOrder", "true");
        map.put("chiefId", getIntent().getStringExtra("chiefId"));
        mPresenter.orderList(map);
    }

    /**
     * 查询订单
     */
    @OnClick(R.id.tv_queryOrder)
    void tv_queryOrder() {
        if (StringUtils.isEmpty(et_scanCode.getText().toString())) {
            Toast.makeText(mContext, "请输入核销码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map map = new HashMap();
        map.put("code", et_scanCode.getText().toString());
        mPresenter.getChiefOrderByCode(map);
    }

    private void initRecyclerView(List<OrderListBean.ListBean> teamList) {
        teamAdapter = new BaseRecyclerAdapter<OrderListBean.ListBean>(mContext, teamList, R.layout.layout_team_orders_fragment_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, OrderListBean.ListBean item, int position, boolean isScrolling) {
                TextView tvScan = holder.getView(R.id.tv_scan);
                if (item.getStatus().equals("20")) {
                    holder.setText(R.id.tv_scan, "确认核销");
                    holder.getView(R.id.tv_scan).setBackgroundResource(R.drawable.shape_index_red_sale);
                } else if (item.getStatus().equals("30")) {
                    holder.setText(R.id.tv_scan, "已提货");
                    holder.getView(R.id.tv_scan).setBackgroundResource(R.drawable.shape_bababa_16dp);
                }

                holder.getView(R.id.tv_scan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getStatus().equals("20")) {
                            Map map = new HashMap();
                            map.put("code", item.getCode() + "");
                            map.put("id", item.getId() + "");
                            mPresenter.setFetched(map);
                        }
                    }
                });
                Glide.with(mContext)
                        .load(IMAGE + item.getItems().get(0).getProductImage())
                        .into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_product_name, item.getItems().get(0).getProductName());
                holder.setText(R.id.tv_sp, item.getItems().get(0).getProductSubName());
                holder.setText(R.id.tv_orderNo, item.getId() + "");
                holder.setText(R.id.tv_time, TimeUtil.formatMsecConvertTime(item.getCreateTime()) + "");
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(teamAdapter);
    }

    private void initRecyclerView2(List<ChiefOrderByCodeBean> teamList2) {
        teamAdapter2 = new BaseRecyclerAdapter<ChiefOrderByCodeBean>(mContext, teamList2, R.layout.layout_team_orders_fragment_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ChiefOrderByCodeBean item, int position, boolean isScrolling) {
                TextView tvScan = holder.getView(R.id.tv_scan);
                if (item.getStatus().equals("20")) {
                    holder.setText(R.id.tv_scan, "确认核销");
                    holder.getView(R.id.tv_scan).setBackgroundResource(R.drawable.shape_index_red_sale);
                } else if (item.getStatus().equals("30")) {
                    holder.setText(R.id.tv_scan, "已提货");
                    holder.getView(R.id.tv_scan).setBackgroundResource(R.drawable.shape_bababa_16dp);
                }
                holder.getView(R.id.tv_scan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getStatus().equals("20")) {
                            Map map = new HashMap();
                            map.put("code", item.getCode() + "");
                            map.put("id", item.getId() + "");
                            mPresenter.setFetched(map);
                        }
                    }
                });
                Glide.with(mContext)
                        .load(IMAGE + item.getItems().get(0).getProductImage())
                        .into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_product_name, item.getItems().get(0).getProductName());
                holder.setText(R.id.tv_sp, item.getItems().get(0).getProductSubName());
                holder.setText(R.id.tv_orderNo, item.getId() + "");
                holder.setText(R.id.tv_time, TimeUtil.formatMsecConvertTime(item.getCreateTime()) + "");
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(teamAdapter2);
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
        if (DataBean.getItems() != null) {
            List<ChiefOrderByCodeBean> list = new ArrayList<>();
            list.add(DataBean);
            teamList2.clear();
            teamList2.addAll(list);
            initRecyclerView2(teamList2);
        } else {
            Toast.makeText(mContext, "未查询到相关订单", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void SetFetchedSuc(String DataBean) {
        Toast.makeText(mContext, "核销成功", Toast.LENGTH_SHORT).show();
        initView();
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