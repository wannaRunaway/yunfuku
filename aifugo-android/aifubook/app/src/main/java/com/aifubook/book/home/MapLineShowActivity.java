package com.aifubook.book.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.application.MyApp;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.jiarui.base.utils.ToastUtil;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/5/18 3:05
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class MapLineShowActivity extends BaseActivity {

    private static final String TAG = "MapLineShowActivity";
    private MapView mapView;
    protected TencentMap tencentMap;

    @BindView(R.id.inThree)
    TextView inThree;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.showLogo)
    ImageView showLogo;

    @BindView(R.id.centent)
    TextView centent;

    @BindView(R.id.baidu)
    TextView baidu;

    @BindView(R.id.good)
    TextView good;

    @BindView(R.id.cancel)
    TextView cancel;

    @BindView(R.id.showLocation)
    RelativeLayout showLocation;

    @BindView(R.id.iv_left)
    ImageView iv_left;


    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initPresenter() {

    }

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    private boolean isInstalled(String packageName) {
        PackageManager manager = mContext.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }

    @OnClick({R.id.centent, R.id.baidu, R.id.good, R.id.cancel, R.id.showLocation})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.centent:
                if (!isInstalled("com.tencent.map")) {
                    ToastUitl.showShort(this, "暂未安装腾讯地图");
                    return;
                }
                LatLng endPoint = new LatLng(Double.parseDouble(getIntent().getExtras().getString("lag")), Double.parseDouble(getIntent().getExtras().getString("lon")));//坐标转换
                StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                        .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + getIntent().getExtras().getString("add"));
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
                startActivity(intent);
            case R.id.baidu:
                try {
                    Uri uri = Uri.parse("baidumap://map/geocoder?src=com.jianqi.wuye&address=" + getIntent().getExtras().getString("add") + "");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } catch (Exception e) {
                    ToastUitl.showShort(this, "暂未安装百度地图");
                }
                break;
            case R.id.good:
                try {
                    Uri uri = Uri.parse("amapuri://route/plan/?dlat=" + getIntent().getExtras().getString("lag") + "&dlon=" + getIntent().getExtras().getString("lon") + "&dname=" + getIntent().getExtras().getString("add") + "&dev=0&t=0");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } catch (Exception e) {
                    ToastUitl.showShort(this, "暂未安装高德地图");
                }
                break;
            case R.id.cancel:
                showLocation.setVisibility(View.GONE);
                break;
            case R.id.showLocation:
                break;
        }

    }


    @Override
    public void initView() {
        mapView = findViewById(R.id.mapview);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //创建tencentMap地图对象，可以完成对地图的几乎所有操作
        tencentMap = mapView.getMap();

        showLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocation.setVisibility(View.VISIBLE);
            }
        });

//        verifyStoragePermissions();


        inThree.setText("" + getIntent().getExtras().getString("name"));
        address.setText("" + getIntent().getExtras().getString("add"));
        String ll = getIntent().getExtras().getString("lag");
        String lo = getIntent().getExtras().getString("lon");
        LogUtil.e(TAG, "lag=" + ll + " lon=" + lo);

        if (!StringUtils.isEmpty(ll) && !StringUtils.isEmpty(lo)) {
            Double lat = Double.parseDouble(ll);
            Double lon = Double.parseDouble(lo);

            if (lat > 90) {
                finish();
            }

            LatLng position = new LatLng(lat, lon);
            Marker marker = tencentMap.addMarker(new MarkerOptions(position));

            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(lat, lon), //中心点坐标，地图目标经纬度
                            19,  //目标缩放级别
                            45f, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            45f)); //目标旋转角 0~360° (正北方为0)
            tencentMap.moveCamera(cameraSigma); //移动地图
        }
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
                ActivityCompat.requestPermissions(MapLineShowActivity.this, PERMISSIONS_STORAGE, 100);
            } else {


            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {
                ToastUtil.showToast("请开启定位权限", true);

            }
        }
    }

    /**
     * mapview的生命周期管理
     */
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mapView.onRestart();
    }


}
