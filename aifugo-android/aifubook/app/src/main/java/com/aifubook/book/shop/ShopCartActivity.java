package com.aifubook.book.shop;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.adapter.ShopCartAdapter;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.base.CouponBean;
import com.aifubook.book.bean.CarInBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.dialog.CarInputNumDialog;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.fragment.Addcart;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.model.ShopCarModel;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.productcar.ConfirmOrderActivity;
import com.aifubook.book.productcar.cart.CarItemsBean;
import com.aifubook.book.productcar.cart.CartBean;
import com.aifubook.book.productcar.cart.CartFragmentModel;
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.aifubook.book.api.ApiService.IMAGE;

public class ShopCartActivity extends BaseActivity implements OnCallBack, SwipeRefreshLayout.OnRefreshListener, @Nullable OnLoadMoreListener, Addcart {

    private static final String TAG = "ShopCartActivity";

    private static final int shop_car_list = 0x11;
    private static final int type_getCoupons = 0x12;
    private static final int type_change_count = 0x13;
    private static final int type_add_cart = 0x14;
    private static final int type_remove_cart = 0x15;
    private static final int type_use_coupon = 0x16;
    private static final int product_list = 0x17;

    private List<CarItemsBean> selectedCartItemList = new ArrayList<>();//已勾选购物车
    private List<CartBean.ShopCarsBean> cartList = new ArrayList<>();// 同一店铺条目

    private int countNum;//结算 条目数量
    private BigDecimal totalMoney = BigDecimal.ZERO;
    private boolean isSelectAll = false;//是否全选

    @Override
    protected int setContentView() {
        return R.layout.activity_shopcart;
    }

    private String shopId;

    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);
        shopId = SharedPreferencesUtil.get(mActivity, "SHOP_ID", "");

        findView();
        initData();

    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getMsg_type() == MessageEvent.PAY_SUCCESS) {
            ShopCartActivity.this.finish();
        }

    }

    //    private RecyclerView recyclerView;
    private TextView tv_reduce, tv_total_amount, tv_submit;
    private LinearLayout ll_shops, ll_submit;
    private MySwipeRefresh mMySwipeRefresh;
    private ShopCartAdapter teacherAreaAdapter;
    private TextView header_textview;
    private ImageView imageview_left;

    private void findView() {
//        mHeadView.setCentreTextView("购物车");
//        recyclerView = findViewById(R.id.recyclerView);
        header_textview = findViewById(R.id.header_textview);
        imageview_left = findViewById(R.id.imageview_left);
        header_textview.setText("购物车");
        imageview_left.setOnClickListener(v -> finish());
        tv_reduce = findViewById(R.id.tv_reduce);
        tv_total_amount = findViewById(R.id.tv_total_amount);
        tv_submit = findViewById(R.id.tv_submit);
        ll_submit = findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(v -> submitCart());
        View root = findViewById(R.id.root);
        View topView = mInflater.inflate(R.layout.topview_shop_cart, null);
        ll_shops = topView.findViewById(R.id.ll_shops);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);//第二个参数为网格的列数
        mMySwipeRefresh = new MySwipeRefresh(root, layoutManager);
        teacherAreaAdapter = new ShopCartAdapter(this);
        mMySwipeRefresh.setAdapter(teacherAreaAdapter);
        mMySwipeRefresh.setOnRefreshListener(this);

        teacherAreaAdapter.addHeaderView(topView);
        teacherAreaAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        teacherAreaAdapter.getLoadMoreModule().setAutoLoadMore(true);
        teacherAreaAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        View bottomView = new View(ShopCartActivity.this);
        bottomView.setMinimumHeight(50);
        teacherAreaAdapter.addFooterView(bottomView);

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
//        getRecommendList();
        getCarList();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getRecommendList();
    }

    private ShopCarModel model;

    private void initData() {

        model = new ShopCarModel(new CartFragmentModel());
        model.setOnCallBackListener(this);
        showLoadingDialog();

        getCarList();

        teacherAreaAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                ProductListBean.list list = mProductLists.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("productId", "" + list.getId());
//                bundle.putInt("zeroBuy", 1);
                startActivity(ProductDetailsActivity.class, bundle);


            }
        });

    }

    private int pageNo=1;

    private void getRecommendList(){
        Map<String, Object> map = new HashMap<>();
        map.put("from", 3);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", 10 + "");
        map.put("shopId", shopId);
        map.put("recommend", 1 + "");
        model.getProductList(map, product_list);
    }

    public void getCarList() {
        Map map = new HashMap();
        model.carGet(map, shop_car_list);
    }

    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type == shop_car_list) {
            getRecommendList();
            onCarListResult(result);
        } else if (type == type_getCoupons) {
            onCouponsList(result);
        } else if (type == type_change_count) {
            onChangeCount(result);
        } else if (type == type_add_cart) {
            onAddCartResult(result);
        } else if (type == type_use_coupon) {
            useCouponResult(result);
        } else if (type == type_remove_cart) {
            getCarList();
        } else if(type == product_list){
            onListResult(result);
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

    private void useCouponResult(Object result) {
        ToastUtil.showToast("领取成功", false);

    }

    private void onAddCartResult(Object result) {

        closeLoadingDialog();
    }

    private void onChangeCount(Object result) {
        getCarList();
    }

    //购物车结算
    private void submitCart() {


        if (selectedCartItemList.isEmpty()) {
            ToastUtil.showToast("结算不能为零", false);
            return;
        }


        List<CarInBean> carInBeans = new ArrayList<>();
        if (!selectedCartItemList.isEmpty()) {
            for (int i = 0; i < cartList.size(); i++) {
                CarInBean carInBean = new CarInBean();
                List<CarInBean.productListBean> productListBeans = new ArrayList<>();
                for (int j = 0; j < cartList.get(i).getCarItems().size(); j++) {
                    if (cartList.get(i).getCarItems().get(j).isSelect()) {
                        CarInBean.productListBean productListBean = new CarInBean.productListBean();
                        productListBean.setId(cartList.get(i).getCarItems().get(j).getProductId());
                        productListBean.setDiscountPrice(cartList.get(i).getCarItems().get(j).getProduct().getDiscountPrice());
                        productListBean.setImage(cartList.get(i).getCarItems().get(j).getProduct().getImage());
                        productListBean.setName(cartList.get(i).getCarItems().get(j).getProduct().getName());
                        productListBean.setPrice(Integer.valueOf(cartList.get(i).getCarItems().get(j).getProduct().getPrice() + ""));
                        productListBean.setShopId(cartList.get(i).getCarItems().get(j).getProduct().getShopId());
                        productListBean.setCount(cartList.get(i).getCarItems().get(j).getCount());
                        productListBean.setChiefId(cartList.get(i).getCarItems().get(j).getChiefId());
                        productListBean.setStock(cartList.get(i).getCarItems().get(j).getProduct().getStock());
                        productListBean.setLimit(cartList.get(i).getCarItems().get(j).getProduct().getLimit());
                        productListBean.setZeroBuy(cartList.get(i).getCarItems().get(j).getZeroBuy());
                        productListBeans.add(productListBean);
                        carInBean.setName(cartList.get(i).getShop().getName());
                        carInBean.setProductListBeans(productListBeans);
                    }
                }


                if (carInBean.getProductListBeans() != null) {

                    carInBeans.add(carInBean);
                }
            }

        }
        if (carInBeans.size() > 1) {
            ToastUtil.showToast("目前不支持跨店铺结算", false);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", (Serializable) carInBeans);
        startActivity(ConfirmOrderActivity.class, bundle);
//        startActivity(TrueOrderActivity.class, bundle);
//        mPresenter.submitCarts(StringUtil.listToString(cartIds));

    }


    private void onCouponsList(Object result) {
        List<CouponBean> DataBean = (List<CouponBean>) result;
        if (DataBean != null && DataBean.size() > 0) {

            ShowReportDialog showReportDialog = new ShowReportDialog();
            showReportDialog.showCouponDialog(ShopCartActivity.this, DataBean);
            showReportDialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                @Override
                public void onConfirm(Object bean) {

                    CouponBean item = (CouponBean) bean;
                    Map map = new HashMap();
                    map.put("couponId", item.getId() + "");
                    model.couponReceive(map, type_use_coupon);
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            ToastUtil.showToast("没有可领取的优惠券", false);
        }


    }

    private void onCarListResult(Object result) {

        CartBean DataBean = (CartBean) result;
        cartList.clear();
        cartList.addAll(DataBean.getShopCars());

//        ShopAdapter.notifyDataSetChanged();

        initShopList(cartList);

        updateAccountMoney();

    }

    @Override
    public void onError(String error, int type) {
        closeLoadingDialog();
        ToastUtil.showToast(error, false);
    }

    private void initShopList(List<CartBean.ShopCarsBean> cartList) {
        ll_shops.removeAllViews();

        for (int i = 0; i < cartList.size(); i++) {
            CartBean.ShopCarsBean item = cartList.get(i);
            View itemView = mInflater.inflate(R.layout.layout_cart_item, null);
            TextView tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            ImageView cbShopAll = itemView.findViewById(R.id.cb_shop_all);
            TextView tv_get_coupon = itemView.findViewById(R.id.tv_get_coupon);
            LinearLayout ll_products = itemView.findViewById(R.id.ll_products);
            initProductList(ll_products, item.getCarItems(), i);


            int shopId = item.getShop().getId();

            cbShopAll.setImageResource(item.isSelect() ? R.mipmap.icon_checked : R.mipmap.icon_unchecked);
            tv_shop_name.setText(item.getShop().getName());

            tv_get_coupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Map map = new HashMap();
                    map.put("shopId", shopId + "");
                    model.getShopReduceCoupons(map, type_getCoupons);

                }
            });

            cbShopAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int shopId = item.getShop().getId();
                    for (CartBean.ShopCarsBean item : cartList) {
                        if (item.isSelect()) {
                            if (shopId != item.getShop().getId()) {
                                ToastUtil.showToast("不能跨店铺结算商品", false);
                                return;
                            }
                        }
                    }

                    item.setSelect(!item.isSelect());
                    for (CarItemsBean carItemsBean : item.getCarItems()) {
                        int stock = carItemsBean.getProduct().getStock();
                        int status = carItemsBean.getProduct().getStatus();
                        if (stock > 0 && status == 2) {
                            carItemsBean.setSelect(item.isSelect());
                            initShopList(cartList);
                            updateAccountMoney();
                        }

                    }
                }
            });

            ll_shops.addView(itemView);
        }


    }

    private void initProductList(LinearLayout ll_products, List<CarItemsBean> productList, int index) {

        ll_products.removeAllViews();

        for (int i = 0; i < productList.size(); i++) {
            View itemView = mInflater.inflate(R.layout.layout_cart_product_item, null);
            CarItemsBean item = productList.get(i);
            CarItemsBean.ProductBean productBean = productList.get(i).getProduct();
            TextView tv_product_name = itemView.findViewById(R.id.tv_product_name);
            TextView tv_product_price = itemView.findViewById(R.id.tv_product_price);
            TextView tv_delete_price = itemView.findViewById(R.id.tv_delete_price);
            TextView tv_status = itemView.findViewById(R.id.tv_status);
            TextView tv_spec = itemView.findViewById(R.id.tv_spec);
            TextView tv_stock = itemView.findViewById(R.id.tv_stock);
            ImageView cb_select = itemView.findViewById(R.id.cb_select);
            ImageView iv_add = itemView.findViewById(R.id.iv_add);
            ImageView iv_remove = itemView.findViewById(R.id.iv_remove);
            ImageView iv_delete = itemView.findViewById(R.id.iv_delete);
            CommonImage iv_product = itemView.findViewById(R.id.iv_product);

            tv_status.setVisibility(View.GONE);
            tv_product_name.setText(item.getProduct().getName());
            tv_spec.setText(item.getProduct().getSubName());
            tv_product_price.setText("¥" + Double.parseDouble(item.getProduct().getDiscountPrice() + "") / 100);
            tv_delete_price.setText("¥" + Double.parseDouble(item.getProduct().getPrice() + "") / 100);
            LinearLayout ll_item = itemView.findViewById(R.id.ll_item);
            TextView tv_delete = itemView.findViewById(R.id.tv_delete_price);
            tv_delete.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            TextView tv_limit = itemView.findViewById(R.id.tv_limit);
            TextView tv_zeroBy = itemView.findViewById(R.id.tv_zeroBy);
            TextView tv_product_num = itemView.findViewById(R.id.tv_product_num);

            if (item.getProduct().getLimit() == 0) {
                tv_limit.setVisibility(View.GONE);
            } else {
                tv_limit.setVisibility(View.VISIBLE);
                tv_limit.setText("限购" + item.getProduct().getLimit() + "件");
            }
            ll_item.setEnabled(true);
            int zeroBuy = item.getZeroBuy();
            if (1 == zeroBuy) {
                tv_zeroBy.setVisibility(View.VISIBLE);
            } else {
                tv_zeroBy.setVisibility(View.GONE);
            }

            tv_product_name.setTextColor(ContextUtil.getColor(R.color.black));
            tv_product_price.setTextColor(ContextUtil.getColor(R.color.price_red));
            tv_delete_price.setTextColor(ContextUtil.getColor(R.color.grey_text));
            int stock = item.getProduct().getStock();
            cb_select.setEnabled(true);
            if (stock <= 0) {
                cb_select.setEnabled(false);
                tv_stock.setText("库存不足");
                tv_stock.setVisibility(View.VISIBLE);
                ll_item.setEnabled(false);
                tv_product_name.setTextColor(ContextUtil.getColor(R.color.view_color_d9d9d9));
                tv_product_price.setTextColor(ContextUtil.getColor(R.color.price_red_delete));
                tv_delete_price.setTextColor(ContextUtil.getColor(R.color.grey_text_del));
            } else if (stock <= 10) {
                tv_stock.setText("仅剩" + stock + "件");
                tv_stock.setVisibility(View.VISIBLE);
            } else {
                tv_stock.setVisibility(View.GONE);
            }
            int status = item.getProduct().getStatus();
            if (status != 2) {
                tv_status.setVisibility(View.VISIBLE);
                //这里是已下架或者已删掉的商品
                tv_status.setText("该商品已失效");
                if (status == 3) {
                    tv_status.setText("已下架");
                } else if (status == 5) {
                    tv_status.setText("已失效");
                }
                ll_item.setEnabled(false);
                cb_select.setEnabled(false);
            }
            if (item.getCount() > 1) {
                iv_remove.setImageResource(R.mipmap.icon_cart_remove);
            } else {
                iv_remove.setImageResource(R.drawable.iv_minus_grey);
            }
            int limit = item.getProduct().getLimit();
            int r = 0;
            if (limit == 0) {
                r = stock;
            } else {
                r = Math.min(stock, limit);
            }
            if (item.getCount() < r) {
                iv_add.setImageResource(R.mipmap.icon_cart_add);
            } else {
                iv_add.setImageResource(R.drawable.iv_plus_grey);
            }

            tv_product_num.setText("" + item.getCount());

//            holder.setImageFresco(R.id.iv_product, IMAGE + item.getProduct().getImage());
            iv_product.setImageURI(IMAGE + item.getProduct().getImage());

            cb_select.setImageResource(item.isSelect() ? R.mipmap.icon_checked : R.mipmap.icon_unchecked);

            cb_select.setTag(item);

            tv_product_num.setOnClickListener(v -> {
                //修改购买数量
                CarInputNumDialog dialog = new CarInputNumDialog();
                dialog.setOnClickListener(new CarInputNumDialog.OnClickListener() {
                    @Override
                    public void onConfirm(int num) {
                        int stock1 = item.getProduct().getStock();
                        int limit1 = item.getProduct().getLimit();

                        if (limit1 == 0) {
                            if (num > stock1) {
                                ToastUtil.showToast("库存不足", false);
                                return;
                            }
                        } else if (stock1 > limit1) {
                            if (num > limit1) {
                                ToastUtil.showToast("不能超出限购", false);
                                return;
                            }
                        } else {
                            if (num > stock1) {
                                ToastUtil.showToast("库存不足", false);
                                return;
                            }
                        }
                        item.setCount(num);
                        updateAccountMoney();
                        //notifyDataSetChanged();
                        tv_product_num.setText(num + "");
                        Map map = new HashMap();
                        map.put("productId", item.getProductId() + "");
                        map.put("count", num + "");
                        model.carResetCount(map, type_change_count);

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                dialog.showDialog(ShopCartActivity.this);


            });

            cb_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CarItemsBean carItemsBean = (CarItemsBean) v.getTag();
                    int shopId = carItemsBean.getShopId();
                    for (CartBean.ShopCarsBean item : cartList) {
                        if (item.isSelect()) {
                            if (shopId != item.getShop().getId()) {
                                ToastUtil.showToast("不能跨店铺结算商品", false);
                                return;
                            }
                        }
                    }
                    carItemsBean.setSelect(!carItemsBean.isSelect());
                    boolean isShopAllProductSelect = true;
                    for (CarItemsBean carItemsBean1 : cartList.get(index).getCarItems()) {
                        if (!carItemsBean1.isSelect()) {
                            isShopAllProductSelect = false;
                            break;
                        }
                    }
                    cartList.get(index).setSelect(isShopAllProductSelect);
                    initShopList(cartList);
//                    cb_select.setImageResource(isShopAllProductSelect ? R.mipmap.icon_checked : R.mipmap.icon_unchecked);
                    updateAccountMoney();

                }
            });
            iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int stock = item.getProduct().getStock();
                    int limit = item.getProduct().getLimit();
                    int r = 0;
                    if (limit == 0) {
                        r = stock;
                    } else {
                        r = Math.min(stock, limit);
                    }
                    if (item.getCount() < r) {
                        iv_add.setImageResource(R.mipmap.icon_cart_add);
                    }
                    if (item.getCount() > 1) {
                        item.setCount(item.getCount() - 1);
                        updateAccountMoney();
//                        notifyDataSetChanged();
                        tv_product_num.setText(item.getCount() + "");
                        Map map = new HashMap();
                        map.put("productId", item.getProductId() + "");
                        map.put("count", "-1");
                        model.carAdd(map, type_add_cart);
                    } else {
                        iv_remove.setImageResource(R.drawable.iv_minus_grey);
                    }

                }
            });

            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int stock = item.getProduct().getStock();
                    int limit = item.getProduct().getLimit();
                    if (item.getCount() > 1) {
                        iv_remove.setImageResource(R.mipmap.icon_cart_remove);
                    }
                    if (limit == 0) {
                        if (item.getCount() >= stock) {
                            ToastUtil.showToast("库存不足", false);
                            iv_add.setImageResource(R.drawable.iv_plus_grey);
                            return;
                        }
                    } else if (stock > limit) {
                        if (item.getCount() >= limit) {
                            ToastUtil.showToast("不能超出限购", false);
                            iv_add.setImageResource(R.drawable.iv_plus_grey);
                            return;
                        }
                    } else {
                        if (item.getCount() >= stock) {
                            ToastUtil.showToast("库存不足", false);
                            iv_add.setImageResource(R.drawable.iv_plus_grey);
                            return;
                        }
                    }
                    item.setCount(item.getCount() + 1);
                    updateAccountMoney();
//                    notifyDataSetChanged();
                    tv_product_num.setText(item.getCount() + "");
                    Map map = new HashMap();
                    map.put("productId", item.getProductId() + "");
                    map.put("count", "1");
                    model.carAdd(map, type_add_cart);

                }
            });

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(mActivity);
                    affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == R.id.dialog_affirm_btn) {
                                affirmMessageDialog.dismiss();
                                Map map = new HashMap();
                                map.put("productId", item.getProductId() + "");
                                model.carRemove(map, type_remove_cart);
                            }
                        }
                    });
                    affirmMessageDialog.show("确认要删除该商品吗？");
                }
            });

            ll_products.addView(itemView);
        }


    }

    //更新结算金额
    private void updateAccountMoney() {
        LogUtil.e(TAG, "updateAccountMoney");
        selectedCartItemList.clear();
        int selectedShop = 0;//同一店铺全部被选中
        int selectedPro = 0;//商品被选中
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmount2 = BigDecimal.ZERO;
        for (CartBean.ShopCarsBean item : cartList) {
            if (item.isSelect()) {
                selectedShop++;
            }
            for (CarItemsBean carItemsBean : item.getCarItems()) {
                if (carItemsBean.isSelect()) {
                    selectedCartItemList.add(carItemsBean);
                    selectedPro++;

                    int discountPrice = carItemsBean.getProduct().getDiscountPrice();
                    int price = carItemsBean.getProduct().getPrice();

                    int count = carItemsBean.getCount();

                    int zeroBuy = carItemsBean.getZeroBuy();
                    if (1 == zeroBuy) {
                        if (count > 1) {
                            totalAmount2 = totalAmount2.add(BigDecimal.valueOf(Double.parseDouble(price + "") / 100));
                            totalAmount2 = totalAmount2.add(BigDecimal.valueOf(Double.parseDouble(((price - discountPrice) * (count - 1)) + "") / 100).setScale(2, RoundingMode.DOWN));
                        } else {
                            totalAmount2 = totalAmount2.add(BigDecimal.valueOf(Double.parseDouble(price + "") / 100).setScale(2, RoundingMode.DOWN));

                        }


                    } else {
                        totalAmount2 = totalAmount2.add(BigDecimal.valueOf(Double.parseDouble(((price - discountPrice) * count) + "") / 100).setScale(2, RoundingMode.DOWN));
                    }

                    if (1 == zeroBuy) {

                        if (count > 1) {
                            totalAmount = totalAmount.add(BigDecimal.valueOf((Double.parseDouble(discountPrice * (count - 1) + "") / 100)).setScale(2, RoundingMode.DOWN));
                        }

                    } else {
                        totalAmount = totalAmount.add(BigDecimal.valueOf((Double.parseDouble(discountPrice * count + "") / 100)).setScale(2, RoundingMode.DOWN));

                    }

                }
            }

        }

        LogUtil.e(TAG, "totalAmount=" + totalAmount + " totalAmount2=" + totalAmount2);

        countNum = selectedPro;
        totalMoney = totalAmount;

        if (selectedShop == cartList.size() && selectedShop != 0) {
            isSelectAll = true;
        } else {
            isSelectAll = false;
        }
        tv_reduce.setText("优惠：￥" + totalAmount2);

        tv_submit.setText("结算(" + countNum + ")");

        tv_total_amount.setText("¥" + totalMoney.setScale(2, RoundingMode.DOWN));

    }


    @Override
    public void addcart(int id) {

    }
}
