package com.aifubook.book.home;

import android.view.View;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

/**
 * Created by ListKer_Hlg on 2021/5/13 11:59
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class MapActivity extends BaseActivity {

    private static final String TAG = "MapActivity";
    private MapView mapView;
    protected TencentMap tencentMap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_map_new;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        findViewById(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mapView = findViewById(R.id.mapview);
        //创建tencentMap地图对象，可以完成对地图的几乎所有操作
        tencentMap = mapView.getMap();

        String ll = getIntent().getExtras().getString("lag");
        String lo = getIntent().getExtras().getString("lon");
        LogUtil.e(TAG,"lag="+ll+" lon="+lo);

        if (!StringUtils.isEmpty(ll) && !StringUtils.isEmpty(lo)) {
            Double lat = Double.parseDouble(ll);
            Double lon = Double.parseDouble(lo);

            if(lat>90){
                finish();
            }

//            LatLng position = new LatLng(lag, lon);
//            Marker marker = tencentMap.addMarker(new MarkerOptions(position));

            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(lat, lon), //中心点坐标，地图目标经纬度
                            19,  //目标缩放级别
                            45f, //目标倾斜角[0.0 ~ 45.0] (垂直地图时为0)
                            45f)); //目标旋转角 0~360° (正北方为0)
            tencentMap.moveCamera(cameraSigma); //移动地图
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
