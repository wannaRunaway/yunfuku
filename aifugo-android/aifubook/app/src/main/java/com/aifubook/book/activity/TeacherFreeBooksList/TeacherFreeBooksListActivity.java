package com.aifubook.book.activity.TeacherFreeBooksList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.teacherfreeproductsdetails.TeacherFreeBookProductsDetailsActivity;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeacherFreeBooksListActivity extends BaseActivity<CheckDetailPresent> implements CheckDetailView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ShopDetailsActivity";

//    @BindView(R.id.refreshLayout)   // 刷新控件
//            SmartRefreshLayout mRefreshLayout;

    String shopId = "";

//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;

    //    BaseRecyclerAdapter<ProductListBean.list> beanBaseRecyclerAdapter;
    List<ProductListBean.list> productListBeans = new ArrayList<>();

    private int pageNum = 1;
    private int pageSize = 20;

//    private boolean isRefresh = true;//是否刷新
//    private boolean isLoad = false;//是否加载

    @BindView(R.id.SelectIn)
    TextView SelectIn;

    @BindView(R.id.SendIn)
    TextView SendIn;

    @BindView(R.id.root)
    View root;

    @BindView(R.id.ll_grade)
    LinearLayout ll_grade;

    @BindView(R.id.tv_grade)
    TextView tv_grade;

//    @BindView(R.id.smartrefresh)
//    SmartRefreshLayout smartRefreshLayout;
//
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;

    private MySwipeRefresh mMySwipeRefresh;
    private TeacherFreeBookListAdapter showDetailAdapter;

    List<String> downIn = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_teacher_getfreebooks_list;
    }

    @Override
    public void initPresenter() {
        mPresenter = new CheckDetailPresent(this);
    }

    String inType = "0";
    private String categoryId = "";
    private List<String> jsonList = new ArrayList<>();
    private Map<String, String> jsonMap = new HashMap<>();

    @Override
    public void initView() {
        setTitle("商品列表");
//        initRighRecycler();

        String gradeString = SharedPreferencesUtil.get(this, "gradeList", "");
        LogUtil.e(TAG, "gradeString=" + gradeString);
        try {
            jsonList.clear();
            JSONArray jsonArray = new JSONArray(gradeString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.optJSONObject(i);
                String name = jsonObj.optString("name");
                String id = jsonObj.optString("id");
                jsonMap.put(name, id);
                jsonList.add(name);

            }
            inType = getIntent().getExtras().getString("inType");
            categoryId = getIntent().getExtras().getString("categoryId");
            shopId = getIntent().getExtras().getString("shopId");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        GetData();
        downIn.add("综合");
        downIn.add("价格升序");
        downIn.add("价格降序");
//        productList();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);//第二个参数为网格的列数

        mMySwipeRefresh = new MySwipeRefresh(root, null);
//        GridItemDecoration gridItemDecoration = new GridItemDecoration(this, 10, ContextUtil.getColor(R.color.view_color_F8F8F8));
        mMySwipeRefresh.getRecyclerView().setLayoutManager(layoutManager);
//        mMySwipeRefresh.getRecyclerView().addItemDecoration(gridItemDecoration);
        mMySwipeRefresh.setAdapter(showDetailAdapter = new TeacherFreeBookListAdapter());
        mMySwipeRefresh.setOnRefreshListener(this);
        showDetailAdapter.getLoadMoreModule().setAutoLoadMore(true);
        showDetailAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        showDetailAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                GetData();
            }
        });
        showDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                ProductListBean.list list = productListBeans.get(position);
                if (list.getYesBuy() == 1) {//不可领取 分享
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "这里有个APP买教辅很方便，你试用下吧！https://www.aifubook.com/download.html");
                    startActivity(shareIntent);
                } else if (list.getYesBuy() == 0) {//可以领取
                    if (list != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("productId", "" + list.getId());
                        bundle.putInt("zeroBuy", 1);
                        bundle.putSerializable("productDetails", (Serializable) list);
                        startActivity(TeacherFreeBookProductsDetailsActivity.class, bundle);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetData();
    }

    /**
     * 作者: ListKer_Hlg
     * 用途:
     * 简述:
     * 时间: 2019/11/29
     */

    @OnClick({R.id.SelectIn, R.id.SendIn, R.id.ll_grade})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.SelectIn:
                pvCustomOptions();
                break;
            case R.id.SendIn:
                slectTrue = !slectTrue;
                setIn(2);
                break;
            case R.id.ll_grade:
                showGradeList();
                break;
        }
    }

    boolean slectTrue = true;

    String slectType = "";  //-soldCount

    private void setIn(int selectType) {
        if (selectType == 1) {
            Drawable dra = getResources().getDrawable(R.mipmap.up);
            dra.setBounds(0, 0, dra.getIntrinsicWidth(), dra.getIntrinsicHeight());
            ;
            SendIn.setCompoundDrawables(null, null, dra, null);

            Drawable dras = getResources().getDrawable(R.mipmap.up_red);
            dras.setBounds(0, 0, dras.getIntrinsicWidth(), dras.getIntrinsicHeight());
            ;
            SelectIn.setCompoundDrawables(null, null, dras, null);

            SelectIn.setText(Names);
            SelectIn.setTextColor(getResources().getColor(R.color.view_color_FC5739));
            SendIn.setTextColor(getResources().getColor(R.color.black_three));
        } else {

            Drawable dras = getResources().getDrawable(R.mipmap.up);
            dras.setBounds(0, 0, dras.getIntrinsicWidth(), dras.getIntrinsicHeight());
            ;
            SelectIn.setCompoundDrawables(null, null, dras, null);


            if (!slectTrue) {
                slectType = "-soldCount";

                Drawable dra = getResources().getDrawable(R.mipmap.up_red);
                dra.setBounds(0, 0, dra.getIntrinsicWidth(), dra.getIntrinsicHeight());
                ;
                SendIn.setCompoundDrawables(null, null, dra, null);

            } else {
                slectType = "";
                Drawable dra = getResources().getDrawable(R.mipmap.on_red);
                dra.setBounds(0, 0, dra.getIntrinsicWidth(), dra.getIntrinsicHeight());
                ;
                SendIn.setCompoundDrawables(null, null, dra, null);
            }
            SelectIn.setTextColor(getResources().getColor(R.color.black_three));
            SendIn.setTextColor(getResources().getColor(R.color.view_color_FC5739));
        }
//        isRefresh = true;
        pageNum = 1;
        GetData();
    }

    private OptionsPickerView pickerView;

    private <T> void showGradeList() {

        pickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                String name = jsonList.get(options1);
                LogUtil.e(TAG, "name=" + name);
                tv_grade.setText(name);
                categoryId = jsonMap.get(name);
                GetData();

            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                final TextView ivCancel = v.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pickerView.returnData();
                        pickerView.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerView.dismiss();
                    }
                });
            }
        })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();

        pickerView.setPicker(jsonList);//添加数据
        pickerView.show();


    }


    String Names = "综合";  //-soldCount

    OptionsPickerView pvCustomOptions;

    private <T> void pvCustomOptions() {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //返回的分别是三个级别的选中位置

                switch (options1) {
                    case 0:
                        Names = "综合";
                        slectType = "";
                        break;
                    case 1:
                        slectType = "price";
                        Names = "价格升序";
                        break;
                    case 2:
                        slectType = "-price";
                        Names = "价格降序";
                        break;
                }

                setIn(1);

            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        final TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();

        pvCustomOptions.setPicker(downIn);//添加数据
        pvCustomOptions.show();
    }

    private void GetData() {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", 0);
        map.put("categoryId", categoryId);
        if (!StringUtils.isEmpty(slectType)) {
            map.put("sort", slectType);
        }
        map.put("shopId", shopId);
//        map.put("zeroBuy", 1);
        map.put("zeroType", 1);
        map.put("status", 2);
        map.put("from", 4);
        map.put("pageNo", pageNum);
        map.put("pageSize", pageSize);
        mPresenter.productList(map);
    }

    // 获取店铺详情的接口
    private void shopDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + shopId);
        mPresenter.shopDetail(map);
    }

    // 发送验证码接口
    private void productList() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", "" + shopId);
        mPresenter.productList(map);
    }


//    private void initRighRecycler() {
//        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ProductListBean.list>(this, productListBeans, R.layout.home_product_item) {
//            @Override
//            public void convert(BaseRecyclerHolder holder, ProductListBean.list item, int position, boolean isScrolling) {
//                holder.setText(R.id.mCount, "销量" + item.getSoldCount() + "");
//                holder.getView(R.id.bySelef).setVisibility(item.getShopId() == 0 ? View.VISIBLE : View.GONE);
//                holder.setText(R.id.mPrice, "￥" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
//                holder.setText(R.id.mBookName, item.getName() + "");
//                holder.setImageByUrl(R.id.mImageView, ApiService.IMAGE + "" + item.getImage());
//                holder.getView(R.id.inProductDetails).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("productId", "" + item.getId());
//                        startActivity(ProductDetailsActivity.class, bundle);
//                    }
//                });
//            }
//        };
////        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
////        recyclerView.setAdapter(beanBaseRecyclerAdapter);
//    }

//    @BindView(R.id.shopLogo)
//    ImageView shopLogo;
//
//    @BindView(R.id.shopName)
//    TextView shopName;
//
//    @BindView(R.id.shopAddress)
//    TextView shopAddress;

    @Override
    public void GetShopSuc(ShopBean DataBean) {
//        shopName.setText(DataBean.getName());
//        shopAddress.setText("地址: " + DataBean.getAddress());
    }

    @Override
    public void GetShopFail(String Message) {

    }

    @Override
    public void GetListDataSuc(ProductListBean DataBean) {
        mMySwipeRefresh.setRefreshing(false);
        showDetailAdapter.getLoadMoreModule().setEnableLoadMore(true);

        List<ProductListBean.list> dataList = DataBean.getList();
        if (dataList == null) {
            return;
        }

        if (pageNum == 1) {
            productListBeans.clear();
            showDetailAdapter.setList(dataList);
        } else {
            showDetailAdapter.addData(dataList);
        }
        if (dataList.size() < 10) {
            showDetailAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            showDetailAdapter.getLoadMoreModule().loadMoreComplete();
        }

        if (dataList.size() > 0) {
            productListBeans.addAll(dataList);
        }


    }

    @Override
    public void GetListDataFail(String Message) {
        showDetailAdapter.getLoadMoreModule().loadMoreComplete();
        mMySwipeRefresh.setRefreshing(false);
//        if (list.size() == 0) {
//            isShowLinearLayout.setVisibility(View.VISIBLE);
//        } else {
//            isShowLinearLayout.setVisibility(View.GONE);
//        }
//        if (isRefresh || isLoad)
//            mHandler.sendEmptyMessage(2);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        mMySwipeRefresh.setRefreshing(true);
        GetData();
    }
}