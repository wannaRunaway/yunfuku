package com.aifubook.book.shop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.activity.main.shopdetail.ShopDetailListActivity;
import com.aifubook.book.adapter.ShopHomeAdapter;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.ShopHomeBean;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.aifubook.book.home.MapLineShowActivity;
import com.aifubook.book.login.LoginNewActivity;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.utils.ContextUtil;
import com.aifubook.book.utils.StatusBarUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.BigDecimalUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentLocationUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

import static com.tencent.map.geolocation.TencentLocationRequest.REQUEST_LEVEL_NAME;

public class ShopHomeActivity extends BaseActivity<ShopHomePresenter> implements ShopHomeView, SwipeRefreshLayout.OnRefreshListener, TencentLocationListener {

    private static final String TAG = "ShopHomeActivity";
    private MySwipeRefresh mMySwipeRefresh;

    @BindView(R.id.root)
    View root;


    private TencentLocationManager mLocationManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shophome;
    }

    @Override
    public void initPresenter() {
        mPresenter = new ShopHomePresenter(this);
    }

    private String shopId;
    private ShopHomeAdapter shopHomeAdapter;
    private View topView;
    private TextView tv_num, tv_shopName, tv_time, tv_time_detail, tv_address, tv_distance, tv_open, tv_all;
    private ImageView iv_phone, iv_back;
    private LinearLayout ll_pics;
    private LinearLayout ll_location;

    @Override
    public void initView() {
        verifyStoragePermissions();

        topView = mInflater.inflate(R.layout.top_shop_home, null);
        tv_num = topView.findViewById(R.id.tv_num);
        tv_shopName = topView.findViewById(R.id.tv_shopName);
        ll_pics = topView.findViewById(R.id.ll_pics);
        tv_time = topView.findViewById(R.id.tv_time);
        tv_time_detail = topView.findViewById(R.id.tv_time_detail);
        tv_address = topView.findViewById(R.id.tv_address);
        tv_distance = topView.findViewById(R.id.tv_distance);
        iv_phone = topView.findViewById(R.id.iv_phone);
        tv_open = topView.findViewById(R.id.tv_open);
        iv_back = topView.findViewById(R.id.iv_back);
        ll_location = topView.findViewById(R.id.ll_location);
        tv_all = topView.findViewById(R.id.tv_all);

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("inType", "0");
//                bundle.putString("shopId", shopId+"");
//                startActivity(ShopDetailsActivity.class, bundle);
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentKey.Companion.getCLASSFICATION(), "");
                bundle.putString(IntentKey.Companion.getSHOPID(), shopId);
                bundle.putString(IntentKey.Companion.getGRADE(), "");
                bundle.putString(IntentKey.Companion.getFROMWHERE(), IntentKey.Companion.getFROMSHOPHOME());
                startActivity(ShopDetailListActivity.class, bundle);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(phone)) {
                    showWarn();
                }
            }
        });
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开地图
                if (StringUtils.isEmpty(lag + "") || StringUtils.isEmpty(lon + "")) {
                    return;
                }
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("lag", "" + lag);
                    bundle.putString("lon", "" + lon);
                    bundle.putString("name", "" + shopName);
                    bundle.putString("add", "" + address);
                    startActivity(MapLineShowActivity.class, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        View barView = topView.findViewById(R.id.fillStatusBarView);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = statusBarHeight;
        barView.setLayoutParams(layoutParams);

        shopId = getIntent().getExtras().getString("shopId");
        mMySwipeRefresh = new MySwipeRefresh(root, null);

        shopHomeAdapter = new ShopHomeAdapter();
        mMySwipeRefresh.setAdapter(shopHomeAdapter);

        RecyclerView recyclerView = mMySwipeRefresh.getRecyclerView();
        recyclerView.setBackground(ContextUtil.getResource(R.drawable.bg_shop_home));

        shopHomeAdapter.addHeaderView(topView);

        mMySwipeRefresh.setOnRefreshListener(this);
        shopHomeAdapter.getLoadMoreModule().setAutoLoadMore(true);
        shopHomeAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        shopHomeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNo++;
                getData();
            }
        });

        shopHomeAdapter.addChildClickViewIds(R.id.iv_addCart);
        shopHomeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ShopHomeBean.ShopHome shop = bookList.get(position);
                if (shop == null) {
                    return;
                }
                productId = shop.getId() + "";
                if (isLogin()) {
                    showToast("请先登录");
                    startActivity(LoginNewActivity.class);
                    return;
                }
                startProgressDialog();
                carAdd();

            }
        });
        shopHomeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                ShopHomeBean.ShopHome shop = bookList.get(position);
                if (shop == null) {
                    return;
                }
                productId = shop.getId() + "";
                Bundle bundle = new Bundle();
                bundle.putString("productId", productId);
                startActivity(ProductDetailsActivity.class, bundle);
            }
        });
        mMySwipeRefresh.setRefreshing(true);
        getTopData();

    }

    private String productId;

    // 获取店铺详情的接口
    private void carAdd() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", "" + productId);
        map.put("count", "" + 1);
        mPresenter.carAdd(map);
    }

    private void showWarn() {
        AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(this);
        affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.dialog_affirm_btn) {
                    PermissionX.init(ShopHomeActivity.this).permissions(Manifest.permission.CALL_PHONE).request(new RequestCallback() {
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
        affirmMessageDialog.show("是否确认拨打？");
    }


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {            //检查是否已经给了权限
            int permission = ActivityCompat.checkSelfPermission(MyApp.getInstance(),
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            int permission1 = ActivityCompat.checkSelfPermission(MyApp.getInstance(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission != PackageManager.PERMISSION_GRANTED && permission1 != PackageManager.PERMISSION_GRANTED) {
                //没有给权限
                // Log.e("permission","动态申请");                //参数分别是当前活动，权限字符串数组，requestcode
                ActivityCompat.requestPermissions(ShopHomeActivity.this, PERMISSIONS_STORAGE, 100);
            } else {
//                ToastUtil.showToast("开启定位权限才能查看附近店铺",false);
                mLocationManager = TencentLocationManager.getInstance(this);
                TencentLocationRequest request = TencentLocationRequest.create();
                request.setRequestLevel(REQUEST_LEVEL_NAME);
                mLocationManager.requestSingleFreshLocation(request, this, Looper.getMainLooper());
            }
        }else{
            mLocationManager = TencentLocationManager.getInstance(this);
            TencentLocationRequest request = TencentLocationRequest.create();
            request.setRequestLevel(REQUEST_LEVEL_NAME);
            mLocationManager.requestSingleFreshLocation(request, this, Looper.getMainLooper());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationManager = TencentLocationManager.getInstance(this);
                TencentLocationRequest request = TencentLocationRequest.create();
                request.setRequestLevel(REQUEST_LEVEL_NAME);
                mLocationManager.requestSingleFreshLocation(request, this, Looper.getMainLooper());
            } else {
                ToastUtil.showToast("开启定位权限才能看到距离", true);
                getTopData();
            }
        }
    }

    private void getTopData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", shopId + "");
        mPresenter.getShopTopDetail(map);
    }


    private int pageNo = 1;
    private int pageSize = 10;

    private void getData() {

        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");
        map.put("shopId", shopId + "");
        mPresenter.getShopDetail(map);
    }

    private List<ShopHomeBean.ShopHome> bookList = new ArrayList<>();

    @Override
    public void showShopData(ShopHomeBean bean) {
        if(bean==null){
            return;
        }
        mMySwipeRefresh.setRefreshing(false);
        shopHomeAdapter.getLoadMoreModule().setEnableLoadMore(true);
        List<ShopHomeBean.ShopHome> dataList = bean.getList();

        if (dataList == null) {
            if (pageNo > 1) {
                shopHomeAdapter.getLoadMoreModule().loadMoreEnd();
            }
            return;
        }

        if (pageNo == 1) {
            bookList.clear();
            shopHomeAdapter.setList(dataList);
        } else {
            shopHomeAdapter.addData(dataList);
        }
        if (dataList.size() < 10) {
            shopHomeAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            shopHomeAdapter.getLoadMoreModule().loadMoreComplete();
        }

        if (dataList.size() > 0) {
            bookList.addAll(dataList);
        }

        tv_num.setText("(" + bookList.size() + ")");

    }

    @Override
    public void getDataError(String msg) {
        if(!StringUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }

    private Double lag, lon;
    private String phone;
    private String shopName,address;

    @Override
    public void showTopData(ShopBean bean) {
        getData();
        shopName = bean.getName();
        List<Double> location = bean.getLocation();
        address = bean.getAddress();
        phone = (String) bean.getPhone();
        boolean opening = bean.isOpening();
//        String startHour = bean.getStartHour();
//        String endHour = bean.getEndHour();
//        tv_open.setText(opening ? "营业中" : "暂未营业");
        tv_open.setText("营业中");
//        tv_time.setText(("") + "-" + endHour);
//        tv_time_detail.setText("周一至周日 " + startHour + "-" + endHour + "正常营业");

        ArrayList<String> images = (ArrayList<String>) bean.getImages();

        if (images != null) {
            ll_pics.removeAllViews();
            for (int i = 0; i < images.size(); i++) {
                View item = mInflater.inflate(R.layout.item_shop_pic, null);
                CommonImage image = item.findViewById(R.id.iv_book);
                String img = ApiService.IMAGE + images.get(i);
                image.setImageURI(img);
                int finalI = i;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("pics",images);
                        bundle.putInt("pos", finalI);
                        startActivity(ShopPicActivity.class,bundle);
                    }
                });

                ll_pics.addView(item);
            }
        }

        tv_shopName.setText(shopName);
        tv_address.setText(address);

        if (location != null && location.size() > 0) {
            lag = location.get(0);
            lon = location.get(1);
            if (myLong > 0) {
                Double dis = TencentLocationUtils.distanceBetween(myLat, myLong, lag, lon);
                // addressDis.setText("距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "km");
                if (dis > 1000) {
                    tv_distance.setText("距您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "千米");
                } else {
                    tv_distance.setText("距您" + BigDecimalUtil.getFixedPointNum(dis.toString(), 2) + "米");
                }
            }

        }


    }

    @Override
    public void showTopDataError(String msg) {

    }

    @Override
    public void CardAddSuc(String dataBean) {
        stopProgressDialog();
        ToastUitl.showShort(this, "加入购物车成功!");

    }

    @Override
    public void CardAddFail(String message) {
        stopProgressDialog();

        ToastUitl.showShort(this, "加入购物车失败");

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        getData();
    }

    private Double myLong, myLat;

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        myLong = tencentLocation.getLongitude();
        myLat = tencentLocation.getLatitude();
        getTopData();

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
