package com.aifubook.book.shoprequest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aifubook.book.activity.main.BaseActivity;
import com.alibaba.fastjson.JSONObject;
import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.mine.setting.PriviteActivity;
import com.bumptech.glide.request.RequestOptions;
import com.jiarui.base.glide.GlideImageLoader;
import com.jiarui.base.utils.ToastUitl;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.tencent.map.geolocation.TencentLocationRequest.REQUEST_LEVEL_POI;

/**
 * Created by ListKer_Hlg on 2021/5/11 23:12
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ShopRequestActivity extends BaseActivity<ShopRequestPresenter> implements ShopRequestView, TencentLocationListener {


    @BindView(R.id.et_name)  // 聯係人姓名
            EditText et_name;

    @BindView(R.id.et_account)  // 手机号码
            EditText et_account;

    @BindView(R.id.et_inviteCode)  // 店铺名称
            EditText et_inviteCode;

    @BindView(R.id.tv_selectArea)  // 书店地址
            TextView tv_selectArea;

    @BindView(R.id.tv_selectLog)  // 经纬度
            TextView tv_selectLog;

    @BindView(R.id.tv_detailedAddress)  // 详细地址
            EditText tv_detailedAddress;

    @BindView(R.id.tv_detailedTalk)  // 特别说明
            EditText tv_detailedTalk;

    @BindView(R.id.ImageSelectFirst)  // 特别说明
            ImageView ImageSelectFirst;


    @BindView(R.id.ImageSelectSec)  // 特别说明
            ImageView ImageSelectSec;


    @BindView(R.id.ImageSelectfour)  // 特别说明
            ImageView ImageSelectfour;


    @BindView(R.id.ImageSelectthree)  // 特别说明
            ImageView ImageSelectthree;


    @BindView(R.id.ImageSelectfive)  // 特别说明
            ImageView ImageSelectfive;


    String address;
    String proId = "0";
    String cityId = "0";
    String dirId = "0";

    String proStr = "0";
    String cityStr = "0";
    String dirStr = "0";


    JSONArray jsonObject;

    @BindView(R.id.BigContent)
    ScrollView BigContent;

    @BindView(R.id.success)
    LinearLayout success;

    @BindView(R.id.fail)
    LinearLayout fail;

    @BindView(R.id.searchNow)
    LinearLayout searchNow;

    @BindView(R.id.tv_submit)
    TextView tv_submit;


    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_request;
    }

    @Override
    public void initPresenter() {
        mPresenter = new ShopRequestPresenter(this);
    }

    String type = "3";

    @Override
    public void initView() {
        setTitle("门店入驻");
        address = getFromAssets("address.json");

        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(this);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(REQUEST_LEVEL_POI);
        mLocationManager.requestSingleFreshLocation(request, this, Looper.getMainLooper());


        Log.e("WhatMessage = ", getIntent().getExtras().getString("type") + "");
        //        0 :申请中 1 :店铺审核通过 2 :审核拒绝
        type = getIntent().getExtras().getString("type");
        switch (type) {
            case "0":
                searchNow.setVisibility(View.VISIBLE);
                break;
            case "1":
                success.setVisibility(View.VISIBLE);
                break;
            case "2":
                fail.setVisibility(View.VISIBLE);
                break;
            case "3":
                BigContent.setVisibility(View.VISIBLE);
                tv_submit.setVisibility(View.VISIBLE);
                break;
        }

        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String Long = "";
    String Lat = "";
    String ADDRESS = "";

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        Log.e("http", tencentLocation.getLongitude() + "inDownLocation" + tencentLocation.getProvider());
        Long = tencentLocation.getLongitude() + "";
        Lat = tencentLocation.getLatitude() + "";
        tv_selectLog.setText(Lat + "," + Long);
        ADDRESS = tencentLocation.getAddress() + "";
        tv_detailedAddress.setText(ADDRESS);
//        SharedPreferencesUtil.put(mActivity, "Long", "" + tencentLocation.getLongitude());
//        SharedPreferencesUtil.put(mActivity, "Lat", "" + tencentLocation.getLatitude());
//        SharedPreferencesUtil.put(mActivity, "ADDRESS", "" + tencentLocation.getAddress());
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.e("http", "ssss" + s);
    }

    @OnClick(R.id.prisitionManger)
    void prisitionManger() {
        Bundle bundle = new Bundle();
        bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/joinShopAgreement.html");
        bundle.putString("title", "店铺入驻协议");
        startActivity(PriviteActivity.class, bundle);
    }


    // 商品详情的接口
    private void shopApply() {
        Map<String, String> map = new HashMap<>();
        mPresenter.shopApply(getRequestBodys());
    }

//    /**
//     * 身份证正面
//     */
//    private String idCardFront;
//    /**
//     * 身份证反面
//     */
//    private String idCardReverseSide;
//    /**
//     * 出版物经营许可证
//     */
//    private String publicationBusinessLicense;

    List<String> ins = new ArrayList<>();

    public RequestBody getRequestBodys() {
        ins.add("" + Lat);
        ins.add("" + Long);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("address", "" + tv_detailedAddress);
        jsonObject3.put("city", "" + city);
        jsonObject3.put("cityId", "" + cityId);
        jsonObject3.put("credentials", "" + ImgTwo);
        jsonObject3.put("district", "" + district);
        jsonObject3.put("districtId", "" + dirId);
        jsonObject3.put("linkman", "" + et_name.getText().toString());
        jsonObject3.put("location", ins);

        jsonObject3.put("locationAddress", "" + ADDRESS);

        jsonObject3.put("logo", "" + ImgOne);

        jsonObject3.put("idCardFront", "" + ImgThree);
        jsonObject3.put("idCardReverseSide", "" + Imgfour);
        jsonObject3.put("publicationBusinessLicense", "" + Imgfive);


        jsonObject3.put("name", "" + et_inviteCode.getText().toString());
        jsonObject3.put("phone", "" + et_account.getText().toString());
        jsonObject3.put("province", "" + province);
        jsonObject3.put("provinceId", "" + proId);
        jsonObject3.put("remark", "" + tv_detailedTalk);
        jsonObject3.put("smsCode", "");
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject3.toString());
        return requestBody;
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

    @OnClick({R.id.ImageSelectfour, R.id.ImageSelectthree, R.id.ImageSelectfive, R.id.success_back, R.id.failBack, R.id.Search_back, R.id.tv_submit, R.id.tv_selectArea, R.id.ImageSelectFirst, R.id.ImageSelectSec})
    void Onlcicks(View view) {
        switch (view.getId()) {
            case R.id.success_back:
                this.finish();
                break;
            case R.id.Search_back:
                this.finish();
                break;
            case R.id.failBack:
                fail.setVisibility(View.GONE);
                searchNow.setVisibility(View.GONE);
                tv_submit.setVisibility(View.VISIBLE);
                BigContent.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_submit:
                shopApply();
                break;
            case R.id.tv_selectArea:
                selectAddress();
                break;
            case R.id.ImageSelectFirst:
                SelectType = 1;
                verifyStoragePermissions();
                break;
            case R.id.ImageSelectSec:
                SelectType = 2;
                verifyStoragePermissions();
                break;
            case R.id.ImageSelectthree:
                SelectType = 3;
                verifyStoragePermissions();
                break;
            case R.id.ImageSelectfour:
                SelectType = 4;
                verifyStoragePermissions();
                break;
            case R.id.ImageSelectfive:
                SelectType = 5;
                verifyStoragePermissions();
                break;
        }
    }

    //省份
    String province;
    //城市
    String city;
    //区县（如果设定了两级联动，那么该项返回空）
    String district;

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(ShopRequestActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
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
                tv_selectArea.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    /**
     * 8      * Checks if the app has permission to write to device storage
     * 9      *
     * 10      * If the app does not has permission then the user will be prompted to
     * 11      * grant permissions
     * 12      *
     * 13      * @param activity
     * 14
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {            //检查是否已经给了权限
            int permission = ActivityCompat.checkSelfPermission(MyApp.getInstance(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //没有给权限
                // Log.e("permission","动态申请");                //参数分别是当前活动，权限字符串数组，requestcode
                ActivityCompat.requestPermissions(ShopRequestActivity.this, PERMISSIONS_STORAGE, 100);
            } else {
                selectImg();
            }
        }
    }


    /**
     * 作者: ListKer_Hlg
     * 用途: 下面是图片的处理
     * 简述:
     * 时间: 2018/9/18
     */
    private void selectImg() {
//        // 进入相册 以下是例子：不需要的api可以不写
        int themeId = R.style.picture_default_style;
        PictureSelector.create(ShopRequestActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(selectList)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    List<String> mPath = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    mPath.clear();
                    for (LocalMedia media : selectList) {
                        Log.e("图片-----》", media.getCompressPath());
                        mPath.add(media.getCompressPath());
//                        stringMap.get().put("1-1", media.getCompressPath());
//                        mPresenter.getSendImage(stringMap);
                    }
                    GetData();
                    break;
            }
        }
//        if (resultCode == 5) {
//            if (StringUtils.isEmpty("" + data.getExtras().getString("Name"))) {
//                return;
//            }
//            Name = data.getExtras().getString("Name");
//            Type = "1";
//            GetSaveData();
//        }
    }

    /**
     * 作者: ListKer_Hlg
     * 用途: 数据的访问
     * 简述:
     * 时间: 2018/11/20
     */
    int num;
    List<File> fileList = new ArrayList<>();

    private void GetData() {
        fileList.clear();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < mPath.size(); i++) {
            if (!mPath.get(i).equals("无")) {
                fileList.add(new File(mPath.get(i)));
            }
        }
        if (fileList.size() == 0) {
            return;
        }
        mPresenter.getNextImageSend(map, fileList);
    }

    String Name = "";

    private void GetSaveData() {
//        fileList.clear();
//        Map<String, String> map = new HashMap<>();
//        Map<String, String> Signs = new HashMap<>();
//        if (Type.equals("0")) {
//            map.put("image", "" + HalfPath);
//            map.put("nickName", "");
//        } else {
//            map.put("image", "");
//            map.put("nickName", "" + Name);
//        }
//        map.put("nonce", "" + num);
//        Signs.put("nonce", "" + num);
//        map.put("sign", MD5signeasy.Sort(Signs));
//        mPresenter.getHomePage(map);
    }


    /**
     * 自定义压缩存储地址
     *
     * @return 返回新的地址
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/smart_canteen/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


    String ImgOne;
    String ImgTwo;
    String ImgThree;
    String Imgfour;
    String Imgfive;
    int SelectType = 1;

    @Override
    public void GetSendImageSuc(String notDataBean) {
        if (SelectType == 1) {
            GlideImageLoader.create(ImageSelectFirst).load(ApiService.IMAGE + notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            ImgOne = notDataBean;
        } else if (SelectType == 2) {
            GlideImageLoader.create(ImageSelectSec).load(ApiService.IMAGE + notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            ImgTwo = notDataBean;
        } else if (SelectType == 3) {
            GlideImageLoader.create(ImageSelectthree).load(ApiService.IMAGE + notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            ImgThree = notDataBean;
        } else if (SelectType == 4) {
            GlideImageLoader.create(ImageSelectfour).load(ApiService.IMAGE + notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            Imgfour = notDataBean;
        } else if (SelectType == 5) {
            GlideImageLoader.create(ImageSelectfive).load(ApiService.IMAGE + notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
            Imgfive = notDataBean;
        }

    }

    @Override
    public void GetSendImageFail(String Message) {

    }

    @Override
    public void GetDataSuc(ProductDetailBean DataBean) {

    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetShopSuc(String DataBean) {
        ToastUitl.showShort(this, "成功提交!");
    }

    @Override
    public void GetShopFail(String Message) {
        ToastUitl.showShort(this, Message + "!");
    }

    @Override
    public void GetListDataSuc(ProductListBean DataBean) {

    }

    @Override
    public void GetListDataFail(String Message) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
