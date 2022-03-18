package com.aifubook.book.regimental;


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
import com.jiarui.base.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MyTeamActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseRecyclerAdapter<ChiefMembersBean.ListBean> CommissionAdapter;
    List<ChiefMembersBean.ListBean> CommissionList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("我的团队");
        Map map = new HashMap();
        map.put("chiefMemberId", getIntent().getStringExtra(""));
        mPresenter.chiefMembers(map);
    }

    private void initRecyclerView(List<ChiefMembersBean.ListBean> CommissionList) {
        CommissionAdapter = new BaseRecyclerAdapter<ChiefMembersBean.ListBean>(mContext, CommissionList, R.layout.layout_my_team_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ChiefMembersBean.ListBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_name, item.getMember().getNickname());
                holder.setText(R.id.tv_schoolClass, item.getMember().getSchoolName() == null ? "暂无学校名称" : item.getMember().getSchoolName() + "  " + item.getMember().getClassName() == null ? "暂无班级名称" : item.getMember().getClassName());
                holder.setText(R.id.tv_time, TimeUtil.formatMsecConvertTime(item.getMember().getCreateTime()) + "");
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
        CommissionList.clear();
        CommissionList.addAll(DataBean.getList());
        initRecyclerView(CommissionList);
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

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}