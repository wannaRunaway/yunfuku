package com.aifubook.book.regimental;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.setting.PriviteActivity;
import com.aifubook.book.regimental.mvp.HeadApplyPresenter;
import com.aifubook.book.regimental.mvp.HeadApplyView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class HeadApplyActivity extends BaseActivity<HeadApplyPresenter> implements HeadApplyView {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.iv_check)
    ImageView iv_check;
    @BindView(R.id.tv_send_code)
    TextView tv_send_code;
    @BindView(R.id.tv_detailedAddress)
    EditText tv_detailedAddress;
    @BindView(R.id.tv_professional)
    TextView tv_professional;
    @BindView(R.id.SelectAddress)
    TextView SelectAddress;
    @BindView(R.id.tv_selectGrade)
    TextView tv_selectGrade;
    @BindView(R.id.et_class)
    EditText et_class;
    @BindView(R.id.iv_qualification)
    ImageView image;

    @BindView(R.id.et_school)
    EditText et_school;

    private String grade = "";

    private boolean isChecked = true;
    private OptionsPickerView pvCustomOptions;//交易类型自定义选择器

    String address;
    String proId = "0";
    String cityId = "0";
    String dirId = "0";
    JSONArray jsonObject;

//    String SchoolId = "";
//    String ClassId = "";
    List<SchoolBean> adds = new ArrayList<>();
    List<String> gradeList = new ArrayList<>();
//    List<String> addsSchool = new ArrayList<>();

    String[] grades = new String[]{"一年级","二年级","三年级","四年级","五年级","六年级","七年级","八年级","九年级"};

    List<FindSchoolClassesBean> ClassBeans = new ArrayList<>();
    List<String> addClass = new ArrayList<>();

    String pid = "";
    List<String> addProfessional = new ArrayList<>();

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

    @BindView(R.id.et_inviteCode)
    EditText et_inviteCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_head_apply_g;
    }

    @Override
    public void initPresenter() {
        mPresenter = new HeadApplyPresenter(this);
    }

    String type = "3";

    @Override
    public void initView() {
        setTitle("团长报名申请");

        et_inviteCode.setText(getIntent().getExtras().getString("inivCode"));


        addProfessional.add("教师");
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
                tv_submit.setVisibility(View.VISIBLE);
                BigContent.setVisibility(View.VISIBLE);
                break;
        }

        address = getFromAssets("address.json");

        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        gradeList.clear();
        for (int i=0;i<grades.length;i++){
            gradeList.add(grades[i]);
        }


    }


    @OnClick(R.id.prisitionManger)
    void prisitionManger() {
        Bundle bundle = new Bundle();
        bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/chiefAgreement.html");
        bundle.putString("title", "团长入驻协议");
        startActivity(PriviteActivity.class, bundle);
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

    @OnClick(R.id.tv_send_code)
    void tv_send_code() {//发送验证码
        if (StringUtils.isMobileNO(et_account.getText().toString())) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        WeakReference<TextView> tvVerifyCode = new WeakReference<>(tv_send_code);
        TimeUtil.startTimer(tvVerifyCode, "获取验证码", 60, 1);
        sedTel();
    }

    @OnClick({R.id.success_back, R.id.failBack, R.id.Search_back, R.id.tv_professional, R.id.SelectAddress, R.id.tv_selectGrade})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.success_back:
            case R.id.Search_back:
                HeadApplyActivity.this.finish();
                break;
            case R.id.failBack:
                fail.setVisibility(View.GONE);
                searchNow.setVisibility(View.GONE);
                tv_submit.setVisibility(View.VISIBLE);
                BigContent.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_professional:
                pvCustomOptions(4, addProfessional);
                break;
            case R.id.SelectAddress:
                selectAddress();
                break;
            case R.id.tv_selectGrade:
                if (StringUtils.isEmpty(et_school.getText().toString())) {
                    ToastUitl.showShort(HeadApplyActivity.this, "请先输入学校");
                    return;
                }
                pvCustomOptions(9, gradeList);
                break;
          /*  case R.id.SelectClass:
                if (StringUtils.isEmpty(SchoolId)) {
                    ToastUitl.showShort(HeadApplyActivity.this, "请先选择学校");
                    return;
                }
                findSchoolClasses();
                break;*/
        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(HeadApplyActivity.this)
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
                findByCityId(dirId);
                //为TextView赋值
                SelectAddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }


    // 获取下面得学校
    private void findByCityId(String cityId) {
        Map<String, String> map = new HashMap<>();
        map.put("cityId", "" + cityId);
        mPresenter.findByCityId(map);
    }

    // 获取下面得学校
    private void findSchoolClasses() {
        Map<String, Object> map = new HashMap<>();
//        map.put("schoolId", "" + SchoolId);
        mPresenter.findSchoolClasses(map);
    }


    private <T> void pvCustomOptions(int type, List<T> data) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                if (StringUtils.isEmpty(options1 + "")) {
                    return;
                }
                if (data.size() == 0) {
                    return;
                }

                //返回的分别是三个级别的选中位置
                if (type == 2) {
                    if (StringUtils.isEmpty(options1 + "")) {
                        return;
                    }

                    if (data.size() == 0) {
                        return;
                    }

//                    tv_selectGrade.setText(addsSchool.get(options1));
//                    SchoolId = adds.get(options1).getId() + "";
                } else if (type == 3) {
                    if (StringUtils.isEmpty(options1 + "")) {
                        return;
                    }
                    if (data.size() == 0) {
                        return;
                    }
                    tv_selectGrade.setText(addClass.get(options1) + "");
//                    ClassId = ClassBeans.get(options1).getId() + "";
                } else if (type == 4) {
                    if (StringUtils.isEmpty(options1 + "")) {
                        return;
                    }
                    if (data.size() == 0) {
                        return;
                    }
                    tv_professional.setText(addProfessional.get(options1));
                    pid = "1";
                }else if(type==9){
                    grade=gradeList.get(options1);
                    tv_selectGrade.setText(grade);
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

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();

        pvCustomOptions.setPicker(data);//添加数据
        pvCustomOptions.show();
    }

    private static final int REQ_PERMISSION_CODE = 0x100;

    //动态权限检查
    public boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(activity,
                        permissionsArray,
                        REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    List<String> fileList = new ArrayList<>();
    private String uploadImage = "";

    /**
     * 上传教师资格证
     */
    @OnClick(R.id.iv_qualification)
    void imageUpload() {
        if (!checkPermission(this)) {
            return;
        }
//        // 进入相册 以下是例子：不需要的api可以不写
//            int themeId = R.style.picture_default_style;
        PictureSelector.create(HeadApplyActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
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
                    fileList.clear();
                    for (LocalMedia media : selectList) {
                        Log.e("图片-----》", media.getCompressPath());
                        fileList.add(media.getCompressPath());
//                        stringMap.get().put("1-1", media.getCompressPath());
//                        mPresenter.getSendImage(stringMap);
                    }
                    GetData();
                    break;
            }
        }
    }

    private void GetData() {
        Map<String, String> map = new HashMap<>();
        List<File> showImgFile = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            if (!fileList.get(i).equals("无")) {
                showImgFile.add(new File(fileList.get(i)));
            }
        }
        if (showImgFile.size() == 0) {
            return;
        }
        mPresenter.uploadImage(map, showImgFile);
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


    /**
     * 发送验证码
     */
    private void sedTel() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "" + et_account.getText().toString());
        map.put("scene", "" + 2);
        mPresenter.sendSmsCode(map);
    }


    /**
     * 我已阅读同意《团长申请协议》
     */
    @OnClick(R.id.iv_check)
    void iv_check() {
        if (isChecked) {
            isChecked = false;
            iv_check.setImageResource(R.mipmap.icon_unchecked);
        } else {
            isChecked = true;
            iv_check.setImageResource(R.mipmap.icon_checked);
        }
    }

    /**
     * 提交申请
     */
    @OnClick(R.id.tv_submit)
    void tv_submit() {
        if (StringUtils.isEmpty(et_name.getText().toString())) {
            Toast.makeText(mContext, "请输入联系人姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(et_account.getText().toString())) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(et_code.getText().toString())) {
            Toast.makeText(mContext, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pid)) {
            ToastUitl.showShort(HeadApplyActivity.this, "请选择职业");
            return;
        }
        if (StringUtils.isEmpty(dirId)) {
            ToastUitl.showShort(HeadApplyActivity.this, "请选择城市");
            return;
        }

        if (StringUtils.isEmpty(tv_detailedAddress.getText().toString())) {
            ToastUitl.showShort(HeadApplyActivity.this, "请输入街道门牌等信息");
            return;
        }

        if (StringUtils.isEmpty(et_school.getText().toString())) {
            ToastUitl.showShort(HeadApplyActivity.this, "请输入学校");
            return;
        }
        if (StringUtils.isEmpty(grade)) {
            ToastUitl.showShort(HeadApplyActivity.this, "请选择年级");
            return;
        }
        if (StringUtils.isEmpty(et_class.getText().toString())) {
            ToastUitl.showShort(HeadApplyActivity.this, "请输入班级");
            return;
        }

        if (StringUtils.isEmpty(uploadImage)) {
            ToastUitl.showShort(HeadApplyActivity.this, "请上传教师资格证");
            return;
        }
        if (!isChecked) {
            Toast.makeText(mContext, "请阅读同意《团长申请协议》", Toast.LENGTH_SHORT).show();
            return;
        }

        Map map = new HashMap();
//        map.put("shopInviteCode","");//店铺邀请码
        map.put("code", et_code.getText().toString());
        map.put("mobile", et_account.getText().toString());
        map.put("name", et_name.getText().toString());
        map.put("jobId", pid);//职业Id
        map.put("schoolName", et_school.getText().toString());//学校Id
        map.put("className", et_class.getText().toString());//班级Id
        map.put("grade", grade);
        map.put("credentials", uploadImage + "");
        map.put("provinceId", proId);
        map.put("districtId", dirId);
//        map.put("classId","");
//        map.put("schoolId","");
        map.put("cityId", cityId);
        map.put("shopInviteCode", "" + et_inviteCode.getText().toString());
        map.put("address", tv_detailedAddress.getText().toString());//详细地址
        mPresenter.chiefApply(map);
    }


    @Override
    public void ChiefApplySuc(String DataBean) {
        Toast.makeText(mContext, "申请已提交", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void ChiefDetailSuc(ChiefDetailsBean DataBean) {

    }

    @Override
    public void ChiefMembersSuc(ChiefMembersBean DataBean) {

    }

    @Override
    public void ChiefMyChiefSuc(ChiefMyChiefBean DataBean) {

    }

    @Override
    public void GetDataSuc(List<SchoolBean> DataBean) {
        adds.clear();
//        addsSchool.clear();
        adds.addAll(DataBean);
//        for (int i = 0; i < DataBean.size(); i++) {
//            addsSchool.add(DataBean.get(i).getName());
//        }
    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetListDataSuc(List<FindSchoolClassesBean> DataBean) {
        ClassBeans.clear();
        addClass.clear();
        ClassBeans.addAll(DataBean);
        for (int i = 0; i < DataBean.size(); i++) {
            addClass.add(DataBean.get(i).getName());
        }
        pvCustomOptions(3, addClass);
    }

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {

    }

    @Override
    public void UploadImageSuc(String DataBean) {
        if (!StringUtils.isEmpty(DataBean)) {

            uploadImage = ApiService.IMAGE + DataBean;
            Glide.with(this)
                    .load(uploadImage)
                    .into(image);

          /*  try {
                JSONObject jsonObject = new JSONObject(DataBean);
                String pic = jsonObject.optString("result");

                uploadImage = ApiService.IMAGE+pic;
                Glide.with(this)
                        .load(uploadImage)
                        .into(image);

            } catch (JSONException e) {
                e.printStackTrace();
            }*/


        }

    }

    @Override
    public void GetChiefOrderByCodeSuc(ChiefOrderByCodeBean DataBean) {

    }

    @Override
    public void SetFetchedSuc(String DataBean) {

    }

    @Override
    public void OrderListSuc(OrderListBean DataBean) {

    }

    @Override
    public void RecordListSuc(CommissionDetailsBean DataBean) {

    }

    @Override
    public void CommissionApplySuc(String DataBean) {

    }

    @Override
    public void OrderCreateSuc(RechargeBean DataBean) {

    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetDataSucs(ProductListBean DataBean) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}