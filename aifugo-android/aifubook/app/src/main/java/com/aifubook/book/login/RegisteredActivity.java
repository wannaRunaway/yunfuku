package com.aifubook.book.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.setting.PriviteActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUitl;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity<PhoneLoginPresenter> implements PhoneLoginView {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_selectArea)
    TextView tv_selectArea;
    @BindView(R.id.tv_school)
    TextView tv_school;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.iv_check)
    ImageView iv_check;

    @BindView(R.id.tv_send_code)
    TextView tv_send_code;


    private boolean isChecked = false;
    private ArrayList<String> cardItem = new ArrayList<>();//数据源
    private ArrayList<Integer> cardItem2 = new ArrayList<>();//返回后台月份
    private OptionsPickerView pvCustomOptions;//交易类型自定义选择器


    String address;
    String proId = "0";
    String cityId = "0";
    String dirId = "0";

    JSONArray jsonObject;

    @Override
    public int getLayoutId() {
        return R.layout.activity_registered;
    }

    @Override
    public void initPresenter() {
        mPresenter = new PhoneLoginPresenter(this);
    }

    @Override
    public void initView() {
        setTitle("");
        et_inviteCode.setText(getIntent().getExtras().getString("invicode"));
        address = getFromAssets("address.json");

        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void selectAddress() {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_code.getWindowToken(),0);

        CityPicker cityPicker = new CityPicker.Builder(RegisteredActivity.this)
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
                findByCityId(dirId);
                //为TextView赋值
                tv_selectArea.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
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
        map.put("schoolId", "" + SchoolId);
        mPresenter.findSchoolClasses(map);
    }


    @OnClick(R.id.USerManger)
    void USerManger() {
        Bundle bundle = new Bundle();
        bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/userinfoAgreement.html");
        bundle.putString("title", "用户协议");
        startActivity(PriviteActivity.class, bundle);
    }

    @OnClick(R.id.prisitionManger)
    void prisitionManger() {
        Bundle bundle = new Bundle();
        bundle.putString("fig", "https://api.aifubook.com/bookstatic/html/privacy-policy-privacy.html");
        bundle.putString("title", "隐私条款");
        startActivity(PriviteActivity.class, bundle);
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
     * 选择所在区域
     */
    @OnClick(R.id.tv_selectArea)
    void tv_selectArea() {
        selectAddress();
    }

    String ClassId = "";
    String SchoolId = "";

    private <T> void pvCustomOptions(int type, List<T> data) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                if (type == 0) {
                } else if (type == 1) {
                } else if (type == 2) {
                    if (addsSchool.size() == 0) {
                        return;
                    }
                    tv_school.setText(addsSchool.get(options1));
                    SchoolId = adds.get(options1).getId() + "";
                } else if (type == 3) {
                    if (addClass.size() == 0) {
                        return;
                    }
                    tv_class.setText(addClass.get(options1));
                    ClassId = ClassBeans.get(options1).getId() + "";
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

    List<SchoolBean> adds = new ArrayList<>();
    List<String> addsSchool = new ArrayList<>();

    List<FindSchoolClassesBean> ClassBeans = new ArrayList<>();
    List<String> addClass = new ArrayList<>();

    /**
     * 勾选同意用户隐私协议
     */
    @OnClick(R.id.iv_check)
    void iv_check() {
        if (isChecked) {
            isChecked = false;
            iv_check.setImageResource(R.mipmap.icon_uncheck);
        } else {
            isChecked = true;
            iv_check.setImageResource(R.mipmap.icon_check);
        }
    }

    @OnClick({R.id.tv_school, R.id.tv_class})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.addChil:
//                if (StringUtils.isEmpty(SelectName.getText().toString())){
//                    return;
//                }
//                if (StringUtils.isEmpty(SelectAge.getText().toString())){
//                    return;
//                }
//                if (StringUtils.isEmpty(SelectAddress.getText().toString())){
//                    return;
//                }
//                if (StringUtils.isEmpty(SelectSex.getText().toString())){
//                    return;
//                }
//                childaddChild();
                break;
            case R.id.tv_school:
                if (StringUtils.isEmpty(dirId)) {
                    ToastUitl.showShort(RegisteredActivity.this, "请先选择城市");
                    return;
                }
                pvCustomOptions(2, addsSchool);
                break;
            case R.id.tv_class:
                if (StringUtils.isEmpty(SchoolId)) {
                    ToastUitl.showShort(RegisteredActivity.this, "请先选择学校");
                    return;
                }
                findSchoolClasses();
                break;
        }
    }

    /**
     * 注册
     */
    @OnClick(R.id.tv_registered)
    void tv_registered() {
        if (StringUtils.isEmpty(et_account.getText().toString())) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(et_code.getText().toString())) {
            Toast.makeText(mContext, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(dirId)) {
            Toast.makeText(mContext, "请选择区域", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(SchoolId)) {
            Toast.makeText(mContext, "请选择学校", Toast.LENGTH_SHORT).show();
        }

        if (!isChecked) {
            Toast.makeText(mContext, "请同意并勾选《用户协议》和《隐私条款》", Toast.LENGTH_SHORT).show();
            return;
        }
        Register();
    }

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_inviteCode)
    EditText et_inviteCode;

    private void Register() {
        Map<String, String> map = new HashMap<>();
        map.put("cityId", "" + cityId);

        if (!StringUtils.isEmpty(ClassId)) {
            map.put("classId", "" + ClassId);
        }

        if (!StringUtils.isEmpty(et_name.getText().toString())) {
            map.put("name", "" + et_name.getText().toString());
        }

        if (!StringUtils.isEmpty(SchoolId)) {
            map.put("schoolId", "" + SchoolId);
        }

        map.put("districtId", "" + dirId);

        map.put("provinceId", "" + proId);
        map.put("mobile", "" + et_account.getText().toString());

        map.put("inviteCode", "" + et_inviteCode.getText().toString());
        map.put("code", "" + et_code.getText().toString());
        map.put("address","");
        map.put("jobId","1");

        mPresenter.registerChiefApply(map);
    }

    @Override
    public void GetverificationCodeSuc(Integer DataBean) {
        ToastUitl.showShort(this, "验证码发送成功!");
        WeakReference<TextView> tvVerifyCode = new WeakReference<>(tv_send_code);
        TimeUtil.startTimer(tvVerifyCode, "获取验证码", 60, 1);
    }

    @Override
    public void GetverificationCodeFail(String Message) {
        Toast.makeText(mContext, Message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetHomePageSuc(LoginHomePageBean DataBean) {

    }

    @Override
    public void UserRegisterSuc(LoginHomePageBean DataBean) {

//        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.IS_LOGIN, "1");
//        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.PHONE, ""+DataBean.getUsername());
//        SharedPreferencesUtil.put(MyApp.getInstance(), KeyCom.USER_ID, "");
        ToastUitl.showShort(this,"注册成功!");
//        startActivity(MainActivity.class);
        this.finish();
    }

    @Override
    public void GetHomePageFail(String Message) {
        Toast.makeText(mContext, "接口返回异常：" + Message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    //    下面是網絡接口的回調
    @Override
    public void GetDataSuc(List<SchoolBean> DataBean) {
        adds.clear();
        addsSchool.clear();
        adds.addAll(DataBean);
        for (int i = 0; i < DataBean.size(); i++) {
            addsSchool.add(DataBean.get(i).getName());
        }
    }

    @Override
    public void GetDataFail(String Message) {

    }

    @Override
    public void GetShopSuc(List<ClassBean> DataBean) {

    }

    @Override
    public void GetShopFail(String Message) {

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
    public void AddDataSuc(String DataBean) {

    }

    @Override
    public void GetListDataFail(String Message) {

    }

    @Override
    public void weChatLoginForApp(String DataBean) {

    }

    @Override
    public void weChatLoginForFail(String DataBean) {

    }

    @Override
    public void registerChief() {
        ToastUitl.showShort(this,"注册成功!");
        this.finish();
    }

    @Override
    public void registerChiefError(String msg) {

    }
}