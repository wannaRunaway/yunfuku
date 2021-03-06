package com.aifubook.book.home.shop;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.home.adapter.ShowDetailAdapter;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.GridItemDecoration;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.HEAD;

/**
 * Created by ListKer_Hlg on 2021/4/26 1:48
 * ??????????????????: ??????????????????????????????
 * ??????: 1425034784@qq.com
 */
public class ShopDetailsActivity extends BaseActivity<ShopDetailsPresenter> implements ShopDetailsView, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ShopDetailsActivity";

//    @BindView(R.id.refreshLayout)   // ????????????
//            SmartRefreshLayout mRefreshLayout;

    String shopId = "";
    @BindView(R.id.shopLogo)
    ImageView shopLogo;

    @BindView(R.id.shopName)
    TextView shopName;

    @BindView(R.id.shopAddress)
    TextView shopAddress;

//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;

    //    BaseRecyclerAdapter<ProductListBean.list> beanBaseRecyclerAdapter;
    List<ProductListBean.list> productListBeans = new ArrayList<>();

    private int pageNum = 1;
    private int pageSize = 10;

//    private boolean isRefresh = true;//????????????
//    private boolean isLoad = false;//????????????

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

    private MySwipeRefresh mMySwipeRefresh;
    private ShowDetailAdapter showDetailAdapter;

    List<String> downIn = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopdetails;
    }

    @Override
    public void initPresenter() {
        mPresenter = new ShopDetailsPresenter(this);
    }

    String inType = "0";
    private String categoryId = "";
    private String keyWord = "";
    private int zeroBuy = 0;
    private List<String> jsonList = new ArrayList<>();
    private Map<String,String> jsonMap = new HashMap<>();

    @Override
    public void initView() {
        setTitle("????????????");
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
                jsonMap.put(name,id);
                jsonList.add(name);

            }
            inType = getIntent().getExtras().getString("inType");
            categoryId = getIntent().getExtras().getString("categoryId");
            shopId = getIntent().getExtras().getString("shopId");
            keyWord = getIntent().getExtras().getString("keyWord");
            zeroBuy = getIntent().getExtras().getInt("zeroBuy");

        } catch (Exception e) {
            e.printStackTrace();
        }

        GetData();
        downIn.add("??????");
        downIn.add("????????????");
        downIn.add("????????????");
//        productList();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);//?????????????????????????????????

        mMySwipeRefresh = new MySwipeRefresh(root, null);
//        GridItemDecoration gridItemDecoration = new GridItemDecoration(this, 10, ContextUtil.getColor(R.color.view_color_F8F8F8));
        mMySwipeRefresh.getRecyclerView().setLayoutManager(layoutManager);
//        mMySwipeRefresh.getRecyclerView().addItemDecoration(gridItemDecoration);
        mMySwipeRefresh.setAdapter(showDetailAdapter = new ShowDetailAdapter());
        mMySwipeRefresh.setOnRefreshListener(ShopDetailsActivity.this);
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
                if (list != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("productId", "" + list.getId());
                    bundle.putInt("zeroBuy", zeroBuy);
                    startActivity(ProductDetailsActivity.class, bundle);
                }
            }
        });

    }

    /**
     * ??????: ListKer_Hlg
     * ??????:
     * ??????:
     * ??????: 2019/11/29
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
                LogUtil.e(TAG,"name="+name);
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

        pickerView.setPicker(jsonList);//????????????
        pickerView.show();


    }


    String Names = "??????";  //-soldCount

    OptionsPickerView pvCustomOptions;

    private <T> void pvCustomOptions() {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //?????????????????????????????????????????????

                switch (options1) {
                    case 0:
                        Names = "??????";
                        slectType = "";
                        break;
                    case 1:
                        slectType = "price";
                        Names = "????????????";
                        break;
                    case 2:
                        slectType = "-price";
                        Names = "????????????";
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

        pvCustomOptions.setPicker(downIn);//????????????
        pvCustomOptions.show();
    }

    private void GetData() {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", 0);
//        map.put("sort",1);
        if (inType.equals("1")) {
            map.put("categoryId", categoryId);
        }else{
            map.put("categoryId", categoryId);
        }

        if (inType.equals("0")) {
            map.put("shopId", shopId);
        }

        if (inType.equals("2")) {
            map.put("keyword", keyWord);

        }


        if (!StringUtils.isEmpty(slectType)) {
            map.put("sort", slectType);
        }

        map.put("shopId", shopId);
        map.put("zeroBuy", zeroBuy);
        if (1 == zeroBuy) {
            map.put("from", 4);
        }else{
            map.put("from",5);
        }
        map.put("pageNo", pageNum);
        map.put("pageSize", pageSize);
        mPresenter.productList(map);
    }

    // ???????????????????????????
    private void shopDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + shopId);
        mPresenter.shopDetail(map);
    }

    // ?????????????????????
    private void productList() {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", "" + shopId);
        mPresenter.productList(map);
    }


//    private void initRighRecycler() {
//        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ProductListBean.list>(this, productListBeans, R.layout.home_product_item) {
//            @Override
//            public void convert(BaseRecyclerHolder holder, ProductListBean.list item, int position, boolean isScrolling) {
//                holder.setText(R.id.mCount, "??????" + item.getSoldCount() + "");
//                holder.getView(R.id.bySelef).setVisibility(item.getShopId() == 0 ? View.VISIBLE : View.GONE);
//                holder.setText(R.id.mPrice, "???" + (Double.parseDouble(item.getDiscountPrice() + "") / 100) + "");
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


    /**
     * ??????????????????????????????
     *
     * @param DataBean
     */

    @Override
    public void GetDataSuc(ProductDetailBean DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

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
        shopName.setText(DataBean.getName());
        shopAddress.setText("??????: " + DataBean.getAddress());
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
