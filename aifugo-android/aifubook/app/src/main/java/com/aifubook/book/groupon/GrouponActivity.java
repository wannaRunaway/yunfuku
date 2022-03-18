package com.aifubook.book.groupon;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.adapter.GrouponAdapter;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.bean.GrouponModel;
import com.aifubook.book.bean.GrouponPageBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.model.GrouponDataModel;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.product.ProductDetailsNewActivity;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.fresco.CommonImage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GrouponActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, @Nullable OnLoadMoreListener, OnCallBack {


    private static final int type_groupon_list = 0x11;

    @Override
    protected int setContentView() {
        return R.layout.activity_groupon;
    }


    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);

        findView();
        initData();

    }

    private RecyclerView recyclerView;
    private GrouponAdapter grouponAdapter;
    private SwipeRefreshLayout swipe_refresh_widget;
    private List<GrouponPageBean.GrouponBean> dataList = new ArrayList<>();
    private GrouponModel grouponModel;
    private CommonImage mImageView;

    private TextView textView_header;
    private ImageView leftImg;
    private void initData() {
//        mHeadView.setCentreTextView("拼团列表");
        textView_header = findViewById(R.id.textview);
        textView_header.setText("拼团列表");
        leftImg = findViewById(R.id.leftImg);
        leftImg.setOnClickListener(v -> finish());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipe_refresh_widget.setOnRefreshListener(this);
        swipe_refresh_widget.setColorSchemeResources(R.color.colorAccent);
        swipe_refresh_widget.setRefreshing(true);
        grouponAdapter = new GrouponAdapter();
        recyclerView.setAdapter(grouponAdapter);
        grouponAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        grouponAdapter.getLoadMoreModule().setAutoLoadMore(true);
        grouponAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
//        View topView = mInflater.inflate(R.layout.top_groupon, null);

        grouponAdapter.setList(dataList);
//        grouponAdapter.addHeaderView(topView);

        grouponModel = new GrouponModel(new GrouponDataModel());
        grouponModel.setOnCallBackListener(this);

        shopId = SharedPreferencesUtil.get(this, "SHOP_ID", "");

        grouponAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                GrouponPageBean.GrouponBean grouponBean = dataList.get(position);
                int id = grouponBean.getProductId();
                Bundle bundle = new Bundle();
                bundle.putString("productId", "" + id);
                startActivity(ProductDetailsNewActivity.class, bundle);
            }
        });



        getDataList();
    }

    private String shopId;
    private int pageNo = 1;
    private int pageSize = 10;

    private void getDataList() {

        Map<String, String> map = new HashMap<>();
        map.put("shopId", shopId);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        grouponModel.getGrouponList(map, type_groupon_list);

    }


    private void findView() {

        recyclerView = findViewById(R.id.recyclerView);
        swipe_refresh_widget = findViewById(R.id.swipe_refresh_widget);
        mImageView=findViewById(R.id.mImageView);

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        getDataList();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getDataList();
    }

    @Override
    public void onNext(Object result, int type) {
        swipe_refresh_widget.setRefreshing(false);
        if (type == type_groupon_list) {
            onDataResult(result);
        }

    }

    @Override
    public void onError(String error, int type) {
        showToast(error);
    }


    private void onDataResult(Object result) {
        GrouponPageBean pageBean = (GrouponPageBean) result;
        List<GrouponPageBean.GrouponBean> beanList = pageBean.getList();

        grouponAdapter.getLoadMoreModule().setEnableLoadMore(true);

        if (pageBean == null || pageBean.getList() == null) {
            return;
        }

        if (beanList == null) {
            if (pageNo > 1) {
                grouponAdapter.getLoadMoreModule().loadMoreEnd();
            }
            return;
        }

        if (pageNo == 1) {

            dataList.clear();
            grouponAdapter.setList(beanList);
        } else {
            grouponAdapter.addData(beanList);
        }
        if (beanList.size() < 10) {
            grouponAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            grouponAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (beanList.size() > 0) {
            dataList.addAll(beanList);
        }
        if(dataList.size()==0){
            mImageView.setVisibility(View.GONE);
            View emptyView = mInflater.inflate(R.layout.layout_empty, null);
            grouponAdapter.setEmptyView(emptyView);
        }
    }
}
