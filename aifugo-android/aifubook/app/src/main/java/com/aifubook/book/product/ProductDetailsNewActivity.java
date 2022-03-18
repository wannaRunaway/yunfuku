package com.aifubook.book.product;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.Constants;
import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.CarInBean;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.download.InstallUtils;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.login.LoginNewActivity;
import com.aifubook.book.model.OnCallBack;
import com.aifubook.book.productcar.ConfirmOrderActivity;
import com.aifubook.book.scan.RQcodeUtil;
import com.aifubook.book.shop.ShopCartActivity;
import com.aifubook.book.shop.ShopHomeActivity;
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.GlideRoundTransform;
import com.aifubook.book.utils.MiniCodeUtil;
import com.aifubook.book.utils.PackageUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.StatusBarUtil;
import com.aifubook.book.utils.ViewToBitmapUtil;
import com.aifubook.book.web.X5TbsFileServicePage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.ms.MyCsBannerViewPager;
import com.jiarui.base.utils.DisplayUtil;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDetailsNewActivity extends BaseActivity implements OnCallBack {

    private static final String TAG = "ProductDetailsNewActivity";
    private static final int type_product_detail = 0x11;
    private static final int type_introduce = 0x12;
    private static final int type_access_token = 0x13;
    private static final int type_shop_detail = 0x14;
    private static final int type_recommend_list = 0x15;
    private static final int type_add_cart = 0x16;

    private String BOOK_SAVE_PATH;
    private String productId, getShareCode, inviteCode;
    private int zeroBuy;
    private MyCsBannerViewPager bannerViewPager;
    private View rl_detail, barView;
    private TextView tv_zeroBy;
    List<ProductListBean.list> mProductList;
    private RecyclerView recyclerView;
    private com.aifubook.book.model.ProductDetailsModel model;
    private LinearLayout ll_ebook;
    private NestedScrollView scrollView;
    private View rl_to_top;
    private ImageView showSharCode, iv_qrcode, iv_back;
    private CommonImage iv_header, iv_book;
    private TextView tv_cart;


    @Override
    protected int setContentView() {
        return R.layout.activity_product_detail_new;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onInit(Bundle bundle) {
        super.onInit(bundle);

        findView();
        initData();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if(event.getMsg_type()==MessageEvent.PAY_SUCCESS){
            ProductDetailsNewActivity.this.finish();
        }

    }

    private void initData() {
        showLoadingDialog();


        StatusBarUtil.setStatusTextColor(true, this);
        BOOK_SAVE_PATH = Constants.BOOK_SAVE_PATH + PackageUtil.getAppPackageName(this) + "/files/eBooks/";


        productId = getIntent().getExtras().getString("productId");
        getShareCode = getIntent().getExtras().getString("inviteCode");
        zeroBuy = getIntent().getExtras().getInt("zeroBuy");

        inviteCode = SharedPreferencesUtil.get(ProductDetailsNewActivity.this, "INVICODE", "");

        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = statusBarHeight;
        barView.setLayoutParams(layoutParams);

        tv_zeroBy.setVisibility(zeroBuy == 1 ? View.VISIBLE : View.GONE);
        mProductList = new ArrayList<>();
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, DisplayUtil.dip2px(mContext, 8), false);
        recyclerView.addItemDecoration(gridSpacingItemDecoration);

        model = new com.aifubook.book.model.ProductDetailsModel(new ProductDetailsModel());
        model.setOnCallBackListener(this);

        model.getWechatAccessToken(type_access_token);


        initAdapter();

        //商品详情
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + productId);
        map.put("zeroBuy", zeroBuy + "");
        model.productDetail(map, type_product_detail);

    }

    private TextView tv_name, tv_book_name, tv_book_price, tv_delete,
            tv_productName, tv_price, tv_discount, tv_purchasing,
            tv_openBooking, tv_sold, sendIn, tv_buy, tv_preSale, onTime,
            tv_config, tv_day, tv_hours, tv_minutes,
            tv_seconds, tv_storeAddress, tv_proprietary, showSharCancel,
            showSharWechar, showSharSave, OnShareElves, tv_customer_service,
            tv_main, tv_groupon_more, tv_price_single, tv_groupon_price;
    private WebView webView;
    private RelativeLayout rl_show_bg;
    private LinearLayout ll_act_bg, ll_buy, ll_sale, ll_share,
            inShopLinearLayout, ll_guarantee, ll_parameter, view_groupon,
            ll_groupon, ll_buy_single, ll_buy_groupon;
    private String shopId;
    private ImageView iv_storeImage, iv_tip;
    private View layout_share, shareView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void findView() {

        barView = findViewById(R.id.fillStatusBarView);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        tv_book_price = findViewById(R.id.tv_book_price);
        tv_discount = findViewById(R.id.tv_discount);
        tv_book_name = findViewById(R.id.tv_book_name);
        tv_productName = findViewById(R.id.tv_productName);
        tv_delete = findViewById(R.id.tv_delete);
        iv_book = findViewById(R.id.iv_book);
        rl_detail = findViewById(R.id.rl_detail);
        tv_zeroBy = findViewById(R.id.tv_zeroBy);
        recyclerView = findViewById(R.id.recyclerView);
        ll_ebook = findViewById(R.id.ll_ebook);
        scrollView = findViewById(R.id.scrollView);
        rl_to_top = findViewById(R.id.rl_to_top);
        showSharCode = findViewById(R.id.showSharCode);
        iv_qrcode = findViewById(R.id.iv_qrcode);
        iv_header = findViewById(R.id.iv_header);
        webView = findViewById(R.id.webView);
        tv_purchasing = findViewById(R.id.tv_purchasing);
        rl_show_bg = findViewById(R.id.rl_show_bg);
        tv_openBooking = findViewById(R.id.tv_openBooking);
        tv_sold = findViewById(R.id.tv_sold);
        ll_act_bg = findViewById(R.id.ll_act_bg);
        ll_buy = findViewById(R.id.ll_buy);
        sendIn = findViewById(R.id.sendIn);
        tv_buy = findViewById(R.id.tv_buy);
        tv_preSale = findViewById(R.id.tv_preSale);
        ll_sale = findViewById(R.id.ll_sale);
        onTime = findViewById(R.id.onTime);
        tv_config = findViewById(R.id.tv_config);
        tv_day = findViewById(R.id.tv_day);
        tv_hours = findViewById(R.id.tv_hours);
        tv_minutes = findViewById(R.id.tv_minutes);
        tv_seconds = findViewById(R.id.tv_seconds);
        tv_storeAddress = findViewById(R.id.tv_storeAddress);
        iv_storeImage = findViewById(R.id.iv_storeImage);
        tv_proprietary = findViewById(R.id.tv_proprietary);
        showSharCancel = findViewById(R.id.showSharCancel);
        layout_share = findViewById(R.id.layout_share);
        iv_back = findViewById(R.id.iv_back);
        showSharWechar = findViewById(R.id.showSharWechar);
        showSharSave = findViewById(R.id.showSharSave);
        ll_share = findViewById(R.id.ll_share);
        OnShareElves = findViewById(R.id.OnShareElves);
        shareView = findViewById(R.id.shareView);
        tv_customer_service = findViewById(R.id.tv_customer_service);
        tv_main = findViewById(R.id.tv_main);
        inShopLinearLayout = findViewById(R.id.inShopLinearLayout);
        ll_guarantee = findViewById(R.id.ll_guarantee);
        iv_tip = findViewById(R.id.iv_tip);
        ll_parameter = findViewById(R.id.ll_parameter);
        view_groupon = findViewById(R.id.view_groupon);
        tv_groupon_more = findViewById(R.id.tv_groupon_more);
        ll_groupon = findViewById(R.id.ll_groupon);
        tv_price_single = findViewById(R.id.tv_price_single);
        ll_buy_single = findViewById(R.id.ll_buy_single);
        ll_buy_groupon = findViewById(R.id.ll_buy_groupon);
        tv_groupon_price = findViewById(R.id.tv_groupon_price);
        tv_cart = findViewById(R.id.tv_cart);
        iv_back.setOnClickListener(this);
        rl_to_top.setOnClickListener(this);
        showSharCancel.setOnClickListener(this);
        showSharWechar.setOnClickListener(this);
        showSharSave.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        OnShareElves.setOnClickListener(this);
        tv_customer_service.setOnClickListener(this);
        tv_main.setOnClickListener(this);
//        tv_buy.setOnClickListener(this);
        inShopLinearLayout.setOnClickListener(this);
//        sendIn.setOnClickListener(this);
        iv_tip.setOnClickListener(this);
        tv_preSale.setOnClickListener(this);
        ll_guarantee.setOnClickListener(this);
        ll_parameter.setOnClickListener(this);
        tv_groupon_more.setOnClickListener(this);
        ll_buy_single.setOnClickListener(this);
        ll_buy_groupon.setOnClickListener(this);
        tv_cart.setOnClickListener(this);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollY = +scrollY;
//                LogUtil.e(TAG, "scrollY=" + scrollY);
                if (scrollY > 2500) {
                    rl_to_top.setVisibility(View.VISIBLE);
                } else {
                    rl_to_top.setVisibility(View.GONE);
                }
            }
        });


    }

    BaseRecyclerAdapter<ProductListBean.list> beanBaseRecyclerAdapter;

    private void initAdapter() {
        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<ProductListBean.list>(this, mProductList, R.layout.home_product_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, ProductListBean.list item, int position, boolean isScrolling) {
                holder.setText(R.id.mCount, "销量" + item.getSoldCount() + "");
                holder.setText(R.id.mPrice, "￥" + (Double.parseDouble(item.getPrice() + "") / 100) + "");
                holder.setText(R.id.mBookName, item.getName() + "");
                holder.setImageByUrl(R.id.mImageView, ApiService.IMAGE + "" + item.getImage());
                holder.getView(R.id.inProductDetails).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productId = item.getId() + "";
                        //productId
                        Bundle bundle = new Bundle();
                        bundle.putString("productId", "" + item.getId());
                        startActivity(ProductDetailsActivity.class, bundle);

                        //  productDetail();
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(beanBaseRecyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
            case R.id.tv_main:
                MessageEvent messageEvent = new MessageEvent(MessageEvent.BACK_MAIN);
                EventBusUtil.post(messageEvent);
                finish();
                break;
            case R.id.rl_to_top:
                scrollView.smoothScrollTo(0, 0);
                break;
            case R.id.showSharCancel:
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.showSharWechar:
                OnShareEvent(shareView);
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.showSharSave:
                viewSaveToImage(shareView);
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.ll_share:
            case R.id.OnShareElves:
                if (isLogin()) {
                    ToastUitl.showLong(this, "请先登录");
                    startActivity(LoginNewActivity.class);
                    return;
                }
                layout_share.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_customer_service:
                AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(ProductDetailsNewActivity.this);
                affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.dialog_affirm_btn) {
                            PermissionX.init(ProductDetailsNewActivity.this).permissions(Manifest.permission.CALL_PHONE).request(new RequestCallback() {
                                @Override
                                public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                    if (allGranted){
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:057188198813");
                                        intent.setData(data);
                                        startActivity(intent);
                                        affirmMessageDialog.dismiss();
                                    }else {
                                        ToastUtil.showToast("开启电话权限才能拨打客服热线", false);
                                    }
                                }
                            });

                        }
                    }
                });
                affirmMessageDialog.show("是否拨打客服热线？");
                break;
//            case R.id.tv_cart:
//                if (!isLogin()) {
//                    startActivity(ShopCartActivity.class);
//                } else {
//                    startActivity(LoginNewActivity.class);
//                }
//                break;
            case R.id.inShopLinearLayout:
                bundle.putString("shopId", shopId);
                startActivity(ShopHomeActivity.class, bundle);
                break;
            case R.id.sendIn:
                if (isLogin()) {
                    ToastUitl.showLong(this, "请先登录");
                    startActivity(LoginNewActivity.class);
                    return;
                }
                carAdd();
                break;
            case R.id.iv_tip:
                ShowReportDialog.showDialog(ProductDetailsNewActivity.this);
                break;
            case R.id.tv_preSale:
                ShowReportDialog.showTipDialog(ProductDetailsNewActivity.this);
                break;
            case R.id.ll_guarantee:
                //保障
                ShowReportDialog.showGuaranteeDialog(this);
                break;
            case R.id.ll_parameter:
                if (productDetailBean != null) {
                    // ShowReportDialog.showParameterDialog(this);
                    ShowReportDialog.showParameterDialog(this, productDetailBean.getPress(), productDetailBean.getSubject(),
                            productDetailBean.getCode(), productDetailBean.getClasses(), productDetailBean.getPrice() + "", productDetailBean.getDiscountPrice() + "");

                }
                break;
            case R.id.tv_groupon_more:
                //拼单查看更多
                ShowReportDialog dialog = new ShowReportDialog();
                dialog.showGrouponDialog(this, groupOrders);
                dialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                    @Override
                    public void onConfirm(Object bean) {
                        ProductDetailBean.GroupOrder groupOrder = (ProductDetailBean.GroupOrder) bean;
                        grouponBuy(true, groupOrder);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                break;
            case R.id.ll_buy_single:
                //单独购买
                grouponBuy(false, null);
                break;
            case R.id.ll_buy_groupon:
                //拼团

//                startActivity(GrouponShareOrderActivity.class);
                // grouponBuy(true, null);
                firstGrouponBuy();
                break;
            case R.id.tv_cart:

                startActivity(ShopCartActivity.class);

                break;


        }

    }

    private void firstGrouponBuy() {
        if (isLogin()) {
            ToastUitl.showLong(this, "请先登录");
            startActivity(LoginNewActivity.class);
            return;
        }
        List<CarInBean> carInBeans = new ArrayList<>();

        CarInBean carInBean = new CarInBean();
        carInBean.setName(ShopName);

        List<CarInBean.productListBean> productListBeans = new ArrayList<>();

        CarInBean.productListBean productListBean = new CarInBean.productListBean();
        productListBean.setId(productDetailBean.getId());
       /* if ( productDetailBean.getChiefDiscount()!=null &&  productDetailBean.getChiefDiscount() != 0) {
            LogUtil.e(TAG,"chiefDiscount="+productDetailBean.getChiefDiscount());
            productListBean.setDiscountPrice(productDetailBean.getChiefDiscount());
            LogUtil.e(TAG,"DiscountPrice="+productDetailBean.getDiscountPrice());
        } else {
            productListBean.setDiscountPrice(productDetailBean.getGroupPrice());
        }*/
        productListBean.setDiscountPrice(productDetailBean.getGroupPrice());
        productListBean.setGroupBuy(1);
//        productListBean.setGroupType(productDetailBean.getType());
        productListBean.setCanDiscount(productDetailBean.isCanDiscount());
        productListBean.setExpressFree(productDetailBean.isExpressFree());
        productListBean.setChiefReceive(productDetailBean.isChiefReceive());
        if (productDetailBean.getChiefDiscount() != null) {
            productListBean.setChiefDiscount(productDetailBean.getChiefDiscount());
        }
        productListBean.setImage(productDetailBean.getImage());
        productListBean.setName(productDetailBean.getName());
        productListBean.setPrice(productDetailBean.getPrice());
        productListBean.setShopId(productDetailBean.getShopId());
        productListBean.setStock(productDetailBean.getStock());
        productListBean.setCount(1);
        productListBean.setLimit(productDetailBean.getLimit());
        productListBean.setZeroBuy(productDetailBean.getZeroBuy());
        productListBean.setShareMiniPic(shareMiniPic);

        productListBeans.add(productListBean);

        carInBean.setProductListBeans(productListBeans);
        carInBeans.add(carInBean);

        openOrderActivity(carInBeans);
    }

    private void openOrderActivity(List<CarInBean> carInBeans) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", (Serializable) carInBeans);
        bundle.putString("inviteCode", getShareCode);
        bundle.putString("productId", productDetailBean.getId() + "");
        startActivity(ConfirmOrderActivity.class, bundle);
    }


    private void grouponBuy(boolean isGroupon, ProductDetailBean.GroupOrder groupOrder) {
        if (isLogin()) {
            ToastUitl.showLong(this, "请先登录");
            startActivity(LoginNewActivity.class);
            return;
        }
        List<CarInBean> carInBeans = new ArrayList<>();

        CarInBean carInBean = new CarInBean();
        carInBean.setName(ShopName);

        List<CarInBean.productListBean> productListBeans = new ArrayList<>();

        CarInBean.productListBean productListBean = new CarInBean.productListBean();
        productListBean.setId(productDetailBean.getId());
        if (isGroupon) {
            //拼团
           /* if (productDetailBean.getChiefDiscount()!=null && productDetailBean.getChiefDiscount() != 0) {
                productListBean.setDiscountPrice(productDetailBean.getChiefDiscount());
            } else {
                productListBean.setDiscountPrice(productDetailBean.getGroupPrice());
            }*/
            productListBean.setDiscountPrice(productDetailBean.getGroupPrice());
            productListBean.setGroupBuy(1);


        } else {
            productListBean.setDiscountPrice(productDetailBean.getDiscountPrice());
            productListBean.setGroupBuy(0);
        }
        if (groupOrder != null) {
            productListBean.setGroupBuyOrderId(groupOrder.getId());
//            productListBean.setChiefDiscount(groupOrder.getChiefDiscount());
            productListBean.setChiefDiscount(0);
            productListBean.setChiefReceive(groupOrder.isChiefReceive());//团长代收
            productListBean.setFakeMember(groupOrder.isFakeMember());
            productListBean.setCanDiscount(groupOrder.isCanDiscount());//优惠叠加
            productListBean.setExpressFree(groupOrder.isExpressFree());//运费
            productListBean.setMemberId(groupOrder.getMemberId());
            productListBean.setMemberName(groupOrder.getMemberName());
            productListBean.setMemberMobile(groupOrder.getMemberMobile());
        }

        productListBean.setImage(productDetailBean.getImage());
        productListBean.setName(productDetailBean.getName());
        productListBean.setPrice(productDetailBean.getPrice());
        productListBean.setShopId(productDetailBean.getShopId());
        productListBean.setStock(productDetailBean.getStock());
        productListBean.setCount(1);
        productListBean.setLimit(productDetailBean.getLimit());
        productListBean.setZeroBuy(productDetailBean.getZeroBuy());
        productListBean.setShareMiniPic(shareMiniPic);

        productListBeans.add(productListBean);

        carInBean.setProductListBeans(productListBeans);
        carInBeans.add(carInBean);
        openOrderActivity(carInBeans);
    }


    @Override
    public void onNext(Object result, int type) {
        closeLoadingDialog();
        if (type == type_product_detail) {
            onProductDetailResult(result);
            getShopDetail();
        } else if (type_access_token == type) {
            onAccessTokenResult(result);
        } else if (type_shop_detail == type) {
            getRecommendList();
            onShopDetailResult(result);
        } else if (type_recommend_list == type) {
            onRecommendResult(result);
        } else if (type_add_cart == type) {
            ToastUitl.showShort(this, "加入购物车成功!");
        }

    }

    private void onRecommendResult(Object result) {

        ProductListBean DataBean = (ProductListBean) result;
        mProductList.clear();
        mProductList.addAll(DataBean.getList());
        beanBaseRecyclerAdapter.notifyDataSetChanged();
        closeLoadingDialog();
        rl_detail.setVisibility(View.VISIBLE);

    }

    private void getRecommendList() {
        Map<String, Object> map = new HashMap<>();
        map.put("from", 3);
        map.put("recommend", 1);
        map.put("pageSize", 20);
        map.put("shopId", shopId);
        model.productList(map, type_recommend_list);
    }

    private String ShopName;

    private void onShopDetailResult(Object result) {

        ShopBean DataBean = (ShopBean) result;
        ShopName = DataBean.getName();
        tv_storeAddress.setText(DataBean.getName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.bg_home_banner) //预加载图片
                .error(R.mipmap.bg_home_banner) //加载失败图片
                .transform(new GlideRoundTransform(5)); //圆角
        Glide.with(this)
                .load(ApiService.IMAGE + DataBean.getLogo())
                .apply(options)
                .into(iv_storeImage);
        if (DataBean.getId() == 0) {
            tv_proprietary.setVisibility(View.VISIBLE);
        } else {
            tv_proprietary.setVisibility(View.GONE);
        }


    }

    private void carAdd() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", "" + productId);
        map.put("count", "" + 1);
        map.put("zeroBuy", zeroBuy + "");
        if (!StringUtils.isEmpty(getShareCode)) {
            map.put("inviteCode", getShareCode);
        }
        model.carAdd(map, type_add_cart);
    }

    private void OnShareEvent(View view) {
        if (!MyApp.mWxApi.isWXAppInstalled()) {
            return;
        }

        ViewToBitmapUtil viewToBitmapUtil = new ViewToBitmapUtil();
        viewToBitmapUtil.setCacheResultListener(new ViewToBitmapUtil.CacheResult() {
            @Override
            public void result(Bitmap bitmap) {

                WXImageObject imageObject = new WXImageObject(bitmap);
                WXMediaMessage msg = new WXMediaMessage(imageObject);

                if (!StringUtils.isEmpty(getIntent().getExtras().getString("Title"))) {
                    msg.title = "" + getIntent().getExtras().getString("Title");
                } else {
                    msg.title = "云辅库";
                }
                if (!StringUtils.isEmpty(getIntent().getExtras().getString("content"))) {
                    msg.description = "" + getIntent().getExtras().getString("content");
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                //WXSceneTimeline朋友圈    WXSceneSession聊天界面
                req.scene = SendMessageToWX.Req.WXSceneSession;//聊天界面  1 == 1 ? SendMessageToWX.Req.WXSceneTimeline :
                req.message = msg;
                req.transaction = String.valueOf(System.currentTimeMillis());
                MyApp.mWxApi.sendReq(req);


            }
        });
        viewToBitmapUtil.getBitmapFromView(ProductDetailsNewActivity.this, view);

    }

    private void viewSaveToImage(View view) {

        // 把一个View转换成图片
        // Bitmap cachebmp = loadBitmapFromView(view);

        ViewToBitmapUtil viewToBitmapUtil = new ViewToBitmapUtil();
        viewToBitmapUtil.setCacheResultListener(new ViewToBitmapUtil.CacheResult() {
            @Override
            public void result(Bitmap bitmap) {

                FileOutputStream fos;
                String imagePath = "";
                try {
                    // 判断手机设备是否有SD卡
                    boolean isHasSDCard = Environment.getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED);
                    if (isHasSDCard) {
                        // SD卡根目录
                        File sdRoot = Environment.getExternalStorageDirectory();
                        File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
                        fos = new FileOutputStream(file);
                        imagePath = file.getAbsolutePath();
                    } else
                        throw new Exception("创建文件失败!");

                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogUtil.e("asdasd", "imagePath=" + imagePath);

                view.destroyDrawingCache();
                Toast.makeText(mContext, "保存成功!", Toast.LENGTH_SHORT).show();
                // 发送广播，通知刷新图库的显示
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));

            }
        });
        viewToBitmapUtil.getBitmapFromView(ProductDetailsNewActivity.this, view);


    }

    private void getShopDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + shopId);
        model.shopDetail(map, type_shop_detail);
    }

    private void onAccessTokenResult(Object result) {
        String weChatToken = (String) result;

        LogUtil.e(TAG, "token=" + weChatToken);

        MiniCodeUtil codeUtil = new MiniCodeUtil();
        codeUtil.getBitmapData(weChatToken, inviteCode, productId);

        codeUtil.setOnRequestListener(new MiniCodeUtil.RequestResponse() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onResponse(Bitmap wechatqrcode) {
                // shareBitmap = wechatqrcode;
                if (showSharCode != null) {
                    // showSharCode.setImageDrawable(wechatqrcode);
                    showSharCode.setImageBitmap(wechatqrcode);
                }
            }
        });

        String shareUrl = Constants.shareUrl + "product?id=" + productId + "&inviteCode=" + inviteCode;
        LogUtil.e(TAG, "shareUrl=" + shareUrl);
        Bitmap rQcode = RQcodeUtil.getRQcode(shareUrl);
        iv_qrcode.setImageBitmap(rQcode);
    }


    @Override
    public void onError(String error, int type) {
        ToastUitl.showShort(mContext, error);
    }

    private String fileUrl;
    private ProductDetailBean productDetailBean;

    private List<ProductDetailBean.GroupOrder> groupOrders;

    private void onProductDetailResult(Object result) {
        productDetailBean = (ProductDetailBean) result;
        groupOrders = productDetailBean.getGroupOrders();

        if (groupOrders != null && groupOrders.size() > 0) {

            ll_groupon.removeAllViews();

            for (int i = 0; i < groupOrders.size(); i++) {
                if (i > 1) {
                    break;
                }
                View grouponView = mInflater.inflate(R.layout.item_groupon_product, null);
                CommonImage cimage1 = grouponView.findViewById(R.id.cimage1);
                CommonImage cimage2 = grouponView.findViewById(R.id.cimage2);
                TextView tv_name = grouponView.findViewById(R.id.tv_name);
                TextView tv_groupon_order = grouponView.findViewById(R.id.tv_groupon_order);
                List<ProductDetailBean.GroupOrder.User> users = groupOrders.get(i).getUsers();
                ProductDetailBean.GroupOrder groupOrder = groupOrders.get(i);
                String orderId = groupOrder.getId();
                tv_groupon_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grouponBuy(true, groupOrder);
                    }
                });

                if (users.size() > 1) {
                    cimage1.setVisibility(View.VISIBLE);
                    cimage2.setVisibility(View.VISIBLE);
                } else {
                    cimage1.setVisibility(View.GONE);
                    cimage2.setVisibility(View.VISIBLE);
                }
                for (int j = 0; j < users.size(); j++) {
                    if (j < 2) {
                        ProductDetailBean.GroupOrder.User user = users.get(j);
                        String userName = user.getUserName();
                        String userImage = user.getUserImage();
                        if (!userImage.contains("http")) {
                            userImage = ApiService.IMAGE + userImage;
                        }
                        if (j == 0) {
                            cimage2.setImageURI(userImage);
                            tv_name.setText(userName);
                        } else {
                            cimage1.setImageURI(userImage);
                            tv_name.setText(users.get(0).getUserName() + " " + userName);
                        }

                    }
                }
                ll_groupon.addView(grouponView);

            }

            view_groupon.setVisibility(View.VISIBLE);
        } else {
            view_groupon.setVisibility(View.GONE);
        }


        ProductDetailBean.EBook eBook = productDetailBean.geteBook();
        if (eBook != null) {
            ll_ebook.setVisibility(View.VISIBLE);
            String[] urls = eBook.getUrls();
            if (urls != null) {
                String url = urls[0];
                LogUtil.e(TAG, "ebook=url=" + url);
                fileUrl = ApiService.IMAGE + url;
                showFile();

            }
        } else {
            ll_ebook.setVisibility(View.GONE);
        }

        String userName = SharedPreferencesUtil.get(ProductDetailsNewActivity.this, KeyCom.NICKNAME, "---");
        String logo = SharedPreferencesUtil.get(ProductDetailsNewActivity.this, KeyCom.USER_LOGO, "");
        String discount = "¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getDiscountPrice() + "") / 100) + "");
        String price = "¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getPrice() + "") / 100) + "");
        LogUtil.e(TAG, "logo==" + logo);
        if (!StringUtils.isEmpty(logo)) {
            if (logo.contains("http")) {
                iv_header.setImageURI(logo);
            } else {
                iv_header.setImageURI(ApiService.IMAGE + logo);
            }
        }

        tv_name.setText(userName);
        iv_book.setImageURI(ApiService.IMAGE + productDetailBean.getImage());
        tv_book_name.setText(productDetailBean.getName());
        tv_book_price.setText(discount);
        tv_delete.setText(price);
        tv_delete.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线


        tv_productName.setText(StringUtils.isNull(productDetailBean.getName()));
//        if (productDetailBean.getZeroBuy() == 1) {
//            DataBean.setDiscountPrice(0);
//        }
        tv_price.setText("¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getDiscountPrice() + "") / 100) + ""));
        tv_discount.setText("¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getPrice() + "") / 100) + ""));
        tv_discount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
//        tv_sold.setText("已售" + StringUtils.isNull(DataBean.getSoldCount() + ""));

        tv_price_single.setText("¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getDiscountPrice() + "") / 100) + ""));

      /*  Integer chiefDiscount = DataBean.getChiefDiscount();
        if(chiefDiscount!=null && chiefDiscount!=0){
            tv_groupon_price.setText("¥" + StringUtils.isNull((Double.parseDouble(chiefDiscount + "") / 100) + ""));
        }else {
            tv_groupon_price.setText("¥" + StringUtils.isNull((Double.parseDouble(DataBean.getGroupPrice() + "") / 100) + ""));
        }*/
        tv_groupon_price.setText("¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getGroupPrice() + "") / 100) + ""));
        if (productDetailBean.getLimit() == 0) {
            tv_purchasing.setVisibility(View.GONE);
        } else {
            tv_purchasing.setVisibility(View.VISIBLE);
            tv_purchasing.setText("限购" + productDetailBean.getLimit() + "份");
        }
        initWebView(productDetailBean.getDescribe().getContent());

        if (productDetailBean.getPre() == 1) {
            //预售 红色底
            rl_show_bg.setBackground(ContextUtil.getResource(R.drawable.shape_red_5dp));
            tv_price.setTextColor(ContextUtil.getColor(R.color.white));
            tv_openBooking.setBackground(ContextUtil.getResource(R.drawable.shape_red_bg));
            tv_openBooking.setTextColor(ContextUtil.getColor(R.color.white));
            tv_purchasing.setBackground(ContextUtil.getResource(R.drawable.shape_red_bg));
            tv_purchasing.setTextColor(ContextUtil.getColor(R.color.white));
            tv_zeroBy.setBackground(ContextUtil.getResource(R.drawable.shape_red_bg));
            tv_zeroBy.setTextColor(ContextUtil.getColor(R.color.white));
            tv_sold.setTextColor(ContextUtil.getColor(R.color.white));
            tv_discount.setTextColor(ContextUtil.getColor(R.color.view_color_9EFFFFFF));
            ll_act_bg.setVisibility(View.VISIBLE);


            ll_buy.setVisibility(View.GONE);
            sendIn.setVisibility(View.GONE);
            tv_buy.setVisibility(View.GONE);
            tv_openBooking.setVisibility(View.VISIBLE);
            tv_preSale.setVisibility(View.VISIBLE);
            ll_sale.setVisibility(View.VISIBLE);
            onTime.setVisibility(View.VISIBLE);
            Long preTimeDiff = productDetailBean.getPreTimeDiff();
            LogUtil.e(TAG, "time==" + preTimeDiff);
            showTimeCounter(preTimeDiff * 1000);

        } else {
            rl_show_bg.setBackgroundColor(ContextUtil.getColor(R.color.white));
            tv_price.setTextColor(ContextUtil.getColor(R.color.red_text));
            tv_openBooking.setBackground(ContextUtil.getResource(R.drawable.shape_white_bg));
            tv_openBooking.setTextColor(ContextUtil.getColor(R.color.red_text));
            tv_purchasing.setBackground(ContextUtil.getResource(R.drawable.shape_white_bg));
            tv_purchasing.setTextColor(ContextUtil.getColor(R.color.red_text));
            tv_zeroBy.setBackground(ContextUtil.getResource(R.drawable.shape_white_bg));
            tv_zeroBy.setTextColor(ContextUtil.getColor(R.color.red_text));
            tv_sold.setTextColor(ContextUtil.getColor(R.color.red_text));
            tv_discount.setTextColor(ContextUtil.getColor(R.color.view_color_979797));
            ll_act_bg.setVisibility(View.INVISIBLE);


            tv_openBooking.setVisibility(View.GONE);
            ll_buy.setVisibility(View.VISIBLE);
            sendIn.setVisibility(View.VISIBLE);
            tv_buy.setVisibility(View.VISIBLE);
            tv_preSale.setVisibility(View.GONE);
            ll_sale.setVisibility(View.GONE);
            onTime.setVisibility(View.GONE);
        }
        shopId = productDetailBean.getShopId() + "";
//        shopDetail();
//        shopId
//        bookCode.setText("205547");
        tv_config.setText(productDetailBean.getSubject() + " " + productDetailBean.getClasses() + " " + productDetailBean.getPress());
//        productList();


        bannerList.clear();
        for (int i = 0; i < productDetailBean.getImages().size(); i++) {
            String image = productDetailBean.getImages().get(i);
            if (i == 0) {
                shareMiniPic = ApiService.IMAGE + image;
            }
            bannerList.add(ApiService.IMAGE + image);

        }

        if (bannerList.size() > 0) {
            if (null == bannerViewPager) {
                bannerViewPager = new MyCsBannerViewPager(rl_detail, new MyCsBannerViewPager.BannerOnItemClickListener() {

                    @Override
                    public void onListener(int position) {


                    }
                });
            }
            bannerViewPager.setAspectRatio(1);
            bannerViewPager.show(bannerList);
        }


    }

    private String shareMiniPic;
    private CountDownTimer countDownTimer;
    private ArrayList<String> bannerList = new ArrayList<>();


    private void showTimeCounter(long times) {

        if (times <= 0) {
            return;
        }
        countDownTimer = new CountDownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                long hour = (millisUntilFinished - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minute = (millisUntilFinished - days * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                long seconds = millisUntilFinished % 60000;
                long second = Math.round((float) seconds / 1000);

                tv_day.setText(days + "");
                tv_hours.setText(hour + "");
                tv_minutes.setText(minute + "");
                tv_seconds.setText(second + "");

            }

            @Override
            public void onFinish() {
                ll_sale.setVisibility(View.GONE);
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    private void initWebView(String detailsUrl) {
        //webview
        //启用支持javascript
        //封装头文件,将图片铺满屏幕显示
        String sHead = "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />" +
                "<style>img{max-width:100% !important;height:auto !important;}</style>"
                + "<style>body{max-width:100% !important;}</style>" + "</head><body>";
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDomStorageEnabled(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.loadDataWithBaseURL(null, sHead + detailsUrl + "</body></html>", "text/html", "utf-8", null);
    }


    private void showFile() {
        ll_ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionX.init(ProductDetailsNewActivity.this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).request(
                        new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, @NonNull @NotNull List<String> grantedList, @NonNull @NotNull List<String> deniedList) {
                                if (allGranted) {
                                    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
                                    String filePath = BOOK_SAVE_PATH + fileName;
                                    LogUtil.e(TAG, "filePath=" + filePath);
                                    File file = new File(filePath);
                                    if (file.exists()) {
                                        Intent intent = new Intent(ProductDetailsNewActivity.this, X5TbsFileServicePage.class);
                                        intent.putExtra("filePath", filePath);
                                        startActivity(intent);
                                    } else {
                                        downLoad(fileUrl, BOOK_SAVE_PATH + fileName);
                                    }
                                } else {
                                    showToast("请开启权限才能打开文件进行查看");
                                }
                            }
                        }
                );

            }
        });
    }

    private InstallUtils.DownloadCallBack downloadCallBack;

    private void downLoad(String url, String filePath) {


        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                showLoadingDialog();

            }

            @Override
            public void onComplete(String path) {
                closeLoadingDialog();
                LogUtil.e(TAG, "complete-path==" + path);
                Intent intent = new Intent(ProductDetailsNewActivity.this, X5TbsFileServicePage.class);
                intent.putExtra("filePath", path);
                startActivity(intent);

            }

            @Override
            public void onLoading(long total, long current) {
                int progress = (int) (current * 100 / total);
//                tv_progress.setText("正在下载 " + progress + "%");
            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void cancle() {

            }
        };

        InstallUtils.with(this)
                //必须-下载地址
                .setApkUrl(url)
                //非必须-下载保存的文件的完整路径+name.apk
                .setApkPath(filePath)
                //非必须-下载回调
                .setCallBack(downloadCallBack)
                //开始下载
                .startDownload();


    }
}
