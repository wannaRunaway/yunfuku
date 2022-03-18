package com.aifubook.book.regimental;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.adapter.TeacherAreaAdapter;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.home.search.SearchActivity;
import com.aifubook.book.order.ActivityAllOrdersList;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.model.TeacherAreaModel;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.shop.ShopCartActivity;
import com.aifubook.book.type.TypeModel;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jiarui.base.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 教师专区
 */
public class TeacherAreaActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnCallBack, @Nullable OnLoadMoreListener {

    private MySwipeRefresh mMySwipeRefresh;
    private static final int product_list = 0x11;
    private static final int type_add_cart = 0x12;


    @Override
    protected int setContentView() {

        return R.layout.activity_teacher_area;
    }

    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);
//        mHeadView.setCentreTextView("教师专区");
        findView();
        initData();

    }


    private TeacherAreaModel model;
    private String shopId;

    private void initData() {

        shopId = SharedPreferencesUtil.get(mActivity, "SHOP_ID", "");

        TypeModel typeModel = new TypeModel();
        model = new TeacherAreaModel(typeModel);
        model.setOnCallBackListener(this);
        getProductLists();

        teacherAreaAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                ProductListBean.list list = mProductLists.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("productId", "" + list.getId());
                bundle.putInt("zeroBuy", 1);
                startActivity(ProductDetailsActivity.class, bundle);


            }
        });
        teacherAreaAdapter.addChildClickViewIds(R.id.iv_addCart);
        teacherAreaAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                showLoadingDialog();
                ProductListBean.list list = mProductLists.get(position);
                Map<String, String> map = new HashMap<>();
                map.put("productId", "" + list.getId());
                map.put("count", "" + 1);
                map.put("zeroBuy", 1 + "");
                model.addCart(map, type_add_cart);

            }
        });


    }

    private int pageNo = 1;

    private void getProductLists() {
        Map<String, Object> map = new HashMap<>();
        map.put("from", 4);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", 10 + "");
        map.put("shopId", shopId);
        map.put("zeroBuy", 1 + "");
        model.getProductList(map, product_list);
    }

    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type == product_list) {
            onListResult(result);
        } else if (type == type_add_cart) {
            ToastUtil.showToast("加入购物车成功", false);

        }
    }


    List<ProductListBean.list> mProductLists = new ArrayList<>();

    private void onListResult(Object result) {
        ProductListBean listBean = (ProductListBean) result;
        mMySwipeRefresh.setRefreshing(false);
        teacherAreaAdapter.getLoadMoreModule().setEnableLoadMore(true);

        if (listBean == null || listBean.getList() == null) {
            return;
        }

        List<ProductListBean.list> dataList = listBean.getList();

        if (dataList == null) {
            if (pageNo > 1) {
                teacherAreaAdapter.getLoadMoreModule().loadMoreEnd();
            }
            return;
        }

        if (pageNo == 1) {
            mProductLists.clear();
            teacherAreaAdapter.setList(dataList);
        } else {
            teacherAreaAdapter.addData(dataList);
        }
        if (dataList.size() < 10) {
            teacherAreaAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            teacherAreaAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (dataList.size() > 0) {
            mProductLists.addAll(dataList);
        }

    }

    @Override
    public void onError(String error, int type) {
        ToastUtil.showToast(error, false);
    }

    private TeacherAreaAdapter teacherAreaAdapter;
    List<String> list = new ArrayList<>();

    private void findView() {

        View root = findViewById(R.id.root);
        View topView = mInflater.inflate(R.layout.top_teacher_area, null);
        TextView tv_search = topView.findViewById(R.id.tv_search);
        View ll_category = topView.findViewById(R.id.ll_category);
        View ll_cart = topView.findViewById(R.id.ll_cart);
        View ll_order = topView.findViewById(R.id.ll_order);

        ll_category.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        tv_search.setOnClickListener(this);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);//第二个参数为网格的列数
        mMySwipeRefresh = new MySwipeRefresh(root, layoutManager);
        teacherAreaAdapter = new TeacherAreaAdapter();
        mMySwipeRefresh.setAdapter(teacherAreaAdapter);
        mMySwipeRefresh.setOnRefreshListener(this);

        teacherAreaAdapter.addHeaderView(topView);
        teacherAreaAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        teacherAreaAdapter.getLoadMoreModule().setAutoLoadMore(true);
        teacherAreaAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_category:
                Bundle bundle = new Bundle();
                bundle.putInt("zeroBuy", 1);
                startActivity(ProductCategoryActivity.class, bundle);
                break;
            case R.id.ll_cart:
                startActivity(ShopCartActivity.class);
                break;
            case R.id.ll_order:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 0);
                startActivity(ActivityAllOrdersList.class, bundle1);
                break;
            case R.id.tv_search:
                Bundle bundle2 = new Bundle();
                bundle2.putString("shopId", shopId);
                bundle2.putInt("zeroBuy", 1);
                startActivity(SearchActivity.class, bundle2);
                break;
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        getProductLists();
    }


    @Override
    public void onLoadMore() {
        pageNo++;
        getProductLists();
    }
}
