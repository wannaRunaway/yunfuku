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
        title.setText("????????????");
//        Map map = new HashMap();
//        mPresenter.memberInfo(map);
        address = getFromAssets("address.json");
        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sex.add("???");
        Sex.add("???");
        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.textview_teacher:
                    identityDialog.dismiss();
                    tv_identify.setText("??????");
                    registerMember();
                    break;
                case R.id.textview_student:
                    identityDialog.dismiss();
                    tv_identify.setText("??????");
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
//            ToastUtil.showToast("??????????????????", false);
//            return;
//        }
//        if (StringUtils.isEmpty(nickname)) {
//            ToastUtil.showToast("??????????????????", false);
//            return;
//        }
//        if (StringUtils.isEmpty(name)) {
//            ToastUtil.showToast("????????????????????????", false);
//            return;
//        }
//        if (StringUtils.isEmpty(address)) {
//            ToastUtil.showToast("?????????????????????", false);
//            return;
//        }
//        if(StringUtils.isEmpty(ImgTwo)){
//            ToastUtil.showToast("???????????????",false);
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
            case "??????":
                identity = 0;
                break;
            case "??????":
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
        EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance())//?????????????????????????????????????????????????????????????????????????????????false??????????????????0???????????????????????????[??????Glide?????????????????????](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.aifubook.book.fileprovider")//????????????????????????`FileProvider?????????`
                .setCount(1)//???????????????????????????????????????1
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        mPath.clear();
                        mPath.add(photos.get(0).path);
                        startProgressDialog("????????????");
                        GetData();
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.d("onCancel()........");
                    }
                });

    }

    //??????
    String province = "";
    //??????
    String city = "";
    //???????????????????????????????????????????????????????????????
    String district = "";

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(PersonalUpdataActivity.this)
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
//                findByCityId(dirId);
                //???TextView??????
                tv_home.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    String ClassId = "";
    String SexS = "1";
    private OptionsPickerView pvCustomOptions;//??????????????????????????????

    private <T> void pvCustomOptions(int type, List<T> data) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //?????????????????????????????????????????????
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

        pvCustomOptions.setPicker(data);//????????????
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
                .error(R.mipmap.icon_my_head); //??????
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
            tv_nick.setHint("???????????????");
        }
        if (!StringUtils.isEmpty(DataBean.getName())) {
            tv_name.setText(DataBean.getName());
        } else {
            tv_name.setHint("?????????????????????");
        }

        if (!StringUtils.isEmpty(DataBean.getAge())) {
            tv_age.setText(DataBean.getAge());
        } else {
            tv_age.setHint("???????????????");
        }

        if (!StringUtils.isEmpty(DataBean.getSex())) {
            if (DataBean.getSex().equals("1")) {
                tv_gender.setText("???");
            } else if (DataBean.getSex().equals("2")) {
                tv_gender.setText("???");
            } else if (DataBean.getSex().equals("0")) {
                tv_gender.setText("????????????");
            }
        } else {
            tv_gender.setText("??????");
        }
        if (!StringUtils.isEmpty(DataBean.getCity())) {
            tv_home.setText(DataBean.getProvince() + DataBean.getCity() + DataBean.getDistrict());
        } else {
            tv_home.setText("???????????????");
        }
        if (!DataBean.getSchoolName().isEmpty()) {
            et_school.setText(DataBean.getSchoolName());
        } else {
            et_school.setHint("?????????");
        }
        if (!DataBean.getGrade().isEmpty() && !DataBean.getGrade().equals("?????????")) {
            tv_class.setText(DataBean.getGrade());
        } else {
            String grade = SharedPreferencesUtil.get(MyApp.getInstance(), ShareKey.Companion.getGRADE(), ShareKey.Companion.getDefautGrade());
            tv_class.setText(grade);
        }
        if (!DataBean.getGrade().isEmpty() && !DataBean.getGrade().equals("?????????")) {
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getGRADE(), DataBean.getGrade());
        }
        if (DataBean.getRegisteredIdentity() == 0) { //0?????? 1??????
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), "??????");
            tv_identify.setText("??????");
            tv_isrecognize.setVisibility(View.GONE);
        } else {
            SharedPreferencesUtil.put(MyApp.getInstance(), ShareKey.Companion.getTEACHERORSTUDENT(), "??????");
            tv_identify.setText("??????");
            tv_isrecognize.setVisibility(View.VISIBLE);
        }
        ChiefDetailsBean chiefDetailsBean = DataBean.getChiefsVO();
        if (chiefDetailsBean != null) {//0 :????????? 1 :?????????????????? 2 :????????????
            if (chiefDetailsBean.getStatus().equals("1")) {
                tv_isrecognize.setText("?????????");
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
        tv_isrecognize.setText("?????????");
        //?????????????????????
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
     * ??????: ListKer_Hlg
     * ??????: ????????????????????????
     * ??????:
     * ??????: 2018/9/18
     */
    private void selectImg() {
//        // ???????????? ??????????????????????????????api????????????
        int themeId = R.style.picture_default_style;
        PictureSelector.create(PersonalUpdataActivity.this)
                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                .theme(themeId)// ?????????????????? ???????????? values/styles   ?????????R.style.picture.white.style
                .maxSelectNum(1)// ????????????????????????
                .minSelectNum(1)// ??????????????????
                .imageSpanCount(4)// ??????????????????
                .selectionMode(PictureConfig.SINGLE)// ??????PictureConfig.MULTIPLE or ??????PictureConfig.SINGLE
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
                .glideOverride(160, 160)// glide ???????????????????????????????????????????????????????????????????????????????????????
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

    List<String> mPath = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "onActivityResult=requestCode=" + requestCode);
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
                    mPath.clear();
                    for (LocalMedia media : selectList) {
                        Log.e("??????-----???", media.getCompressPath());
                        mPath.add(media.getCompressPath());
//                        stringMap.get().put("1-1", media.getCompressPath());
//                        mPresenter.getSendImage(stringMap);
                    }
                    startProgressDialog("????????????");
                    GetData();
                    break;
            }
        }
        if (requestCode == 100 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                //??????????????????????????????????????????????????????????????????????????????????????????????????????
                selectImg();
            } else {
                //?????????????????????????????????????????????????????????
                ToastUitl.showShort(this, "????????????????????????");
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
     * ??????: ListKer_Hlg
     * ??????: ???????????????
     * ??????:
     * ??????: 2018/11/20
     */
    int num;
    List<File> fileList = new ArrayList<>();

    private void GetData() {
        fileList.clear();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < mPath.size(); i++) {
            if (!mPath.get(i).equals("???")) {
                fileList.add(new File(mPath.get(i)));
            }
        }
        if (fileList.size() == 0) {
            return;
        }
        mPresenter.getNextImageSend(map, fileList);
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

    private String imagePath;

   /* @Override
    public void takeSuccess(TResult result) {
        imagePath = result.getImage().getCompressPath();
        mPath.clear();
        mPath.add(imagePath);
        startProgressDialog("????????????");
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