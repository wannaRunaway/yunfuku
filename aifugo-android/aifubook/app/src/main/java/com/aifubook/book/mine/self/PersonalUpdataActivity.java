package com.aifubook.book.mine.self;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.activity.main.shopdetail.DialogGradeKt;
import com.aifubook.book.activity.webview.TeacherWebviewActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.application.MyApp;
import com.aifubook.book.base.IntentKey;
import com.aifubook.book.base.ShareKey;
import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.mine.member.MemberInfoPresenter;
import com.aifubook.book.mine.member.MemberInfoView;
import com.aifubook.book.mine.order.GlideEngine;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.utils.SharedPreferencesUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.jiarui.base.glide.GlideImageLoader;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aifubook.book.api.ApiService.IMAGE;

public class PersonalUpdataActivity extends BaseActivity<MemberInfoPresenter> implements MemberInfoView {
    private static final String TAG = "PersonalUpdataActivity";

    @BindView(R.id.iv_head)
    RoundedImageView iv_head;
    @BindView(R.id.tv_nick)
    EditText tv_nick;
    @BindView(R.id.tv_name)
    EditText tv_name;
    @BindView(R.id.tv_age)
    EditText tv_age;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_identify)
    TextView tv_identify;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.et_school)
    EditText et_school;
    @BindView(R.id.tv_isrecognize)
    TextView tv_isrecognize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personalupdate;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MemberInfoPresenter(this);
    }


    String address;
    String proId = "0";
    String cityId = "0";
    String dirId = "0";

    JSONArray jsonObject;

    List<String> Sex = new ArrayList<>();
    private AlertDialog identityDialog, gradeDialog;
    private ActivityResultLauncher<Intent> cameralauncher;

    @Override
    public void initView() {
        title.setText("个人信息");
//        Map map = new HashMap();
//        mPresenter.memberInfo(map);
        address = getFromAssets("address.json");
        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sex.add("男");
        Sex.add("女");
        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.textview_teacher:
                    identityDialog.dismiss();
                    tv_identify.setText("教师");
                    registerMember();
                    break;
                case R.id.textview_student:
                    identityDialog.dismiss();
                    tv_identify.setText("家长");
                    registerMember();
                    break;
                case R.id.textview_close_identity:
                    identityDialog.dismiss();
                    break;
                case R.id.textview_grade1:
                case R.id.textview_grade2:
                case R.id.textview_grade3:
                case R.id.textview_grade4:
                case R.id.textview_grade5:
                case R.id.textview_grade6:
                case R.id.textview_grade7:
                case R.id.textview_grade8:
                case R.id.textview_grade9:
                    tv_class.setText(((TextView) v).getText());
                    gradeDialog.dismiss();
                    break;
                case R.id.textview_close:
                    gradeDialog.dismiss();
                    break;
                default:
                    break;
            }
        };
        tv_identify.setOnClickListener(v -> {
            hidesoftkeyboard();
            identityDialog = DialogGradeKt.dialogIdentify(PersonalUpdataActivity.this, listener);
        });
        tv_class.setOnClickListener(v -> {
            hidesoftkeyboard();
            gradeDialog = DialogGradeKt.gradeclick(PersonalUpdataActivity.this, listener);
        });
        cameralauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                String link = result.getData().getStringExtra("data_return");
                if (StringUtils.isEmpty(link)) {
                    return;
                }
                if (link.contains("?")) {
                    String arr[] = link.split("\\?");
                    Intent intent = new Intent();
                    intent.putExtra(IntentKey.Companion.getINVITECODE(), arr[1]);
                    intent.setClass(
                            PersonalUpdataActivity.this,
                            TeacherWebviewActivity.class);
                    startActivity(intent);
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

    private void registerMember() {
        String age = tv_age.getText().toString();
        String name = tv_name.getText().toString();
        String nickname = tv_nick.getText().toString();
        String school = et_school.getText().toString();
        String grade = tv_class.getText().toString();
        String address = tv_home.getText().toString();
//        if (StringUtils.isEmpty(age)) {
//            ToastUtil.showToast("年龄不能为空", false);
//            return;
//        }
//        if (StringUtils.isEmpty(nickname)) {
//            ToastUtil.showToast("昵称不能为空", false);
//            return;
//        }
//        if (StringUtils.isEmpty(name)) {
//            ToastUtil.showToast("真实姓名不能为空", false);
//            return;
//        }
//        if (StringUtils.isEmpty(address)) {
//            ToastUtil.showToast("所在地不能为空", false);
//            return;
//        }
//        if(StringUtils.isEmpty(ImgTwo)){
//            ToastUtil.showToast("请上传头像",false);
//            return;
//        }
        Map<String, String> map = new HashMap();
        map.put("age", "" + age);
        map.put("name", "" + name);
        map.put("sex", "" + SexS);
        map.put("nickname", "" + nickname);
        map.put("cityId", "" + cityId);
        map.put("districtId", "" + dirId);
        map.put("provinceId", "" + proId);
        map.put("city", "" + city);
        map.put("district", "" + district);
        map.put("province", "" + province);
        map.put("region", "" + address);
        map.put("logo", "" + ImgTwo);
        // "grade": "string",  "schoolName": "string",
        if (!StringUtils.isEmpty(school)) {
            map.put("schoolName", school);
        }
        if (!StringUtils.isEmpty(grade)) {
            map.put("grade", grade);
        }
        int identity = 0;
        switch (tv_identify.getText().toString()) {
            case "家长":
                identity = 0;
                break;
            case "教师":
                identity = 1;
                break;
            default:
                break;
        }
        map.put("registeredIdentity", String.valueOf(identity));
//        startProgressDialog();
        mPresenter.updateMemberInfo(map);
    }

    @OnClick({R.id.leftImg, R.id.tv_gender, R.id.iv_head, R.id.tv_home})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
//                verifyStoragePermissions();
                hidesoftkeyboard();
                cameraOpen();
                break;
            case R.id.leftImg:
                registerMember();
                finish();
                break;
            case R.id.tv_gender:
                hidesoftkeyboard();
                pvCustomOptions(0, Sex);
                break;
            case R.id.tv_home:
                hidesoftkeyboard();
                selectAddress();
                break;

        }
    }

    private void hidesoftkeyboard(){
        tv_nick.clearFocus();
        tv_name.clearFocus();
        tv_age.clearFocus();
        et_school.clearFocus();
        var inputManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(tv_nick.getWindowToken(), 0);
        inputManager.hideSoftInputFromWindow(tv_name.getWindowToken(), 0);
        inputManager.hideSoftInputFromWindow(tv_age.getWindowToken(), 0);
        inputManager.hideSoftInputFromWindow(et_school.getWindowToken(), 0);
    }

    private void cameraOpen() {
        EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快），[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.aifubook.book.fileprovider")//参数说明：见下方`FileProvider的配置`
                .setCount(1)//参数说明：最大可选数，默认1
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        mPath.clear();
                        mPath.add(photos.get(0).path);
                        startProgressDialog("正在上传");
                        GetData();
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.d("onCancel()........");
                    }
                });

    }

    //省份
    String province = "";
    //城市
    String city = "";
    //区县（如果设定了两级联动，那么该项返回空）
    String district = "";

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(PersonalUpdataActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("浙江省")
                .city("杭州市")
                .district("萧山区")
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
//                findByCityId(dirId);
                //为TextView赋值
                tv_home.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    String ClassId = "";
    String SexS = "1";
    private OptionsPickerView pvCustomOptions;//交易类型自定义选择器

    private <T> void pvCustomOptions(int type, List<T> data) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                if (type == 0) {
                    tv_gender.setText(Sex.get(options1));
                    if (options1 == 0) {
                        SexS = "1";
                    } else {
                        SexS = "2";
                    }
                } else if (type == 1) {
                } else if (type == 2) {
                } else if (type == 3) {
                }

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

                        ivCancel.setOnClickListener(v1 -> pvCustomOptions.dismiss());
                    }
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();

        pvCustomOptions.setPicker(data);//添加数据
        pvCustomOptions.show();
    }

    String ImgTwo = "";

    @Override
    public void GetSendImageSuc(String notDataBean) {
        GlideImageLoader.create(iv_head).load(ApiService.IMAGE + notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
        ImgTwo = notDataBean;
        stopProgressDialog();
    }

    @Override
    public void GetSendImageFail(String Message) {
        stopProgressDialog();

    }

    @Override
    public void GetShopSuc(ShopBean DataBean) {
        stopProgressDialog();

    }

    @Override
    public void GetShopFail(String Message) {
        stopProgressDialog();

    }

    @Override
    public void MemberInfoSuc(MemberInfoBean DataBean) {
        stopProgressDialog();

        cityId = DataBean.getCityId();
        dirId = DataBean.getDistrictId();
        proId = DataBean.getProvinceId();

        city = DataBean.getCity();
        district = DataBean.getDistrict();
        province = DataBean.getProvince();

//        ImgTwo = DataBean.getCityId();


        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head); //圆角
        String logo = DataBean.getLogo();
        ImgTwo = logo;
        if (!logo.contains("http")) {

            logo = IMAGE + logo;
        }
        Glide.with(mContext)
                .load(logo)
                .apply(options)
                .into(iv_head);

        if (!StringUtils.isEmpty(DataBean.getNickname())) {
            tv_nick.setText(DataBean.getNickname());
        } else {
            tv_nick.setHint("请设置昵称");
        }
        if (!StringUtils.isEmpty(DataBean.getName())) {
            tv_name.setText(DataBean.getName());
        } else {
            tv_name.setHint("请设置用户姓名");
        }

        if (!StringUtils.isEmpty(DataBean.getAge())) {
            tv_age.setText(DataBean.getAge());
        } else {
            tv_age.setHint("请设置年龄");
        }

        if (!StringUtils.isEmpty(DataBean.getSex())) {
            if (DataBean.getSex().equals("1")) {
                tv_gender.setText("男");
            } else if (DataBean.getSex().equals("2")) {
                tv_gender.setText("女");
            } else if (DataBean.getSex().equals("0")) {
                tv_gender.setText("未知性别");
            }
        } else {
            tv_gender.setText("保密");
        }
        if (!StringUtils.isEmpty(DataBean.getCity())) {
            tv_home.setText(DataBean.getProvince() + DataBean.getCity() + DataBean.getDistrict());
        } else {
            tv_home.setText("请选择区域");
        }
        if (!DataBean.getSchoolName().isEmpty()) {
            et_school.setText(DataBean.getSchoolName());
        } else {
            et_school.setHint("未填写");
        }
        if (!DataBean.getGrade().isEmpty() && !DataBean.getGrade().equals("未填写")) {
            tv_class.setText(DataBean.getGrade());
        } else {
            String grade = SharedPreferencesUtil.get(MyApp.getInstance(), ShareKey.Companion.getGRADE(), ShareKey.Companion.getDefautGrade());
            tv_class.setText(grade);
        }
        if (!DataBean.getGrade().isEmpty() && !DataBean.getGrade().equals("未填写")) {
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getGRADE(), DataBean.getGrade());
        }
        if (DataBean.getRegisteredIdentity() == 0) { //0家长 1教师
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), "家长");
            tv_identify.setText("家长");
            tv_isrecognize.setVisibility(View.GONE);
        } else {
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), "教师");
            tv_identify.setText("教师");
            tv_isrecognize.setVisibility(View.VISIBLE);
        }
        ChiefDetailsBean chiefDetailsBean = DataBean.getChiefsVO();
        if (chiefDetailsBean != null) {//0 :申请中 1 :店铺审核通过 2 :审核拒绝
            if (chiefDetailsBean.getStatus().equals("1")) {
                tv_isrecognize.setText("已认证");
                tv_isrecognize.setClickable(false);
            } else {
                tv_isrecognize.setClickable(false);
                gorecognize();
            }
        } else {
            tv_isrecognize.setClickable(false);
            gorecognize();
        }
    }

    private void gorecognize() {
        tv_isrecognize.setText("去认证");
        //点击到扫码界面
        tv_isrecognize.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IntentKey.Companion.getINVITECODE(), ApiService.shopinvitecode);
            intent.setClass(
                    PersonalUpdataActivity.this,
                    TeacherWebviewActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void appliedShop(ShopBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void UpdateMemberInfoSuc(String DataBean) {
//        stopProgressDialog();
        Map map = new HashMap();
        mPresenter.memberInfo(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map map = new HashMap();
        mPresenter.memberInfo(map);
    }

    @Override
    public void GetCanUsedBalanceSuc(String DataBean) {
        ToastUitl.showShort(this, DataBean + "");
    }

    @Override
    public void GetAccountInfoSuc(GetAccountInfoBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void HasPayPasswordSuc(String DataBean) {
        stopProgressDialog();
    }

    @Override
    public void SetPayPasswordSuc(String DataBean) {
        stopProgressDialog();
    }

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void GetHomePageFail(String Message) {
        showToast(Message);
        stopProgressDialog();
    }

    @Override
    public void GetDataSuc(ProductListBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void GetDataFail(String Message) {
        stopProgressDialog();
    }

    @Override
    public void getOrderCountResult(OrderCountVO orderCountVO) {

    }

    @Override
    public void getOrderCountError(String msg) {
        stopProgressDialog();

    }

    @Override
    public void getWeChatToken(String token) {
        stopProgressDialog();

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {
        stopProgressDialog();

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
        PictureSelector.create(PersonalUpdataActivity.this)
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
        LogUtil.e(TAG, "onActivityResult=requestCode=" + requestCode);
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
                    startProgressDialog("正在上传");
                    GetData();
                    break;
            }
        }
        if (requestCode == 100 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                //执行获取权限成功的回调，然后进行自己的操作，下面只是我假设的一个方法
                selectImg();
            } else {
                //执行获取权限失败的回调，进行自己的操作
                ToastUitl.showShort(this, "存储权限获取失败");
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

    private String imagePath;

   /* @Override
    public void takeSuccess(TResult result) {
        imagePath = result.getImage().getCompressPath();
        mPath.clear();
        mPath.add(imagePath);
        startProgressDialog("正在上传");
        GetData();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showToast(msg);
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        return null;
    }*/


//    String ImgOne;
//    String ImgTwo;
//    int SelectType = 1;
//    @Override
//    public void GetSendImageSuc(String notDataBean) {
//        if (SelectType==1){
//            GlideImageLoader.create(ImageSelectFirst).load(ApiService.IMAGE+notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
//            ImgOne = notDataBean;
//        }else{
//            GlideImageLoader.create(ImageSelectSec).load(ApiService.IMAGE+notDataBean, new RequestOptions().disallowHardwareConfig().error(R.mipmap.bg_home_banner));
//            ImgTwo = notDataBean;
//        }
//
//    }

}