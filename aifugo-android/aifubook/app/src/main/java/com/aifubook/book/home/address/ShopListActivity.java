package com.aifubook.book.home.address;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.jiarui.base.utils.BigDecimalUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.jiarui.base.utils.LogUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentLocationUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ListKer_Hlg on 2021/4/25 23:01
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ShopListActivity extends BaseActivity<AddressProductPresenter> implements AddressProductView, TencentLocationListener {
    private static final String TAG = "ShopListActivity";
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    BaseRecyclerAdapter<NearShopBean> beanBaseRecyclerAdapter;
    List<NearShopBean> productListBeans = new ArrayList<>();

    @BindView(R.id.inSelectType)
    TextView inSelectType;

    String address;
    String proId = "0";
    String cityId = "0";
    String dirId = "0";

    JSONArray jsonObject;

    String addressStr;

    @BindView(R.id.SelectAddress)
    TextView SelectAddress;

    //    private String myLong,myLat;
    private TencentLocationManager mLocationManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_list;
    }

    @Override
    public void initPresenter() {
        mPresenter = new AddressProductPresenter(this);
    }

    @Override
    public void initView() {
        verifyStoragePermissions();


//        myLong = SharedPreferencesUtil.get(this, "Long", "");
//        myLat = SharedPreferencesUtil.get(this, "Lat", "");

        setTitle("切换门店");
        initRighRecycler();
        // getMostNearShop();


        address = getFromAssets("address.json");

        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        SelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress();
            }
        });

    }


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public void verifyStoragePermissions() {


        PermissionX.init(ShopListActivity.this).permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, @NonNull @NotNull List<String> grantedList, @NonNull @NotNull List<String> deniedList) {
                if (allGranted) {
                    startProgressDialog();
                    TencentLocationManager mLocationManager = TencentLocationManager.getInstance(ShopListActivity.this);
                    TencentLocationRequest request = TencentLocationRequest.create();
                    request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
                    mLocationManager.requestSingleFreshLocation(request, ShopListActivity.this, Looper.getMainLooper());
                } else {
                    ToastUtil.showToast("开启定位权限才能查看附近店铺", true);
                }
            }
        });
    }


    public String getFromAssets(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    String cityIds;
    //1、从选择dialogj进入，需要地址请求adcode;
    //2、从定位进入
    private void getDataAsync(String address) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://apis.map.qq.com/ws/geocoder/v1/?address=" + address + "&key=LVKBZ-QINLR-OE7WV-WNCID-AVVXT-3SBCQ")
//                .url("https://apis.map.qq.com/ws/geocoder/v1/?coord_type=5&get_poi=0&output=json&key=LVKBZ-QINLR-OE7WV-WNCID-AVVXT-3SBCQ&location="+myLat+"&"+myLong)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()){//回调的方法执行在子线程。

                try {
                    String res = response.body().string();
                    LogUtil.e(TAG, "response==" + res);
                    if (res == null) {
                        return;
                    }
                    JSONObject jsonObject = new JSONObject(res);
                    String lng = jsonObject.getJSONObject("result").getJSONObject("location").getString("lng");
                    String lat = jsonObject.getJSONObject("result").getJSONObject("location").getString("lat");
                    cityIds = jsonObject.getJSONObject("result").getJSONObject("ad_info").getString("adcode");
                    LogUtil.e(TAG, "addressStr=" + address + "" + "cityIds" + cityIds + response + response.body().toString());

//                    SharedPreferencesUtil.put(AddressProductActivity.this, "Long", "" + lng);
//                    SharedPreferencesUtil.put(AddressProductActivity.this, "Lat", "" + lat);
//                    SharedPreferencesUtil.put(AddressProductActivity.this,"CityId",""+cityIds);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getMostNearShops();

//                }
            }
        });
    }

//    String url = "";


    //省份
    String province;
    //城市
    String city;
    //区县（如果设定了两级联动，那么该项返回空）
    String district;

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(ShopListActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("浙江省")
                .city("杭州市")
                .district("西湖区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];

                for (int i = 0; i < jsonObject.length(); i++) {
                    try {
                        if (jsonObject.getJSONObject(i).get("name").equals(province)) {
                            proId = jsonObject.getJSONObject(i).get("id") + "";
                            JSONArray jsonArray = jsonObject.getJSONObject(i).getJSONArray("children");
                            for (int j = 0; j < jsonArray.length(); j++) {
                                if (jsonArray.getJSONObject(j).get("name").equals(city)) {
                                    cityId = jsonArray.getJSONObject(j).get("id") + "";

                                    JSONArray jsonArrayD = jsonArray.getJSONObject(j).getJSONArray("children");
                                    for (int k = 0; k < jsonArrayD.length(); k++) {
                                        if (jsonArrayD.getJSONObject(k).get("name").equals(district)) {
                                            dirId = jsonArrayD.getJSONObject(k).get("id") + "";
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //为TextView赋值
                inSelectType.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                addressStr = province.trim() + "" + city.trim() + "" + district.trim();
                startProgressDialog();

                getDataAsync(addressStr);

            }
        });
    }


    // 获取所有类型
    private void getMostNearShops() {
        Map<String, Object> map = new HashMap<>();
        map.put("longitude", SharedPreferencesUtil.get(this, "Long", ""));
        map.put("latitude", SharedPreferencesUtil.get(this, "Lat", ""));
        map.put("cityId", cityIds);
        if (mPresenter != null)
            mPresenter.findShops(map);
    }


    private void initRighRecycler() {
        beanBaseRecyclerAdapter = new BaseRecyclerAdapter<NearShopBean>(this, productListBeans, R.layout.activity_address_shop_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, NearShopBean item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.addressImageView, ApiService.IMAGE + item.getLogo());
                holder.setText(R.id.tv_address, item.getName());
                holder.setText(R.id.details_tv, item.getAddress());
                // holder.setText(R.id.addressTextView, "距离您" + item.getDistance() + "km");
                String lag = item.getLocation().get(0) + "";
                String lon = item.getLocation().get(1) + "";
                if (!StringUtils.isEmpty(myLong + "") && !StringUtils.isEmpty(myLat + "")) {
                    Double dis = TencentLocationUtils.distanceBetween(myLat, myLong, Double.parseDouble(lag), Double.parseDouble(lon));
                    // addressDis.setText("距离您" + BigDecimalUtil.divide(dis.toString(),"1000",2)+ "km");
                    if (dis > 1000) {
                        holder.setText(R.id.addressTextView, "距离您" + BigDecimalUtil.divide(dis.toString(), "1000", 2) + "千米");
                    } else {
                        holder.setText(R.id.addressTextView, "距离您" + BigDecimalUtil.getFixedPointNum(dis.toString(), 2) + "米");
                    }

                }
                holder.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtil.put(ShopListActivity.this, "SHOP_ID", "" + item.getId());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(IntentKey.Companion.getSHOPID(), item.getId().toString());
                        bundle.putString(IntentKey.Companion.getSHOPNAME(), item.getName());
                        bundle.putString(IntentKey.Companion.getSHOPADDRESS(), item.getAddress());
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        ShopListActivity.this.finish();
                    }
                });

            }
        };
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(beanBaseRecyclerAdapter);
    }


    @Override
    public void GetDataSuc(List<NearShopBean> DataBean) {
        stopProgressDialog();
        inSelectType.setText(addressStr);
        productListBeans.clear();
        productListBeans.addAll(DataBean);
        beanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void GetDataFail(String Message) {
        stopProgressDialog();

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    private Double myLong, myLat;

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        LogUtil.e(TAG, "tencentLocation=" + tencentLocation.toString());
        myLong = tencentLocation.getLongitude();
        myLat = tencentLocation.getLatitude();
        SharedPreferencesUtil.put(this, "Long", "" + tencentLocation.getLongitude());
        SharedPreferencesUtil.put(this, "Lat", "" + tencentLocation.getLatitude());
        SharedPreferencesUtil.put(this, "ADDRESS", "" + tencentLocation.getAddress());
        getLocationInfo(tencentLocation.getLatitude() + "", "" + tencentLocation.getLongitude());


    }

    private void getLocationInfo(String lat, String lng) {

        String hsUrl = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=LVKBZ-QINLR-OE7WV-WNCID-AVVXT-3SBCQ" + "&get_poi=0";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(hsUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, "e=" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                LogUtil.e(TAG, "result==" + body);
                try {
                    JSONObject jsonObj = new JSONObject(body);
                    LogUtil.e(TAG, "jsonObject==" + jsonObj);
                    JSONObject result = jsonObj.optJSONObject("result");
                    JSONObject address_component = result.optJSONObject("address_component");

                    String province = address_component.optString("province");
                    String city = address_component.optString("city");
                    String district = address_component.optString("district");
                    addressStr = province + city + district;

                    getDataAsync(province + city + district);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
