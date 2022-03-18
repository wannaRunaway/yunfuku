package com.aifubook.book.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.webview.TeacherWebviewActivity;
import com.aifubook.book.adapter.HomeAdapter;
import com.aifubook.book.adapter.HomeCategoryAdapter;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseFragment;
import com.aifubook.book.bean.HomeCategoryBean;
import com.aifubook.book.bean.HomeModel;
import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.dialog.ShowReportDialog;
import com.aifubook.book.groupon.GrouponActivity;
import com.aifubook.book.home.address.ShopListActivity;
import com.aifubook.book.home.search.SearchActivity;
import com.aifubook.book.home.shop.ShopDetailsActivity;
import com.aifubook.book.keycontent.KeyCom;
import com.aifubook.book.login.LoginNewActivity;
import com.aifubook.book.product.ProductDetailsActivity;
import com.aifubook.book.scan.ScanQRActivity;
import com.aifubook.book.shop.ShopHomeActivity;
import com.aifubook.book.type.TypeModel;
import com.aifubook.book.utils.LocationUtil;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.aifubook.book.utils.StatusBarUtil;
import com.aifubook.book.view.MySwipeRefresh;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.ms.MyCsBannerViewPager;
import com.jiarui.base.utils.BigDecimalUtil;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentLocationUtils;

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

import static com.tencent.map.geolocation.TencentLocationRequest.REQUEST_LEVEL_NAME;

public class HomeNFragment extends BaseFragment implements HomeModel.OnCallBack, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, TencentLocationListener, View.OnClickListener {

    private static final String TAG = "HomeNFragment";
    private static final int SHOW_ADDRESS_LOCAITON = 0x33;
    private MySwipeRefresh mMySwipeRefresh;
    private HomeModel homeModel;
    private int type_category = 0x11;
    private int type_product = 0x12;
    private int type_scene = 0x13;
    private int type_near = 0x14;
    private int type_recommend = 0x15;
    private int type_home_category = 0x16;
    private int type_bind_chief = 0x17;
    private int type_select_shop = 0x18;
    private static final int READ_CAMERA_CODE = 0x19;
    private int type_add_cart = 0x20;
    private static final int REQUEST_PERMISSION_LOCATION = 0x21;
    private static final int REQUEST_PERMISSION_CAMERA = 0x22;
    private static final int START_REQUEST_CAMERA = 0x23;


    private LinearLayout ll_grade;
    private int screenHeight;


    @Override
    protected int setContentView() {
        return R.layout.fragment_homen;
    }

    @Override
    protected void initView() {

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;// 屏幕宽度(像素)
        screenHeight = metric.heightPixels;// 屏幕高度(像素)

//        verifyLocationPermissions();
        ll_grade = findViewById(R.id.ll_grade);


        init();
        initAdapter();
        initTopView();
        LogUtil.e(TAG, "initView");
//        loadData();

        boolean locationEnabled = LocationUtil.isLocationEnabled(getActivity());

        LogUtil.e(TAG, "locationEnabled==" + locationEnabled);

        if (!locationEnabled) {
            ShowReportDialog dialog = new ShowReportDialog();
            dialog.showLocationDialog(getActivity());
            dialog.setOnClickListener(new ShowReportDialog.OnClickListener() {
                @Override
                public void onConfirm() {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(intent);
                }

                @Override
                public void onCancel() {
                    LogUtil.e(TAG, "onCancel getShopForHome");
                    getShopForHome("", "");
                }
            });
        } else {
            if (isFirstLoad) {
                verifyLocationPermissions();
            }
        }

    }

    private boolean isFirstLoad = true;


    public void verifyLocationPermissions() {

        PermissionX.init(HomeNFragment.this).permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, @NonNull @NotNull List<String> grantedList, @NonNull @NotNull List<String> deniedList) {
                if (allGranted) {
                    TencentLocationManager mLocationManager = TencentLocationManager.getInstance(mActivity);
                    TencentLocationRequest request = TencentLocationRequest.create();
                    request.setRequestLevel(REQUEST_LEVEL_NAME);
                    mLocationManager.requestSingleFreshLocation(request, HomeNFragment.this, Looper.getMainLooper());
                } else {
                    ToastUtil.showToast("开启定位权限才能查看附近门店", true);
                    getShopForHome("", "");
                    Intent intent = new Intent(getActivity(), ShopListActivity.class);
                    intent.putExtra("choose_address", 1);
                    startActivityForResult(intent, SHOW_ADDRESS_LOCAITON);

                }
            }
        });
    }


    private HomeAdapter homeAdapter;

    private void initAdapter() {

        homeAdapter = new HomeAdapter();
        mMySwipeRefresh.setAdapter(homeAdapter);
        mMySwipeRefresh.setOnRefreshListener(this);
        homeAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        homeAdapter.getLoadMoreModule().setAutoLoadMore(true);
        homeAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                ProductListBean.list list = mProductLists.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("productId", "" + list.getId());
                startActivity(ProductDetailsActivity.class, bundle);
//                startActivity(ProductDetailsNewActivity.class, bundle);


            }
        });

        homeAdapter.addChildClickViewIds(R.id.iv_addCart);
        homeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ProductListBean.list list = mProductLists.get(position);
                if (checkLogin()) {
                    ToastUtil.showToast("请先登录", false);
                    startActivity(LoginNewActivity.class);
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("productId", "" + list.getId());
                map.put("count", "" + 1);
                homeModel.addCart(map, type_add_cart);

            }
        });
    }

    private TextView tv_search, tv_address_name, tv_address_dis, tv_address_location, tv_children;
    //    private MZBannerView banner;
    private LinearLayout ll_near, ll_recommend, ll_third, ll_category1, ll_category2;
    private CommonImage iv_address, cimage1, cimage2, cimage3, cimage4;
    //    private RecyclerView top_recycleView;
    private HomeCategoryAdapter homeCategoryAdapter;
    private LinearLayout ll_category, ll_recom, ll_ctop;

    private void initTopView() {

        View topView = mInflater.inflate(R.layout.topview_homen, null);
        ll_recom = topView.findViewById(R.id.ll_recom);
        tv_search = topView.findViewById(R.id.tv_search);
        ll_topView = topView.findViewById(R.id.ll_topView);
//        banner = topView.findViewById(R.id.banner);
        ll_near = topView.findViewById(R.id.ll_near);
        tv_address_name = topView.findViewById(R.id.tv_address_name);
        tv_address_dis = topView.findViewById(R.id.tv_address_dis);
        tv_address_location = topView.findViewById(R.id.tv_address_location);
        iv_address = topView.findViewById(R.id.iv_address);
        ll_recommend = topView.findViewById(R.id.ll_recommend);
        ll_third = topView.findViewById(R.id.ll_third);
        tv_children = topView.findViewById(R.id.tv_children);
       /* top_recycleView=topView.findViewById(R.id.top_recycleView);
        homeCategoryAdapter = new HomeCategoryAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);//第二个参数为网格的列数
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        top_recycleView.setLayoutManager(linearLayoutManager);
        top_recycleView.setAdapter(homeCategoryAdapter);*/
        ll_category1 = topView.findViewById(R.id.ll_category1);
        ll_category2 = topView.findViewById(R.id.ll_category2);
        cimage1 = topView.findViewById(R.id.cimage1);
        cimage2 = topView.findViewById(R.id.cimage2);
        cimage3 = topView.findViewById(R.id.cimage3);
        cimage4 = topView.findViewById(R.id.cimage4);
        ll_category = topView.findViewById(R.id.ll_category);
        ll_ctop = topView.findViewById(R.id.ll_ctop);

        homeAdapter.addHeaderView(topView);
        ll_near.setOnClickListener(this);
        rl_grade_top.setOnClickListener(this);
        tv_children.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        iv_address.setOnClickListener(this);



        ll_category.post(new Runnable() {
            @Override
            public void run() {

                ll_ctop.post(new Runnable() {
                    @Override
                    public void run() {
                        changeHeight = ll_ctop.getHeight();
                        childHeight = ll_category.getBottom() + ll_ctop.getHeight() + ll_category.getHeight() / 2;
                        LogUtil.e(TAG, "childHeight=" + childHeight);
                        setMargins(ll_children, ll_children.getLeft(), childHeight - scrollY, 0, 0);
                    }
                });

            }
        });


        mMySwipeRefresh.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                LogUtil.e(TAG, "dy==" + dy);
              /*  ll_children.setVisibility(View.INVISIBLE);
                if (!hasList && mProductLists.size() == 0) {
                    int hh = screenHeight - ll_children.getBottom();
                    LogUtil.e(TAG, "hh=" + hh);
                    int h = ll_children.getHeight() / 2;
                    scrollY = scrollY - hh - h;
                }
                scrollY += dy;
                LogUtil.e(TAG, "scrollY==" + scrollY);
                if (childHeight > 0) {
                    setMargins(ll_children, ll_children.getLeft(), childHeight - scrollY, 0, 0);
                }*/
                scrollTop = scrollTop + dy;
//                LogUtil.e(TAG, "scrollY=" + scrollTop);
                if (scrollTop > 2000) {
                    rl_to_top.setVisibility(View.VISIBLE);
                } else {
                    rl_to_top.setVisibility(View.GONE);
                }


            }
        });


    }

    int scrollTop;

    private int changeHeight;

    private int childHeight;

    private View rl_grade_top, iv_triangle;
    private TextView tv_grade_top;
    private ImageView iv_scan;
    private LinearLayout ll_children;
    private int scrollY = 0;//距离顶部距离
    private View rl_to_top,ll_topView;

    private void init() {

        View root = findViewById(R.id.root);
        rl_to_top = findViewById(R.id.rl_to_top);
        iv_scan = findViewById(R.id.iv_scan);
        ll_children = findViewById(R.id.ll_children);
        iv_triangle = findViewById(R.id.iv_triangle);
        rl_grade_top = findViewById(R.id.rl_grade_top);
        tv_grade_top = findViewById(R.id.tv_grade_top);
        View barView = findViewById(R.id.fillStatusBarView);
        LinearLayout ll_top = findViewById(R.id.ll_top);
        LinearLayout ll_pop = findViewById(R.id.ll_pop);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(getActivity());
        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = statusBarHeight;
        barView.setLayoutParams(layoutParams);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);//第二个参数为网格的列数
        int space = getResources().getDimensionPixelOffset(R.dimen._10dp);
        mMySwipeRefresh = new MySwipeRefresh(root, layoutManager);
//        mMySwipeRefresh.setGridSpace(space);
        TypeModel model = new TypeModel();
        homeModel = new HomeModel(model);
        homeModel.setOnCallBackListener(this);
        iv_scan.setOnClickListener(this);
        rl_to_top.setOnClickListener(this);

        ll_top.post(new Runnable() {
            @Override
            public void run() {
                int height = ll_top.getBottom();
                LogUtil.e(TAG, "height==" + height);

                setMargins(ll_pop, ll_pop.getLeft(), height, 0, 0);
            }
        });

        CommonImage iv_group = findViewById(R.id.iv_group);

        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.iv_group))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        iv_group.setController(controller);

        iv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(GrouponActivity.class);

//                showLoadingDialog();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Bitmap bitmap = BitmapUtil.getBitmap("https://yfk.oss-cn-beijing.aliyuncs.com/abac4db50db7b6a55f4a6c1d4ae0c7b8151b244f.jpg");
//                        mActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                closeLoadingDialog();
//                                MiniUtil.shareMiniToWx(bitmap,"5567");
//                            }
//                        });
//                    }
//                }).start();


            }

        });


    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    private boolean isShow, childShow;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_to_top:
                homeAdapter.getRecyclerView().smoothScrollToPosition(0);
                break;
            case R.id.iv_address:
                Bundle bundle1 = new Bundle();
                bundle1.putString("shopId", shopId);
                startActivity(ShopHomeActivity.class, bundle1);
                break;
            case R.id.iv_scan:
                startScan();
                break;
            case R.id.tv_search:
                Bundle bundle = new Bundle();
                bundle.putString("shopId", shopId);
                startActivity(SearchActivity.class, bundle);

                break;
            case R.id.ll_near:
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                startActivityForResult(intent, SHOW_ADDRESS_LOCAITON);
                break;
            case R.id.rl_grade_top:
                if (!isShow) {
                    iv_triangle.setVisibility(View.VISIBLE);
                    ll_grade.setVisibility(View.VISIBLE);
                    ll_children.setVisibility(View.INVISIBLE);
                } else {
                    iv_triangle.setVisibility(View.INVISIBLE);
                    ll_grade.setVisibility(View.INVISIBLE);
                }
                isShow = !isShow;
                break;
            case R.id.tv_children:
              /*  if (!childShow) {
                    showChildChoose(childrenList);
                    ll_children.setVisibility(View.VISIBLE);
                } else {
                    ll_children.setVisibility(View.INVISIBLE);
                }
                childShow = !childShow;*/
                ShowReportDialog reportDialog = new ShowReportDialog();
                reportDialog.setOnValueListener(new ShowReportDialog.OnValueClickListener() {
                    @Override
                    public void onConfirm(Object data) {
                        TypeBean.ChildrenBean bean = (TypeBean.ChildrenBean) data;
                        thirdBeans = bean.getChildren();
                        String name = bean.getName();
                        tv_children.setText(name);
                        showThird();
                        categoryId = bean.getId() + "";
                        pageNo = 1;
                        getProductLists();

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                reportDialog.showListDialog(getActivity(), childrenList);
                break;
        }
    }

//    private void showChildChoose(List<TypeBean.ChildrenBean> childrenList) {
//
//        ll_children.removeAllViews();
//        for (int i = 0; i < childrenList.size(); i++) {
//            View item_grade = mInflater.inflate(R.layout.item_grade, null);
//            TextView tv_grade = item_grade.findViewById(R.id.tv_grade);
//            TypeBean.ChildrenBean childrenBean = childrenList.get(i);
//            String name = childrenBean.getName();
//            tv_grade.setText(name);
//            item_grade.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    thirdBeans = childrenBean.getChildren();
//                    String name = childrenBean.getName();
//                    tv_children.setText(name);
//                    showThird();
//                    categoryId = childrenBean.getId() + "";
//                    pageNo = 1;
//                    getProductLists();
//                    ll_children.setVisibility(View.GONE);
//                }
//            });
//            ll_children.addView(item_grade);
//        }
//
//    }


    @Override
    protected void loadData() {
        super.loadData();
        LogUtil.e(TAG, "loadData");
        homeModel.getHomeCategory(type_home_category);

    }

    @Override
    public void onRefresh() {
        isFirstLocation = true;
        pageNo = 1;
//        getProductLists();
//        verifyLocationPermissions();
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + SharedPreferencesUtil.get(mActivity, "SHOP_ID", ""));
        homeModel.shopDetail(map, type_select_shop);
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getProductLists();
    }

    // 获取首页轮播图
    private void getScene() {
        Map<String, Object> map = new HashMap<>();
        map.put("scene", 1);
        homeModel.getByScene(map, type_scene);
    }

    // 获取所有类型
    private void categoryAll() {
        Map<String, String> map = new HashMap<>();
        homeModel.categoryAll(map, type_category);
    }

    String categoryId = "";
    int Select = 0;
    int pageNo = 1;

    private void getProductLists() {
        Map<String, Object> map = new HashMap<>();
        map.put("from", 14);
        map.put("pageNo", pageNo + "");
        map.put("pageSize", 10 + "");
        map.put("categoryId", categoryId);
        map.put("shopId", shopId);
        homeModel.productLists(map, type_product);
    }

    private void getRecommendList() {
        Map<String, Object> map = new HashMap<>();
        map.put("from", 10);
        map.put("pageNo", 1 + "");
        map.put("pageSize", 100 + "");
        map.put("categoryId", "");
        map.put("shopId", shopId);
        homeModel.productLists(map, type_recommend);
    }

    List<ProductListBean.list> mProductLists = new ArrayList<>();


    @Override
    public void onNext(Object result, int type) {
//        if (result == null) {
//            return;
//        }
        if (type_scene == type) {
            getProductLists();
            if (result == null) {
                return;
            }
            onSceneDetail(result);
        } else if (type_category == type) {
            getRecommendList();
            if (result == null) {
                return;
            }
            List<TypeBean> typeBeans = (List<TypeBean>) result;
//            String categoryAll = new Gson().toJson(typeBeans);
//            LogUtil.e(TAG, "categoryAll=" +categoryAll);
            onCategoryResult(typeBeans);
        } else if (type_product == type) {
            closeLoadingDialog();
            if (result == null) {
                return;
            }
            ProductListBean listBean = (ProductListBean) result;
            LogUtil.e(TAG, "types=" + type + "==" + listBean.toString());
            onListData(listBean);

        } else if (type_near == type) {
            loadData();
            if (result == null) {
                return;
            }
            onNearResult(result);
        } else if (type_recommend == type) {
            getScene();
            if (result == null) {
                return;
            }
            onRecommendResult(result);
        } else if (type_home_category == type) {
            categoryAll();
            if (result == null) {
                return;
            }
            onHomeCategoryResult(result);
        } else if (type_bind_chief == type) {
            ToastUtil.showToast("您已绑定成功", false);
        } else if (type_select_shop == type) {
            if (result == null) {
                return;
            }
            showSelectShop(result);
        } else if (type_add_cart == type) {
            ToastUtil.showToast("加入购物车成功", false);
        }

    }

    private void showSelectShop(Object result) {
        loadData();
        ShopBean DataBean = (ShopBean) result;

        String shopAddress = DataBean.getAddress();
        shopId = DataBean.getId() + "";
        SharedPreferencesUtil.put(mActivity, "SHOP_ID", shopId + "");
        shopName = DataBean.getName();
        tv_address_location.setText(DataBean.getAddress());
        tv_address_name.setText(DataBean.getName());
        iv_address.setImageURI(ApiService.IMAGE + DataBean.getLogo());
        if (DataBean.getLocation().size() > 0) {
            String lag = DataBean.getLocation().get(0) + "";
            String lon = DataBean.getLocation().get(1) + "";

            String myLong = SharedPreferencesUtil.get(mActivity, "Long", "");
            String myLat = SharedPreferencesUtil.get(mActivity, "Lat", "");
            LogUtil.e(TAG, "lag=" + lag + " lon=" + lon + " Long=" + myLong + " Lat=" + myLat);
            if (!StringUtils.isEmpty(myLong) && !StringUtils.isEmpty(myLat)) {
                Double dis = TencentLocationUtils.distanceBetween(Double.parseDouble(myLat), Double.parseDouble(myLong), Double.parseDouble(lag), Double.parseDouble(lon));
                // addressDis.setText("距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "km");
                if (dis > 1000) {
                    tv_address_dis.setText("距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "千米");
                } else {
                    tv_address_dis.setText("距离您" + BigDecimalUtil.getFixedPointNum(dis.toString(), 2) + "米");
                }
            }
        }


    }

    private List<HomeCategoryBean> homeCategoryList = new ArrayList<>();

    private void onHomeCategoryResult(Object result) {

//        ll_category1.removeAllViews();
//        ll_category2.removeAllViews();
        List<HomeCategoryBean> list = (List<HomeCategoryBean>) result;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
//                View item = mInflater.inflate(R.layout.item_home_category, null);
//                CommonImage image = item.findViewById(R.id.mImageView);
                HomeCategoryBean homeCategoryBean = list.get(i);
                String pic = ApiService.IMAGE + homeCategoryBean.getLogo();
                if (i == 0) {
                    cimage1.setImageURI(pic);
                    cimage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startCategoryActivity(homeCategoryBean);

                        }
                    });
                } else if (i == 1) {
                    cimage2.setImageURI(pic);
                    cimage2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startCategoryActivity(homeCategoryBean);

                        }
                    });
                } else if (i == 2) {
                    cimage3.setImageURI(pic);
                    cimage3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startCategoryActivity(homeCategoryBean);

                        }
                    });
                } else if (i == 3) {
                    cimage4.setImageURI(pic);
                    cimage4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startCategoryActivity(homeCategoryBean);
                        }
                    });
                }

            }

        }


    }

    private void startCategoryActivity(HomeCategoryBean homeCategoryBean) {
        Bundle bundle = new Bundle();
        bundle.putString("inType", "1");
        bundle.putString("categoryId", homeCategoryBean.getId() + "");
        bundle.putString("shopId", shopId);
        startActivity(ShopDetailsActivity.class, bundle);
    }

    //    List<TypeBean> gradeList = new ArrayList<>();
//    List<TypeBean.ChildrenBean> childrenBeans = new ArrayList<>();
//    List<TypeBean.ChildrenBean.ChildrenBeans> thirdBeans = new ArrayList<>();
    List<TypeBean.ChildrenBean> childrenList;
    List<TypeBean.ChildrenBean.ChildrenBeans> thirdBeans;

    private JSONArray jsonArray;

    private int j = 0;

    private void onCategoryResult(List<TypeBean> typeBeans) {
        if (typeBeans == null) {
            return;
        }
//        gradeList.addAll(typeBeans);
//        childrenBeans.clear();
//        thirdBeans.clear();
//        childrenBeans.addAll(typeBeans.get(0).getChildren());
        childrenList = typeBeans.get(0).getChildren();
        //childrenBeans 是二级分类
        if (childrenList != null && childrenList.size() > 0) {
            TypeBean.ChildrenBean childrenBean = childrenList.get(0);
            tv_children.setText(childrenBean.getName());
        }


        if (typeBeans.size() > 0) {
            ll_grade.removeAllViews();
            jsonArray = null;
            jsonArray = new JSONArray();
            j = 0;
            for (int i = 0; i < typeBeans.size(); i++) {
                TypeBean bean = typeBeans.get(i);
                String name = bean.getName();
                Integer id = bean.getId();
//                LogUtil.e(TAG, "name=" + name);
                try {
                    if (name.contains("年级") || name.contains("中考冲刺")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", name);
                        jsonObject.put("id", id);
                        jsonArray.put(j, jsonObject);
                        j++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                View item_grade = mInflater.inflate(R.layout.item_grade, null);
                TextView tv_grade = item_grade.findViewById(R.id.tv_grade);
                tv_grade.setText(bean.getName());
                TypeBean.ChildrenBean be = childrenList.get(0);
                if (be != null) {
                    categoryId = be.getId() + "";
                }
                LogUtil.e(TAG, "categoryId=" + categoryId);
                item_grade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_grade.setVisibility(View.INVISIBLE);
                        iv_triangle.setVisibility(View.INVISIBLE);
                        //二级
                        tv_grade_top.setText(bean.getName());
                        childrenList = bean.getChildren();

                        //三级
                        tv_children.setText("");
                        ll_third.removeAllViews();
                        if (childrenList != null && childrenList.size() > 0) {
                            LogUtil.e(TAG, "children=" + childrenList.get(0).getName());
                            tv_children.setText(childrenList.get(0).getName() + "");
                            thirdBeans = childrenList.get(0).getChildren();
                            showThird();

                        }
                        List<TypeBean.ChildrenBean> childrenList = bean.getChildren();
                        if (childrenList != null && childrenList.size() > 0) {
                            TypeBean.ChildrenBean be = childrenList.get(0);
                            if (be != null) {
                                categoryId = be.getId() + "";
                            } else {
                                categoryId = "";
                            }
                        } else {
                            categoryId = "";
                        }
                        pageNo = 1;
                        if (!StringUtils.isEmpty(categoryId)) {
                            getProductLists();
                        } else {
                            hasList = false;
                            LogUtil.e(TAG, "clear1");
                            int hh = screenHeight - ll_children.getBottom();
                            LogUtil.e(TAG, "hh=" + hh);
                            scrollY = scrollY - hh;
                            mProductLists.clear();
                            homeAdapter.setList(mProductLists);

                        }
                    }
                });
                if (!StringUtils.isEmpty(name) && name.contains("年级")) {
                    ll_grade.addView(item_grade);
                }
            }
            String gradeList = jsonArray.toString();
            LogUtil.e(TAG, "gradeList=" + gradeList);
            SharedPreferencesUtil.put(getActivity(), "gradeList", gradeList);

        }
        if (childrenList != null && childrenList.size() > 0) {
            thirdBeans = childrenList.get(0).getChildren();
            showThird();

        }


    }

    private void showThird() {
        ll_third.removeAllViews();
        if (thirdBeans != null && thirdBeans.size() > 0) {
            for (int i = 0; i < thirdBeans.size(); i++) {
                TypeBean.ChildrenBean.ChildrenBeans childrenBeans = thirdBeans.get(i);
                LinearLayout ll = (LinearLayout) LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.third_item_book, null);
                TextView tv_bookName = ll.findViewById(R.id.tv_book_name);
                tv_bookName.setText(childrenBeans.getName());
                tv_bookName.setEnabled(true);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        categoryId = childrenBeans.getId() + "";
                        LogUtil.e(TAG, "click categoryId=" + categoryId);
                        pageNo = 1;
                        getProductLists();
                        for (int i = 0; i < thirdBeans.size(); i++) {
                            LinearLayout ll = (LinearLayout) ll_third.getChildAt(i);
                            TextView tv = ll.findViewById(R.id.tv_book_name);
                            tv.setEnabled(true);
                        }
                        tv_bookName.setEnabled(false);
                    }
                });
                tv_bookName.setEnabled(true);
                ll_third.addView(ll);
            }
        }
    }

    private void onRecommendResult(Object result) {

        ProductListBean listBean = (ProductListBean) result;
        if (listBean == null) {
            return;
        }
        List<ProductListBean.list> list = listBean.getList();
        if (list != null && list.size() > 0) {
            ll_recommend.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            ProductListBean.list bean = list.get(i);
            View item = mInflater.inflate(R.layout.item_recommend, null);
            CommonImage iv_book = item.findViewById(R.id.iv_book);
            TextView tv_book_name = item.findViewById(R.id.tv_book_name);
            TextView tv_discount = item.findViewById(R.id.tv_discount);
            TextView tv_price = item.findViewById(R.id.tv_price);
            tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            tv_discount.setText("￥" + (Double.parseDouble(bean.getDiscountPrice() + "") / 100) + "");
            tv_price.setText("￥" + (Double.parseDouble(bean.getPrice() + "") / 100) + "");
            tv_book_name.setText(bean.getName());
            iv_book.setImageURI(ApiService.IMAGE + bean.getImage());

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("productId", "" + bean.getId());
                    startActivity(ProductDetailsActivity.class, bundle);

                }
            });

            ll_recommend.addView(item);
        }


    }

    private String shopId, shopName;

    private void onNearResult(Object result) {
        isFirstLoad = false;
        NearShopBean DataBean = (NearShopBean) result;
        if (DataBean != null) {
            shopId = DataBean.getId() + "";
            SharedPreferencesUtil.put(mActivity, "SHOP_ID", shopId + "");
            shopName = DataBean.getName();
            tv_address_location.setText(DataBean.getAddress());
            tv_address_name.setText(DataBean.getName());
            iv_address.setImageURI(ApiService.IMAGE + DataBean.getLogo());
            if (DataBean.getLocation().size() > 0) {
                String lag = DataBean.getLocation().get(0) + "";
                String lon = DataBean.getLocation().get(1) + "";

                String myLong = SharedPreferencesUtil.get(mActivity, "Long", "");
                String myLat = SharedPreferencesUtil.get(mActivity, "Lat", "");
                LogUtil.e(TAG, "lag=" + lag + " lon=" + lon + " Long=" + myLong + " Lat=" + myLat);
                if (!StringUtils.isEmpty(myLong) && !StringUtils.isEmpty(myLat)) {
                    Double dis = TencentLocationUtils.distanceBetween(Double.parseDouble(myLat), Double.parseDouble(myLong), Double.parseDouble(lag), Double.parseDouble(lon));
                    // addressDis.setText("距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "km");
                    if (dis > 1000) {
                        tv_address_dis.setText("距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "千米");
                    } else {
                        tv_address_dis.setText("距离您" + BigDecimalUtil.getFixedPointNum(dis.toString(), 2) + "米");
                    }
                }
            }
        }

    }

    private void onSceneDetail(Object result) {
        LogUtil.e(TAG, "scene=");
        SceneBean bean = (SceneBean) result;
        if (bean == null) {
            return;
        }
        List<SceneBean.ItemsDTO> items = bean.getItems();
       /* if (items != null && items.size() > 0) {
            banner.setPages(items, new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            banner.start();
        }*/
        bannerList.clear();
        for (int i = 0; i < items.size(); i++) {
            String image = items.get(i).getImage();
            bannerList.add(ApiService.IMAGE + image);

        }

        if (bannerList.size() > 0) {
            if (null == bannerViewPager) {
                bannerViewPager = new MyCsBannerViewPager(ll_topView, new MyCsBannerViewPager.BannerOnItemClickListener() {

                    @Override
                    public void onListener(int position) {
                        SceneBean.ItemsDTO data=items.get(position);
                        if (data.getLinkType() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("productId", "" + data.getValue());
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), ProductDetailsActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        } else if (data.getLinkType() == 2) {
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent();

                            bundle.putString("inType", 0 + "");
                            bundle.putString("shopId", data.getValue());
                            intent.setClass(getActivity(), ShopDetailsActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    }
                });
            }
            bannerViewPager.setAspectRatio(2.5f);
            bannerViewPager.show(bannerList);
        }


    }

    private MyCsBannerViewPager bannerViewPager;
    ArrayList<String> bannerList = new ArrayList<>();



   /* public class BannerViewHolder implements MZViewHolder<SceneBean.ItemsDTO> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, null);
            mImageView = (ImageView) view.findViewById(R.id.mBanner_ImageView);
            return view;
        }

        @Override
        public void onBind(Context context, int position, SceneBean.ItemsDTO data) {
            // 数据绑定
//            mImageView.setImageResource(data);
            GlideImageLoader.create(mImageView).load(ApiService.IMAGE + data.getImage(), new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (data.getLinkType() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("productId", "" + data.getValue());
                        Intent intent = new Intent();
                        intent.setClass(context, ProductDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else if (data.getLinkType() == 2) {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent();

                        bundle.putString("inType", 0 + "");
                        bundle.putString("shopId", data.getValue());
                        intent.setClass(context, ShopDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }*/

   /* @Override
    public void onStart() {
        super.onStart();
        if (banner != null) {
            banner.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (banner != null) {
            banner.pause();
        }
    }*/

    private boolean hasList = true;

    private void onListData(ProductListBean listBean) {
        mMySwipeRefresh.setRefreshing(false);
        homeAdapter.getLoadMoreModule().setEnableLoadMore(true);

        if (listBean == null || listBean.getList() == null) {
            return;
        }

        List<ProductListBean.list> dataList = listBean.getList();

        if (dataList == null) {
            if (pageNo > 1) {
                homeAdapter.getLoadMoreModule().loadMoreEnd();
            }
            return;
        }

        if (pageNo == 1) {
            hasList = false;

            mProductLists.clear();
            homeAdapter.setList(dataList);
        } else {
            homeAdapter.addData(dataList);
//            homeAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (dataList.size() < 10) {
            homeAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            homeAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (dataList.size() > 0) {
            hasList = true;
            mProductLists.addAll(dataList);
        }
//        if (dataList.size() > 0) {
//            mProductLists.addAll(dataList);
//        } else {
//            if (pageNo > 1) {
//                ToastUtil.showToast("没有更多数据", false);
//            }
//        }
//        homeAdapter.setList(mProductLists);


    }


    @Override
    public void onError(String error) {
        ToastUtil.showToast(error, false);
    }

//    private void getMostNearShop(String lon, String lag, String cityId) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("longitude", lon);
//        map.put("latitude", lag);
//        map.put("cityId", cityId);
//        homeModel.getMostNearShop(map, type_near);
//    }


    private void getShopForHome(String lon, String lag) {
        Map<String, Object> map = new HashMap<>();
        map.put("longitude", lon);
        map.put("latitude", lag);
        homeModel.getShopForHome(map, type_near);
    }

    private void startScan() {
        if (getActivity() == null) {
            return;
        }

        PermissionX.init(HomeNFragment.this).permissions(Manifest.permission.CAMERA).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, @NonNull @NotNull List<String> grantedList, @NonNull @NotNull List<String> deniedList) {
                if (allGranted) {
                    startActivityForResult(ScanQRActivity.class, START_REQUEST_CAMERA);
                } else {
                    ToastUtil.showToast("开启相机权限才能扫描二维码", false);
                }

            }
        });
    }

    private boolean isFirstLocation = true;

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {


        LogUtil.e("http", tencentLocation.getLongitude() + "inDownLocation" + tencentLocation.getProvider());
        SharedPreferencesUtil.put(mActivity, "Long", "" + tencentLocation.getLongitude());
        SharedPreferencesUtil.put(mActivity, "Lat", "" + tencentLocation.getLatitude());
        SharedPreferencesUtil.put(mActivity, "ADDRESS", "" + tencentLocation.getAddress());
        if (isFirstLocation) {
            isFirstLoad = false;
            getShopForHome(tencentLocation.getLongitude() + "", tencentLocation.getLatitude() + "");
            isFirstLocation = false;
        }

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }


    String inivCode = "";
    String Type = "";
    //    1是团长申请，2跳注册，3跳商品详情吗
//    private static final String TAG = "HomeFragment";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e(TAG, "扫码回调" + "requestCode=" + requestCode + " resultCode=" + requestCode);
        if (resultCode == getActivity().RESULT_OK && requestCode == SHOW_ADDRESS_LOCAITON) {
//            shopDetail();
            showLoadingDialog();
            Map<String, String> map = new HashMap<>();
            map.put("id", "" + SharedPreferencesUtil.get(mActivity, "SHOP_ID", ""));
            homeModel.shopDetail(map, type_select_shop);
        } else if (requestCode == START_REQUEST_CAMERA) {
            if (requestCode == START_REQUEST_CAMERA) {
                super.onActivityResult(requestCode, resultCode, data);
                String link = data.getStringExtra("data_return");
                if (StringUtils.isEmpty(link)) {
                    return;
                }
                LogUtil.e(TAG, "data==" + link);
                //TODO 这里处理扫码的逻辑
                // aifugo://aifubook.com/product?id=xxx&inviteCode=   这是商品的推荐链接
                if (link.contains("aifugo://aifubook.com/product")) {
                    int l = link.indexOf("id=");
                    int r = link.indexOf("&");
                    String productId = link.substring(l + 3, r);
                    LogUtil.e(TAG, "productId=" + productId);
                    int l1 = link.indexOf("inviteCode=");
                    int r1 = link.length();
                    Bundle bundle = new Bundle();
                    if ((l1 + 11) != r1) {
                        String inviteCode = link.substring(l1 + 11, r1);
                        LogUtil.e(TAG, "inviteCode=" + inviteCode);
                        bundle.putString("inviteCode", inviteCode);
                    } else {
                        bundle.putString("inviteCode", "");
                    }
                    bundle.putString("productId", productId);
                    startActivity(ProductDetailsActivity.class, bundle);
                } else if (link.contains(ApiService.weburl)) {
                    if (link.contains("?")){
                        String arr[] = link.split("\\?");
                        Intent recognizeIntent = new Intent();
                        recognizeIntent.putExtra("code", arr[1]);
                        recognizeIntent.setClass(getActivity(), TeacherWebviewActivity.class);
                        startActivity(recognizeIntent);
                    }
//                    int l = link.indexOf("inviteCode=");
//                    int r = link.length();
//                    if ((l + 11) != r) {
//                        String inviteCode = link.substring(l + 11, r);
//                        LogUtil.e(TAG, "inviteCode=" + inviteCode);
//                        if (!StringUtils.isEmpty(inviteCode)) {
//                            //TODO 先判断有没有登录
//                            if (!checkLogin()) {
//                                //登录判断是否是团长
//                                boolean menuFlag = SharedPreferencesUtil.get(MyApp.getInstance(), KeyCom.MENU_FLAG, false);
//                                if (!menuFlag) {
//                                    Bundle b = new Bundle();
//                                    b.putString("inivCode", inviteCode);
//                                    b.putString("type", "3");
//                                    startActivity(HeadApplyActivity.class, b);
//                                } else {
//                                    ToastUtil.showToast("您已经是老师了", false);
//                                }
//                            } else {
//                                //登录页面
//                                startActivity(LoginNewActivity.class);
//
//                            }
//                        }
//                    }
                } else if (link.contains("aifugo://aifubook.com/regist")) {
                    int l = link.indexOf("inviteCode=");
                    int r = link.length();
                    if ((l + 11) != r) {
                        String inviteCode = link.substring(l + 11, r);
                        LogUtil.e(TAG, "inviteCode=" + inviteCode);
                        if (!StringUtils.isEmpty(inviteCode)) {
                            //TODO 先判断有没有登录
                            if (!checkLogin()) {
                                //判断是否绑定老师
                                String recommendId = SharedPreferencesUtil.get(getActivity(), KeyCom.RECOMMEND_ID, "");
                                if (!StringUtils.isEmpty(recommendId)) {
                                    ToastUtil.showToast("您已经办绑定过团长", false);
                                } else {
                                    //发送绑定请求
                                    Map map = new HashMap();
                                    map.put("inviteCode", inviteCode);
//                                    mPresenter.bindChief(map);
                                    homeModel.bindChief(map, type_bind_chief);
                                }

                            } else {
                                //登录页面
                                Bundle bundle = new Bundle();
                                bundle.putString("inviteCode", inviteCode);
                                startActivity(LoginNewActivity.class, bundle);
//                                startActivity(LoginNewActivity.class);
                            }
                        }
                    }
                }


            }
        }
    }


}
