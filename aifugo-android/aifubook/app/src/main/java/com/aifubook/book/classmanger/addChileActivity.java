package com.aifubook.book.classmanger;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.bean.getKidBean;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/5/11 1:11
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class addChileActivity extends BaseActivity<ClassPresenter> implements ClassView {

    @BindView(R.id.SelectName)
    EditText SelectName;

    @BindView(R.id.SelectAge)
    TextView SelectAge;

    @BindView(R.id.SelectSex)
    TextView SelectSex;

    @BindView(R.id.SelectAddress)
    TextView SelectAddress;

    @BindView(R.id.SelectSchool)
    TextView SelectSchool;

    @BindView(R.id.SelectClass)
    TextView SelectClass;

    private OptionsPickerView pvCustomOptions;//交易类型自定义选择器


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_chile;
    }

    @Override
    public void initPresenter() {
           mPresenter = new ClassPresenter(this);
    }

    String address;
    String proId = "0";
    String cityId = "0";
    String dirId = "0";

    JSONArray jsonObject;

    String typeSelect;

    @Override
    public void initView() {
      setTitle("用户管理");

        address = getFromAssets("address.json");

        typeSelect = getIntent().getExtras().getString("typeSelect");

        if (typeSelect.equals("2")){
            getChildById(getIntent().getExtras().getString("id"));
        }

        try {
            jsonObject = new JSONArray(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sex.add("男");
        Sex.add("女");

        for (int i=3;i<20;i++){
            Age.add(i+"");
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

    @OnClick({R.id.addChil,R.id.SelectAge,R.id.SelectSex,R.id.SelectAddress,R.id.SelectSchool,R.id.SelectClass})
    void Onclick(View view){
        switch (view.getId()){
            case R.id.addChil:
                if (StringUtils.isEmpty(SelectName.getText().toString())){
                    return;
                }
                if (StringUtils.isEmpty(SelectAge.getText().toString())){
                    return;
                }
                if (StringUtils.isEmpty(SelectAddress.getText().toString())){
                    return;
                }
                if (StringUtils.isEmpty(SelectSex.getText().toString())){
                    return;
                }

                if (typeSelect.equals("1")){
                    childaddChild();
                }else{
                    childUpdateChild();
                }
                break;
            case R.id.SelectAge:
                pvCustomOptions(1,Age);
                break;
            case R.id.SelectSex:
                pvCustomOptions(0,Sex);
                break;
            case R.id.SelectAddress:
                selectAddress();
                break;
            case R.id.SelectSchool:
                if (StringUtils.isEmpty(dirId)){
                    ToastUitl.showShort(addChileActivity.this,"请先选择城市");
                    return;
                }
                pvCustomOptions(2,addsSchool);
                break;
            case R.id.SelectClass:
                if (StringUtils.isEmpty(SchoolId)){
                    ToastUitl.showShort(addChileActivity.this,"请先选择学校");
                    return;
                }
                findSchoolClasses();
                break;
        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(addChileActivity.this)
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

                for (int i=0;i<jsonObject.length();i++){
                    try {
                        if (jsonObject.getJSONObject(i).get("name").equals(province)){
                            proId = jsonObject.getJSONObject(i).get("id")+"";
                            JSONArray jsonArray = jsonObject.getJSONObject(i).getJSONArray("children");
                            for (int j=0;j<jsonArray.length();j++) {
                                if (jsonArray.getJSONObject(j).get("name").equals(city)){
                                cityId = jsonArray.getJSONObject(j).get("id")+"";

                                    JSONArray jsonArrayD = jsonArray.getJSONObject(j).getJSONArray("children");
                                    for (int k=0;k<jsonArrayD.length();k++) {
                                        if (jsonArrayD.getJSONObject(k).get("name").equals(district)){
                                            dirId = jsonArrayD.getJSONObject(k).get("id")+"";
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

    List<String> Sex = new ArrayList<>();
    List<String> Age = new ArrayList<>();

    // 获取下面得学校
    private void  getChildById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id",""+id);
        mPresenter.getChildById(map);
    }

    // 获取下面得学校
    private void  findByCityId(String cityId){
        Map<String, String> map = new HashMap<>();
        map.put("cityId",""+cityId);
        mPresenter.findByCityId(map);
    }

    // 获取下面得学校
    private void  findSchoolClasses(){
        Map<String, Object> map = new HashMap<>();
        map.put("schoolId",""+SchoolId);
        mPresenter.findSchoolClasses(map);
    }

    // 获取下面得学校
    private void  childaddChild(){
        Map<String, Object> map = new HashMap<>();
        map.put("age",""+SelectAge.getText().toString());
        map.put("cityId",""+cityId);
        map.put("classId",""+ClassId);
        map.put("districtId",""+dirId);
        map.put("name",""+SelectName.getText().toString());
        map.put("provinceId",""+proId);
        map.put("schoolId",""+SchoolId);
        map.put("sex",""+SexS);
        mPresenter.childaddChild(map);
    }

    // 获取下面得学校
    private void  childUpdateChild(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",""+getIntent().getExtras().getString("id"));
        map.put("age",""+SelectAge.getText().toString());
        map.put("cityId",""+cityId);
        map.put("classId",""+ClassId);
        map.put("districtId",""+dirId);
        map.put("name",""+SelectName.getText().toString());
        map.put("provinceId",""+proId);
        map.put("schoolId",""+SchoolId);
        map.put("sex",""+SexS);
        mPresenter.childUpdateChild(map);
    }


    String SchoolId = "";
    String SexS = "";
    private <T> void pvCustomOption(int type) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                if (type == 0) {
                    SelectSex.setText(Sex.get(options1));
                    if (options1==0){
                        SexS = "1";
                    }else{
                        SexS = "2";
                    }
                } else if (type == 1) {
                    SelectAge.setText(Age.get(options1));
                } else if (type == 2){
                    SelectSchool.setText(addsSchool.get(options1));
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

//        pvCustomOptions.setPicker(data);//添加数据
        pvCustomOptions.show();
    }

    String ClassId = "";

    private <T> void pvCustomOptions(int type, List<T> data) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                if (type == 0) {
                    SelectSex.setText(Sex.get(options1));
                } else if (type == 1) {
                    SelectAge.setText(Age.get(options1));
                } else if (type == 2){
                    if (StringUtils.isEmpty(options1+"")){
                        return;
                    }
                    SelectSchool.setText(addsSchool.get(options1));
                    SchoolId = adds.get(options1).getId()+"";
                }else if (type == 3){
                    if (StringUtils.isEmpty(options1+"")){
                        return;
                    }
                    SelectClass.setText(addClass.get(options1));
                    ClassId = ClassBeans.get(options1).getId()+"";
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


    //    下面是網絡接口的回調
    @Override
    public void GetDataSuc(List<SchoolBean> DataBean) {
        adds.clear();
        addsSchool.clear();
        adds.addAll(DataBean);
        for (int i=0;i<DataBean.size();i++){
            addsSchool.add(DataBean.get(i).getName());
        }
    }

    @Override
    public void GetDataFail(String Message) {
        ToastUitl.showShort(this,Message+"!");

    }

    @Override
    public void GetShopSuc(List<ClassBean> DataBean) {

    }

    @Override
    public void GetShopFail(String Message) {
        ToastUitl.showShort(this,Message+"!");

    }

    @Override
    public void GetListDataSuc(List<FindSchoolClassesBean> DataBean) {
        ClassBeans.clear();
        addClass.clear();
        ClassBeans.addAll(DataBean);
        for (int i=0;i<DataBean.size();i++){
            addClass.add(DataBean.get(i).getName());
        }
        pvCustomOptions(3,addClass);
    }

    @Override
    public void AddDataSuc(String DataBean) {
        ToastUitl.showShort(this,"添加用户成功!");
        addChileActivity.this.finish();
    }

    @Override
    public void childDelete(String DataBean) {
    }

    @Override
    public void getChildById(getKidBean DataBean) {
        SelectAge.setText(StringUtils.isNull(DataBean.getAge()+""));
        SelectName.setText(StringUtils.isNull(DataBean.getName()+""));
        cityId = StringUtils.isNull(DataBean.getSchool().getCity());
        dirId = StringUtils.isNull(DataBean.getSchool().getDistrictId()+"");
        proId = StringUtils.isNull(DataBean.getSchool().getProvinceId()+"");
        SchoolId = StringUtils.isNull(DataBean.getSchoolId()+"");

        ClassId = StringUtils.isNull(DataBean.getClassId()+"");
        if (StringUtils.isNull(DataBean.getSex()+"").equals("1")){
            SelectSex.setText("男");
        }else{
            SelectSex.setText("女");
        }
        SexS = StringUtils.isNull(DataBean.getSex()+"");

//        map.put("id",""+getIntent().getExtras().getString("id"));
//        map.put("age",""+SelectAge.getText().toString());
//        map.put("cityId",""+cityId);
//        map.put("classId",""+ClassId);
//        map.put("districtId",""+dirId);
//        map.put("name",""+SelectName.getText().toString());
//        map.put("provinceId",""+proId);
//        map.put("schoolId",""+SchoolId);
//        map.put("sex",""+SexS);
    }

    @Override
    public void GetListDataFail(String Message) {
        ToastUitl.showShort(this,Message+"!");

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
