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
    private OptionsPickerView pvCustomOptions;//??????????????????????????????

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

    String[] grades = new String[]{"?????????","?????????","?????????","?????????","?????????","?????????","?????????","?????????","?????????"};

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
        setTitle("??????????????????");

        et_inviteCode.setText(getIntent().getExtras().getString("inivCode"));


        addProfessional.add("??????");
//        0 :????????? 1 :?????????????????? 2 :????????????
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
        bundle.putString("title", "??????????????????");
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
    void tv_send_code() {//???????????????
        if (StringUtils.isMobileNO(et_account.getText().toString())) {
            Toast.makeText(mContext, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        WeakReference<TextView> tvVerifyCode = new WeakReference<>(tv_send_code);
        TimeUtil.startTimer(tvVerifyCode, "???????????????", 60, 1);
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
                    ToastUitl.showShort(HeadApplyActivity.this, "??????????????????");
                    return;
                }
                pvCustomOptions(9, gradeList);
                break;
          /*  case R.id.SelectClass:
                if (StringUtils.isEmpty(SchoolId)) {
                    ToastUitl.showShort(HeadApplyActivity.this, "??????????????????");
                    return;
                }
                findSchoolClasses();
                break;*/
        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(HeadApplyActivity.this)
                .textSize(14)
                .title("????????????")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("?????????")
                .city("?????????")
                .district("?????????")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //?????????????????????????????????
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //??????
                String province = citySelected[0];
                //??????
                String city = citySelected[1];
                //???????????????????????????????????????????????????????????????
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
                //???TextView??????
                SelectAddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }


    // ?????????????????????
    private void findByCityId(String cityId) {
        Map<String, String> map = new HashMap<>();
        map.put("cityId", "" + cityId);
        mPresenter.findByCityId(map);
    }

    // ?????????????????????
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

                //?????????????????????????????????????????????
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

        pvCustomOptions.setPicker(data);//????????????
        pvCustomOptions.show();
    }

    private static final int REQ_PERMISSION_CODE = 0x100;

    //??????????????????
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
     * ?????????????????????
     */
    @OnClick(R.id.iv_qualification)
    void imageUpload() {
        if (!checkPermission(this)) {
            return;
        }
//        // ???????????? ??????????????????????????????api????????????
//            int themeId = R.style.picture_default_style;
        PictureSelector.create(HeadApplyActivity.this)
                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
//                    .theme(themeId)// ?????????????????? ???????????? values/styles   ?????????R.style.picture.white.style
                .maxSelectNum(1)// ????????????????????????
                .minSelectNum(1)// ??????????????????
                .imageSpanCount(4)// ??????????????????
                .selectionMode(PictureConfig.MULTIPLE)// ??????PictureConfig.MULTIPLE or ??????PictureConfig.SINGLE
                .previewImage(true)// ?????????????????????
                .previewVideo(false)// ?????????????????????
                .enablePreviewAudio(false) // ?????????????????????
                .isCamera(true)// ????????????????????????
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                //.imageFormat(PictureMimeType.PNG)// ??????????????????????????????,??????jpeg
                //.setOutputCameraPath("/CustomPath")// ???????????????????????????
                .enableCrop(false)// ????????????
                .compress(true)// ????????????
                .synOrAsy(true)//??????true?????????false ?????? ????????????
                .compressSavePath(getPath())//????????????????????????
                //.sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .withAspectRatio(1, 1)// ???????????? ???16:9 3:2 3:4 1:1 ????????????
                .hideBottomControls(true)// ????????????uCrop???????????????????????????
                .isGif(false)// ????????????gif??????
                .freeStyleCropEnabled(true)// ????????????????????????
                .circleDimmedLayer(false)// ??????????????????
                .showCropFrame(true)// ?????????????????????????????? ???????????????????????????false
                .showCropGrid(true)// ?????????????????????????????? ???????????????????????????false
                .openClickSound(false)// ????????????????????????
//                .selectionMedia(selectList)// ????????????????????????
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// ??????????????? ????????????????????????????????????(???????????????????????????????????????????????????)
                //.cropCompressQuality(90)// ?????????????????? ??????100
                .minimumCompressSize(100)// ??????100kb??????????????????
                //.cropWH()// ???????????????????????????????????????????????????????????????
                //.rotateEnabled() // ???????????????????????????
                //.scaleEnabled()// ?????????????????????????????????
                //.videoQuality()// ?????????????????? 0 or 1
                //.videoSecond()//??????????????????????????????or??????????????????
                //.recordVideoSecond()//?????????????????? ??????60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//????????????onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // ????????????????????????
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // ?????? LocalMedia ??????????????????path
                    // 1.media.getPath(); ?????????path
                    // 2.media.getCutPath();????????????path????????????media.isCut();?????????true
                    // 3.media.getCompressPath();????????????path????????????media.isCompressed();?????????true
                    // ????????????????????????????????????????????????????????????????????????????????????
                    fileList.clear();
                    for (LocalMedia media : selectList) {
                        Log.e("??????-----???", media.getCompressPath());
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
            if (!fileList.get(i).equals("???")) {
                showImgFile.add(new File(fileList.get(i)));
            }
        }
        if (showImgFile.size() == 0) {
            return;
        }
        mPresenter.uploadImage(map, showImgFile);
    }

    /**
     * ???????????????????????????
     *
     * @return ??????????????????
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
     * ???????????????
     */
    private void sedTel() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "" + et_account.getText().toString());
        map.put("scene", "" + 2);
        mPresenter.sendSmsCode(map);
    }


    /**
     * ??????????????????????????????????????????
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
     * ????????????
     */
    @OnClick(R.id.tv_submit)
    void tv_submit() {
        if (StringUtils.isEmpty(et_name.getText().toString())) {
            Toast.makeText(mContext, "????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(et_account.getText().toString())) {
            Toast.makeText(mContext, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(et_code.getText().toString())) {
            Toast.makeText(mContext, "????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pid)) {
            ToastUitl.showShort(HeadApplyActivity.this, "???????????????");
            return;
        }
        if (StringUtils.isEmpty(dirId)) {
            ToastUitl.showShort(HeadApplyActivity.this, "???????????????");
            return;
        }

        if (StringUtils.isEmpty(tv_detailedAddress.getText().toString())) {
            ToastUitl.showShort(HeadApplyActivity.this, "??????????????????????????????");
            return;
        }

        if (StringUtils.isEmpty(et_school.getText().toString())) {
            ToastUitl.showShort(HeadApplyActivity.this, "???????????????");
            return;
        }
        if (StringUtils.isEmpty(grade)) {
            ToastUitl.showShort(HeadApplyActivity.this, "???????????????");
            return;
        }
        if (StringUtils.isEmpty(et_class.getText().toString())) {
            ToastUitl.showShort(HeadApplyActivity.this, "???????????????");
            return;
        }

        if (StringUtils.isEmpty(uploadImage)) {
            ToastUitl.showShort(HeadApplyActivity.this, "????????????????????????");
            return;
        }
        if (!isChecked) {
            Toast.makeText(mContext, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        Map map = new HashMap();
//        map.put("shopInviteCode","");//???????????????
        map.put("code", et_code.getText().toString());
        map.put("mobile", et_account.getText().toString());
        map.put("name", et_name.getText().toString());
        map.put("jobId", pid);//??????Id
        map.put("schoolName", et_school.getText().toString());//??????Id
        map.put("className", et_class.getText().toString());//??????Id
        map.put("grade", grade);
        map.put("credentials", uploadImage + "");
        map.put("provinceId", proId);
        map.put("districtId", dirId);
//        map.put("classId","");
//        map.put("schoolId","");
        map.put("cityId", cityId);
        map.put("shopInviteCode", "" + et_inviteCode.getText().toString());
        map.put("address", tv_detailedAddress.getText().toString());//????????????
        mPresenter.chiefApply(map);
    }


    @Override
    public void ChiefApplySuc(String DataBean) {
        Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
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