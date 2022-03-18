package com.aifubook.book.product;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.Constants;
import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.bean.CarInBean;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.dialog.ShowShareDialog;
import com.aifubook.book.download.InstallUtils;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.login.LoginNewActivity;
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
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jiarui.base.baserx.bean.MessageEvent;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.ms.MyCsBannerViewPager;
import com.jiarui.base.utils.DisplayUtil;
import com.jiarui.base.utils.EventBusUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.ByteArrayOutputStream;
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
import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/product/ProductDetailsActivity")
public class ProductDetailsActivity extends BaseActivity<ProductDetailsPresenter> implements ProductDetailsView {


    private static final String TAG = "ProductDetailsActivity";

    @BindView(R.id.ll_sale)
    LinearLayout ll_sale;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.tv_hours)
    TextView tv_hours;
    @BindView(R.id.tv_minutes)
    TextView tv_minutes;
    @BindView(R.id.tv_seconds)
    TextView tv_seconds;

    @BindView(R.id.tv_productName)
    TextView tv_productName;

    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_discount)
    TextView tv_discount;

    @BindView(R.id.tv_sold)
    TextView tv_sold;

    @BindView(R.id.tv_purchasing)
    TextView tv_purchasing;

    @BindView(R.id.iv_back)
    ImageView iv_back;

//    @BindView(R.id.banner)
//    MZBannerView mMZBanner;

    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.tv_text2)
    TextView tv_text2;
    @BindView(R.id.tv_text3)
    TextView tv_text3;
    @BindView(R.id.tv_view)
    View tv_view;
    @BindView(R.id.tv_view2)
    View tv_view2;
    @BindView(R.id.tv_view3)
    View tv_view3;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_proprietary)
    TextView tv_proprietary;

    @BindView(R.id.ll_share)
    LinearLayout ll_share;

    @BindView(R.id.showSharCode)
    ImageView showSharCode;

    @BindView(R.id.iv_tip)
    ImageView iv_tip;

    @BindView(R.id.tv_config)
    TextView tv_config;

    @BindView(R.id.tv_preSale)
    TextView tv_preSale;

    @BindView(R.id.tv_buy)
    TextView tv_buy;

    @BindView(R.id.sendIn)
    TextView sendIn;

    @BindView(R.id.ll_buy)
    LinearLayout ll_buy;

    @BindView(R.id.layout_share)
    View layout_share;

    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.rl_to_top)
    View rl_to_top;

    String productId = "";

    @BindView(R.id.rl_detail)
    View rl_detail;

    @BindView(R.id.ll_ebook)
    View ll_ebook;

    @BindView(R.id.rl_show_bg)
    RelativeLayout rl_show_bg;

    @BindView(R.id.top)
    RelativeLayout rl_top;

    @BindView(R.id.tv_zeroBy)
    TextView tv_zeroBy;

    BaseRecyclerAdapter<String> baseRecyclerAdapter;
    List<String> productList = new ArrayList<>();

    //    @Autowired
//    String productId;
    private MyCsBannerViewPager bannerViewPager;


    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initPresenter() {
        mPresenter = new ProductDetailsPresenter(this);
    }

    // 商品详情的接口
    private void productDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + productId);
        map.put("zeroBuy", zeroBuy + "");
        mPresenter.productDetail(map);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bannerViewPager != null) {
            bannerViewPager.startAutoScroll();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bannerViewPager != null) {
            bannerViewPager.stopAutoScroll();
        }

    }

    String shopId = "";

    // 获取隐私协议
    private void getConfigValue() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "shoppingGuid");
        mPresenter.getConfigValue(map);
    }

    // 获取店铺详情的接口
    private void shopDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + shopId);
        mPresenter.shopDetail(map);
    }

//    private Bitmap shareBitmap;

    @Override
    public void getWeChatToken(String weChatToken) {
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


    /**
     * 生成二维码,默认500大小
     *
     * @param contents 需要生成二维码的文字、网址等
     * @return bitmap
     */
    BarcodeEncoder barcodeEncoder;

    public Bitmap createQRCode(String contents) {
        barcodeEncoder = new BarcodeEncoder();
        try {
            return barcodeEncoder.encodeBitmap(contents, BarcodeFormat.QR_CODE, 500, 500);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    String ShopStatus = "2";

    private String inviteCode;
    private String getShareCode = "";

//    @BindView(R.id.fillStatusBarView)
//    View barView;
    private int zeroBuy = 0;

    private String BOOK_SAVE_PATH;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        rl_detail.setVisibility(View.INVISIBLE);
        startProgressDialog();

        StatusBarUtil.setStatusTextColor(true, this);
        BOOK_SAVE_PATH = Constants.BOOK_SAVE_PATH + PackageUtil.getAppPackageName(this) + "/files/eBooks/";


        productId = getIntent().getExtras().getString("productId");
        getShareCode = getIntent().getExtras().getString("inviteCode");
        zeroBuy = getIntent().getExtras().getInt("zeroBuy");

        inviteCode = SharedPreferencesUtil.get(ProductDetailsActivity.this, "INVICODE", "");
        mPresenter.getWechatAccessToken();

//        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
//        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
//        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        layoutParams.height = statusBarHeight;
//        barView.setLayoutParams(layoutParams);
//        setMargins(rl_top,0,statusBarHeight,0,0);

        tv_zeroBy.setVisibility(zeroBuy == 1 ? View.VISIBLE : View.GONE);

        setTitle("商品详情");

        //生成二维码
        // showSharCode.setImageBitmap(createQRCode("{\"inviteCode\":" + SharedPreferencesUtil.get(ProductDetailsActivity.this, "INVICODE", "") + ",\"type\":3,\"PRODUCTID\":" + productId + "}"));

        ShopStatus = SharedPreferencesUtil.get(ProductDetailsActivity.this, "GROUPSTATUS", "2");
        // whoSend.setText(SharedPreferencesUtil.get(ProductDetailsActivity.this, KeyCom.NICKNAME, "---") + "向您推荐");
//        if (!StringUtils.isEmpty(inviteCode)) {
//            sendCode.setText("邀请码:" + SharedPreferencesUtil.get(ProductDetailsActivity.this, "INVICODE", "---"));
//            sendCode.setVisibility(View.VISIBLE);
//        } else {
//            sendCode.setVisibility(View.INVISIBLE);
//        }
        /*if (ShopStatus.equals("1")) {
            OnShareElves.setVisibility(View.VISIBLE);
        } else {
            OnShareElves.setVisibility(View.GONE);
        }*/
        OnShareElves.setVisibility(View.VISIBLE);

        mProductList = new ArrayList<>();
        initAdapter();
        productDetail();

        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, DisplayUtil.dip2px(mContext, 8), false);
        recyclerView.addItemDecoration(gridSpacingItemDecoration);

        getConfigValue();


        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollY = +scrollY;
                LogUtil.e(TAG, "scrollY=" + scrollY);
                if (scrollY > 2500) {
                    rl_to_top.setVisibility(View.VISIBLE);
                } else {
                    rl_to_top.setVisibility(View.GONE);
                }
            }
        });

    }

    int scrollY = 0;

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void DialogShowDetails(String Message) {
        Log.e("SDAsdasdasdasda", "" + Message);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setUseWideViewPort(false);//设置此属性，可任意比例缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadWithOverviewMode(false); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        String detailHtml = StringUtils.isNull(Message);
//图片宽度改为100%  高度为自适应
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        webView.loadData(varjs + detailHtml, "text/html", "UTF-8");
    }

    List<ProductListBean.list> mProductList;
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

    int count = 1;

    private boolean Check() {
        if (SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.IS_LOGIN, "0").equals("0")) {
            return true;
        }
        return false;
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
        viewToBitmapUtil.getBitmapFromView(ProductDetailsActivity.this, view);


    }


    private void showShareDialog() {

        if (productDetailBean == null) {
            return;
        }

        ShowShareDialog dialog = new ShowShareDialog();
        dialog.setOnClickListener(new ShowShareDialog.ChooseClickListener() {
            @Override
            public void weChatClick(View view) {
                OnShareElves(view);
            }

            @Override
            public void saveClick() {

            }
        });
        String userName = SharedPreferencesUtil.get(ProductDetailsActivity.this, KeyCom.NICKNAME, "---");
        String discount = "¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getDiscountPrice() + "") / 100) + "");
        String price = "¥" + StringUtils.isNull((Double.parseDouble(productDetailBean.getPrice() + "") / 100) + "");

//        dialog.shareDialog(this, ApiService.IMAGE + productDetailBean.getImage(),
//                ApiService.IMAGE + productDetailBean.getImage(), userName, productDetailBean.getName(), discount, price, shareBitmap);

    }


    @BindView(R.id.OnShareElves)
    TextView OnShareElves;

    @BindView(R.id.shareView)
    View shareView;


    @OnClick({R.id.iv_back, R.id.rl_to_top, R.id.ll_share, R.id.showSharWechar, R.id.showSharCancel, R.id.showSharSave, R.id.OnShareElves, R.id.tv_customer_service, R.id.tv_buy, R.id.inShopLinearLayout, R.id.sendIn, R.id.tv_cart, R.id.tv_main, R.id.iv_tip, R.id.tv_preSale, R.id.ll_guarantee, R.id.ll_parameter})
    void Onclicks(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_to_top:
                scrollView.smoothScrollTo(0, 0);
                break;
            case R.id.showSharCancel:
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.showSharWechar:
                OnShareElves(shareView);
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.showSharSave:
                viewSaveToImage(shareView);
                layout_share.setVisibility(View.GONE);
                break;
            case R.id.ll_share:
            case R.id.OnShareElves:
                if (Check()) {
                    ToastUitl.showLong(this, "请先登录");
                    startActivity(LoginNewActivity.class);
                    return;
                }
                layout_share.setVisibility(View.VISIBLE);
                // showShar.setVisibility(View.VISIBLE);
                //showShareDialog();
                break;
            case R.id.tv_customer_service:
                AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(ProductDetailsActivity.this);
                affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.dialog_affirm_btn) {
                            PermissionX.init(ProductDetailsActivity.this).permissions(Manifest.permission.CALL_PHONE).request(new RequestCallback() {
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
            case R.id.tv_main:
                MessageEvent messageEvent =new MessageEvent(MessageEvent.BACK_MAIN);
                EventBusUtil.post(messageEvent);
                finish();
                break;
            case R.id.tv_cart:
//                MainActivity.mActivity.cutShopCar();  // 跳转到购物车
//                MessageEvent event = new MessageEvent(MessageEvent.SEND_CART);
//                EventBusUtil.post(event);
//                this.finish();
                if(!isLogin()){
                    startActivity(ShopCartActivity.class);
                }else{
                    startActivity(LoginNewActivity.class);
                }

                break;
            case R.id.tv_buy:
                if (Check()) {
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
                productListBean.setDiscountPrice(productDetailBean.getDiscountPrice());
                productListBean.setImage(productDetailBean.getImage());
                productListBean.setName(productDetailBean.getName());
                productListBean.setPrice(productDetailBean.getPrice());
                productListBean.setShopId(productDetailBean.getShopId());
                productListBean.setStock(productDetailBean.getStock());
                productListBean.setCount(1);
                productListBean.setLimit(productDetailBean.getLimit());
                productListBean.setZeroBuy(productDetailBean.getZeroBuy());
                productListBeans.add(productListBean);
                carInBean.setProductListBeans(productListBeans);
                carInBeans.add(carInBean);
                bundle.putSerializable("key", (Serializable) carInBeans);
                bundle.putString("inviteCode", getShareCode);
                startActivity(ConfirmOrderActivity.class, bundle);
                break;
            case R.id.inShopLinearLayout:
//                bundle.putString("inType", 0 + "");
                bundle.putString("shopId", shopId);
//                startActivity(ShopDetailsActivity.class, bundle);
                startActivity(ShopHomeActivity.class, bundle);
                break;
            case R.id.sendIn:
                if (Check()) {
                    ToastUitl.showLong(this, "请先登录");
                    startActivity(LoginNewActivity.class);
                    return;
                }
                carAdd();
                break;
            case R.id.iv_tip:
                ShowReportDialog.showDialog(ProductDetailsActivity.this);
                break;
            case R.id.tv_preSale:
                ShowReportDialog.showTipDialog(ProductDetailsActivity.this);
                break;
            case R.id.ll_guarantee:
                //保障
                ShowReportDialog.showGuaranteeDialog(this);
                break;
            case R.id.ll_parameter:
                //参数
                if (productDetailBean != null) {
                    // ShowReportDialog.showParameterDialog(this);
                    ShowReportDialog.showParameterDialog(this, productDetailBean.getPress(), productDetailBean.getSubject(),
                            productDetailBean.getCode(), productDetailBean.getClasses(), productDetailBean.getPrice() + "", productDetailBean.getDiscountPrice() + "");

                }

                break;
        }
    }

    public void OnShareElves(View view) {
//        IWXAPI mWXApi = WXAPIFactory.createWXAPI(ProductDetailsActivity.this, "wx494d5354ef916936", true);
//        mWXApi.registerApp("wx494d5354ef916936");

        if (!MyApp.mWxApi.isWXAppInstalled()) {
            return;
        }

       /* WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://flyer.pereal.cn/sunday/weixin/flyer/findOne?flyerId=" + getIntent().getExtras().getString("flyerId") + "&shareMemberId=" + getIntent().getExtras().getString("memberId");
        LogUtil.e(TAG, "" + webpage.webpageUrl);
        WXMediaMessage msg = new WXMediaMessage(webpage);*/

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
//        if (bitmap != null) {
//            msg.thumbData = getWXThumb(bitmap).toByteArray();
//        }
//        promptDialog.dismiss();
//        getWXThumb(bitmap).toByteArray()

                SendMessageToWX.Req req = new SendMessageToWX.Req();
//WXSceneTimeline朋友圈    WXSceneSession聊天界面
                req.scene = SendMessageToWX.Req.WXSceneSession;//聊天界面  1 == 1 ? SendMessageToWX.Req.WXSceneTimeline :
                req.message = msg;
                req.transaction = String.valueOf(System.currentTimeMillis());
                MyApp.mWxApi.sendReq(req);


            }
        });
        viewToBitmapUtil.getBitmapFromView(ProductDetailsActivity.this, view);


//        MyApp.ShareId = getIntent().getExtras().getString("flyerId");
    }

    public static final int IMAGE_LENGTH_LIMIT = 6291456;

    private static ByteArrayOutputStream getWXThumb(Bitmap resource) {
        Bitmap thumb = Bitmap.createScaledBitmap(resource, 100, 100, true);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int quality = 90;
        int realLength = Util.getBitmapByteSize(resource.getWidth(), resource.getHeight(), Bitmap.Config.ARGB_8888);
        if (realLength > IMAGE_LENGTH_LIMIT) {
            quality = (int) (IMAGE_LENGTH_LIMIT * 1f / realLength * 100);
        }
        if (quality < 75) {
            quality = 75;
        }
        resource.compress(Bitmap.CompressFormat.PNG, quality, output);
        output.reset();
        thumb.compress(Bitmap.CompressFormat.PNG, 85, output);
        return output;
    }


    // 添加到购物车
    private void carAdd() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", "" + productId);
        map.put("count", "" + 1);
        map.put("zeroBuy", zeroBuy + "");
        if (!StringUtils.isEmpty(getShareCode)) {
            map.put("inviteCode", getShareCode);
        }

        mPresenter.carAdd(map);
    }

    /**
     * 商品详情
     */
    @OnClick(R.id.ll_view)
    void ll_view() {
        tv_text.setTextColor(getResources().getColor(R.color.view_color_FA7E66));
        tv_text2.setTextColor(getResources().getColor(R.color.gray1));
        tv_text3.setTextColor(getResources().getColor(R.color.gray1));
        tv_view.setVisibility(View.VISIBLE);
        tv_view2.setVisibility(View.INVISIBLE);
        tv_view3.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.VISIBLE);
        ImageViewAllShow.setVisibility(View.GONE);
        showLinearLayout.setVisibility(View.GONE);
    }

    /**
     * 购物指南
     */
    @OnClick(R.id.ll_view2)
    void ll_view2() {
        tv_text.setTextColor(getResources().getColor(R.color.gray1));
        tv_text2.setTextColor(getResources().getColor(R.color.view_color_FA7E66));
        tv_text3.setTextColor(getResources().getColor(R.color.gray1));
        tv_view.setVisibility(View.INVISIBLE);
        tv_view2.setVisibility(View.VISIBLE);
        tv_view3.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.GONE);
        ImageViewAllShow.setVisibility(View.VISIBLE);
        showLinearLayout.setVisibility(View.GONE);
    }

    /**
     * 商品参数
     */
    @OnClick(R.id.ll_view3)
    void ll_view3() {
        tv_text.setTextColor(getResources().getColor(R.color.gray1));
        tv_text2.setTextColor(getResources().getColor(R.color.gray1));
        tv_text3.setTextColor(getResources().getColor(R.color.view_color_FA7E66));
        tv_view.setVisibility(View.INVISIBLE);
        tv_view2.setVisibility(View.INVISIBLE);
        tv_view3.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        ImageViewAllShow.setVisibility(View.GONE);
        showLinearLayout.setVisibility(View.VISIBLE);
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

    private void ImageViewAllShow(String detailsUrl) {
        //webview
        //启用支持javascript
        //封装头文件,将图片铺满屏幕显示
        String sHead = "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />" +
                "<style>img{max-width:100% !important;height:auto !important;}</style>"
                + "<style>body{max-width:100% !important;}</style>" + "</head><body>";
        WebSettings settings = ImageViewAllShow.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDomStorageEnabled(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ImageViewAllShow.loadDataWithBaseURL(null, sHead + detailsUrl + "</body></html>", "text/html", "utf-8", null);
    }

    // 获取所有类型
    private void productList() {
        Map<String, Object> map = new HashMap<>();
        map.put("from", 3);
        map.put("recommend", 1);
        map.put("pageSize", 20);
        map.put("shopId", shopId);
//        map.put("grade", grade);
        mPresenter.productList(map);
    }

    private void initData() {
        productList.clear();
        for (int i = 0; i < 10; i++) {
            productList.add("");
        }
//        initRecyclerView(productList);
    }

    //    下面是网络接口的回调
    ProductDetailBean productDetailBean;

    @BindView(R.id.tv_openBooking)
    TextView tv_openBooking;

    @BindView(R.id.iv_header)
    CommonImage iv_header;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.iv_book)
    CommonImage iv_book;

    @BindView(R.id.tv_book_name)
    TextView tv_book_name;

    @BindView(R.id.tv_book_price)
    TextView tv_book_price;

    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @BindView(R.id.ll_act_bg)
    View ll_act_bg;

    private String fileUrl;

    private void showFile() {
        ll_ebook.setOnClickListener(v -> PermissionX.init(ProductDetailsActivity.this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).request(
                (allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
                        String filePath = BOOK_SAVE_PATH + fileName;
                        LogUtil.e(TAG, "filePath=" + filePath);
                        File file = new File(filePath);
                        if (file.exists()) {
                            Intent intent = new Intent(ProductDetailsActivity.this, X5TbsFileServicePage.class);
                            intent.putExtra("filePath", filePath);
                            startActivity(intent);
                        } else {
                            downLoad(fileUrl, BOOK_SAVE_PATH + fileName);
                        }
                    } else {
                        showToast("请开启权限才能打开文件进行查看");
                    }
                }
        ));
    }

    @Override
    public void GetDataSuc(ProductDetailBean DataBean) {

        ProductDetailBean.EBook eBook = DataBean.geteBook();
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


//        @BindView(R.id.iv_header)
//        ImageView iv_header;
//
//    @BindView(R.id.showSharCancel)
//    TextView showSharCancel;
//
//    @BindView(R.id.showSharCode)
//    ImageView showSharCode;
//
//    @BindView(R.id.showSharDelete)
//    TextView showSharDelete;
//
//    @BindView(R.id.showSharName)
//    TextView showSharName;
//
//    @BindView(R.id.showSharPrice)
//    TextView showSharPrice;

        //TODO 这里展示分享的信息
       /* GlideImageLoader.create(showSharImg).load(ApiService.IMAGE + DataBean.getImage(), new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));

        showSharPrice.setText("¥" + StringUtils.isNull((Double.parseDouble(DataBean.getDiscountPrice() + "") / 100) + ""));
        showSharDelete.setText("¥" + StringUtils.isNull((Double.parseDouble(DataBean.getPrice() + "") / 100) + ""));
        showSharDelete.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        showSharName.setText(StringUtils.isNull(DataBean.getName()));*/

        String userName = SharedPreferencesUtil.get(ProductDetailsActivity.this, KeyCom.NICKNAME, "---");
        String logo = SharedPreferencesUtil.get(ProductDetailsActivity.this, KeyCom.USER_LOGO, "");
        String discount = "¥" + StringUtils.isNull((Double.parseDouble(DataBean.getDiscountPrice() + "") / 100) + "");
        String price = "¥" + StringUtils.isNull((Double.parseDouble(DataBean.getPrice() + "") / 100) + "");
        LogUtil.e(TAG, "logo==" + logo);
        if (!StringUtils.isEmpty(logo)) {
            if (logo.contains("http")) {
                iv_header.setImageURI(logo);
            } else {
                iv_header.setImageURI(ApiService.IMAGE + logo);
            }
        }

        tv_name.setText(userName);
        iv_book.setImageURI(ApiService.IMAGE + DataBean.getImage());
        tv_book_name.setText(DataBean.getName());
        tv_book_price.setText(discount);
        tv_delete.setText(price);
        tv_delete.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线


//        dialog.shareDialog(this, ApiService.IMAGE + productDetailBean.getImage(),
//                ApiService.IMAGE + productDetailBean.getImage(), userName, productDetailBean.getName(), discount, price, shareBitmap);


        productDetailBean = DataBean;
        tv_productName.setText(StringUtils.isNull(DataBean.getName()));
//        if (productDetailBean.getZeroBuy() == 1) {
//            DataBean.setDiscountPrice(0);
//        }
        tv_price.setText("¥" + StringUtils.isNull((Double.parseDouble(DataBean.getDiscountPrice() + "") / 100) + ""));
        tv_discount.setText("¥" + StringUtils.isNull((Double.parseDouble(DataBean.getPrice() + "") / 100) + ""));
        tv_discount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
//        tv_sold.setText("已售" + StringUtils.isNull(DataBean.getSoldCount() + ""));
        if (DataBean.getLimit() == 0) {
            tv_purchasing.setVisibility(View.GONE);
        } else {
            tv_purchasing.setVisibility(View.VISIBLE);
            tv_purchasing.setText("限购" + DataBean.getLimit() + "份");
        }

        initWebView(DataBean.getDescribe().getContent());
        if (DataBean.getPre() == 1) {
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
            Long preTimeDiff = DataBean.getPreTimeDiff();
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
        shopId = DataBean.getShopId() + "";
        shopDetail();
//        shopId
        bookCode.setText("205547");
        sendShop.setText(DataBean.getPress());
        types.setText(DataBean.getSubject());
        grade.setText(DataBean.getClasses());
        tv_config.setText(DataBean.getSubject() + " " + DataBean.getClasses() + " " + DataBean.getPress());
        productList();

        bannerList.clear();
        for (int i = 0; i < DataBean.getImages().size(); i++) {
            String image = DataBean.getImages().get(i);
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





     /*   mMZBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();*/
    }


    private InstallUtils.DownloadCallBack downloadCallBack;

    private void downLoad(String url, String filePath) {


        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                startProgressDialog("正在打开");

            }

            @Override
            public void onComplete(String path) {
                stopProgressDialog();
                Log.e(TAG, "complete-path==" + path);
                Intent intent = new Intent(ProductDetailsActivity.this, X5TbsFileServicePage.class);
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

    private CountDownTimer countDownTimer;

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


    ArrayList<String> bannerList = new ArrayList<>();

    public static class BannerViewHolder implements MZViewHolder<String> {
        private CommonImage mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
//            View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, null);
            View view = LayoutInflater.from(context).inflate(R.layout.item_product_banner, null);
            mImageView = (CommonImage) view.findViewById(R.id.mBanner_ImageView);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
//            mImageView.setImageResource(data);
//            GlideImageLoader.create(mImageView).load(ApiService.IMAGE + data, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            mImageView.setImageURI(ApiService.IMAGE + data);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (data.getPageType() != 1) {
//                        Log.e("xxxxxxxx", "--------");
//                        Intent intent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("Name", "最新资讯");
//                        bundle.putString("Url", "" + data.getPageUrl());
//                        intent.setClass(context, OnlyWebViewActivity.class);
//                        intent.putExtras(bundle);
//                        context.startActivity(intent);
//                    }
                }
            });
        }

    }


    @BindView(R.id.bookCode)
    TextView bookCode;

    @BindView(R.id.sendShop)
    TextView sendShop;

    @BindView(R.id.types)
    TextView types;

    @BindView(R.id.grade)
    TextView grade;

    @BindView(R.id.showLinearLayout)
    LinearLayout showLinearLayout;


    @BindView(R.id.ImageViewAllShow)
    WebView ImageViewAllShow;

    @BindView(R.id.onTime)
    TextView onTime;

    @BindView(R.id.tv_storeAddress)
    TextView tv_storeAddress;

    @BindView(R.id.iv_storeImage)
    ImageView iv_storeImage;


    @Override
    public void GetDataFail(String Message) {
        if (Message!=null) {
            ToastUitl.showShort(MyApp.getInstance(), Message);
        }
    }

    String ShopName = "";

    @Override
    public void GetShopSuc(ShopBean DataBean) {
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

    @Override
    public void GetShopFail(String Message) {
        ToastUitl.showShort(this, Message + "");
    }


    @Override
    public void SendSuc(ProductListBean DataBean) {
        mProductList.clear();
        mProductList.addAll(DataBean.getList());
        beanBaseRecyclerAdapter.notifyDataSetChanged();
        stopProgressDialog();
        rl_detail.setVisibility(View.VISIBLE);
    }

    @Override
    public void SendSucFail(String Message) {

    }

    @Override
    public void CardAddSuc(String DataBean) {
        ToastUitl.showShort(this, "加入购物车成功!");
    }

    @Override
    public void StringRol(String DataBean) {
        ImageViewAllShow(DataBean);
    }

    @Override
    public void CardAddFail(String Message) {
    }


    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

//    private void initRecyclerView(List<String> productList) {
//        baseRecyclerAdapter = new BaseRecyclerAdapter<String>(mContext, productList, R.layout.layout_product_details_item) {
//            @Override
//            public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {
//
//            }
//        };
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setAdapter(baseRecyclerAdapter);
//    }


}