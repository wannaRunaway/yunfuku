package com.aifubook.book.mine.address;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.bean.AddressGetBean;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.ToastUitl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/5/4 18:41
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class CreatAddress extends BaseActivity<AddressPresenter> implements AddressView{

    @BindView(R.id.peopleName)
    EditText peopleName;

    @BindView(R.id.peoplePhone)
    EditText peoplePhone;

    @BindView(R.id.slectPoi)
    TextView slectPoi;

    @BindView(R.id.mSelectCity)
    TextView mSelectCity;

    @BindView(R.id.mSelectArae)
    TextView mSelectArae;

    @BindView(R.id.mAddressDetails)
    EditText mAddressDetails;

    private OptionsPickerView pvCustomOptions;//交易类型自定义选择器'

    List<AddressGetBean> addressGetBeans = new ArrayList<>();
    List<String> inSelect = new ArrayList<>();
    List<String> arrayListPoi = new ArrayList<>();
    List<String> arrayListCity = new ArrayList<>();
    List<String> arrayListArae = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.creat_address;
    }

    @Override
    public void initPresenter() {
mPresenter = new AddressPresenter(this);
    }

    String address;
    JSONArray jsonObject = null;

    String typeSelect = "1";

    @Override
    public void initView() {
        setTitle("填写收货地址");
//        AddressPickerView addressPickerView = new AddressPickerView(this);
//        apvAddress.onClick();
        address = getFromAssets("address.json");
        typeSelect = getIntent().getExtras().getString("typeSelect");
        if (typeSelect.equals("2")){
            addressCurrent();
        }
        try {
            jsonObject = new JSONArray(address);
            for (int i=0;i<jsonObject.length();i++){
                AddressGetBean addressGetBean = new AddressGetBean();
                addressGetBean.setId(jsonObject.getJSONObject(i).get("id")+"");
                addressGetBean.setName(jsonObject.getJSONObject(i).get("name")+"");
                addressGetBeans.add(addressGetBean);
                arrayListPoi.add(jsonObject.getJSONObject(i).get("name")+"");
                LogUtil.e("xxx",addressGetBeans.size()+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 添加地址
    private void  addressCurrent(){
        Map<String, String> map = new HashMap<>();
        map.put("id",""+getIntent().getExtras().getString("id"));
        mPresenter.addressCurrent(map);
    }


    @OnClick({R.id.saveAddress, R.id.slectPoi, R.id.mSelectCity, R.id.mSelectArae})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.saveAddress:
                if (StringUtils.isEmpty(PoiId)){
                    ToastUitl.showShort(this,"请选择省份");
                    return;
                }
                if (StringUtils.isEmpty(CityId)){
                    ToastUitl.showShort(this,"请选择城市");
                    return;
                }
                if (StringUtils.isEmpty(AraeId)){
                    ToastUitl.showShort(this,"请选择区域");
                    return;
                }
                if (StringUtils.isEmpty(peopleName.getText().toString())){
                    ToastUitl.showShort(this,"请输入名称");
                    return;
                }
                if (StringUtils.isEmpty(peoplePhone.getText().toString())){
                    ToastUitl.showShort(this,"请输入联系方式");
                    return;
                }
                if (StringUtils.isEmpty(mAddressDetails.getText().toString())){
                    ToastUitl.showShort(this,"请输入详细地址");
                    return;
                }

                if (typeSelect.equals("1")){
                    AddressIn();
                }else{
                    addressUpdate();
                }

                break;
            case R.id.slectPoi:
                hideSoftKeyBoard();
                inSelect.clear();
                inSelect.addAll(arrayListPoi);
                pvCustomOptions(0);
                break;
            case R.id.mSelectCity:
                hideSoftKeyBoard();
                inSelect.clear();
                inSelect.addAll(arrayListCity);
                pvCustomOptions(1);
                break;
            case R.id.mSelectArae:
                hideSoftKeyBoard();
                inSelect.clear();
                inSelect.addAll(arrayListArae);
                pvCustomOptions(2);
                break;
        }
    }

    // 添加地址
    private void  AddressIn(){
        Map<String, String> map = new HashMap<>();
        map.put("address",""+mAddressDetails.getText().toString());
        map.put("city",""+mSelectCity.getText().toString());
        map.put("cityId",""+CityId);
        map.put("district",""+mSelectArae.getText().toString());
        map.put("districtId",""+AraeId);
        map.put("mobile",""+peoplePhone.getText().toString());
        map.put("name",""+peopleName.getText().toString());
        map.put("province",""+slectPoi.getText().toString());
        map.put("provinceId",""+PoiId);
        map.put("region",""+slectPoi.getText().toString()+mSelectCity.getText().toString()+mSelectArae.getText().toString()+mAddressDetails.getText().toString());
        mPresenter.addressAdd(map);
    }

    // 添加地址
    private void  addressUpdate(){
        Map<String, String> map = new HashMap<>();
        map.put("id",""+getIntent().getExtras().getString("id"));
        map.put("address",""+mAddressDetails.getText().toString());
        map.put("city",""+mSelectCity.getText().toString());
        map.put("cityId",""+CityId);
        map.put("district",""+mSelectArae.getText().toString());
        map.put("districtId",""+AraeId);
        map.put("mobile",""+peoplePhone.getText().toString());
        map.put("name",""+peopleName.getText().toString());
        map.put("province",""+slectPoi.getText().toString());
        map.put("provinceId",""+PoiId);
        map.put("region",""+slectPoi.getText().toString()+mSelectCity.getText().toString()+mSelectArae.getText().toString()+mAddressDetails.getText().toString());
        map.put("defaultAddress", isDefaultAddress+"");

        mPresenter.addressUpdate(map);
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


    String PoiId = "";
    int PoiIndex = 0;
    String CityId = "";
    int CityIndex = 0;
    String AraeId = "";
    int AraeIndex = 0;
    List<AddressGetBean> addressGetCity = new ArrayList<>();
    List<AddressGetBean> addressGetArae = new ArrayList<>();

    JSONArray jsonArray2;

    private void pvCustomOptions(int type) {
        pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                if (type == 0) {
                    PoiId = addressGetBeans.get(options1).getId();
                    slectPoi.setText(addressGetBeans.get(options1).getName());
                    PoiIndex = options1;
                    try {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(PoiIndex);
                        jsonArray2 = jsonObject1.getJSONArray("children");
                        arrayListCity.clear();
                        addressGetCity.clear();
                        for (int i=0;i<jsonArray2.length();i++) {
                            AddressGetBean addressGetBean = new AddressGetBean();
                            addressGetBean.setId(jsonArray2.getJSONObject(i).get("id")+"");
                            addressGetBean.setName(jsonArray2.getJSONObject(i).get("name")+"");
                            addressGetCity.add(addressGetBean);
                            arrayListCity.add(jsonArray2.getJSONObject(i).get("name")+"");
                            Log.e("xxx111",addressGetCity.size()+"");
                         }
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (type == 1) {
                    CityId = addressGetCity.get(options1).getId();
                    mSelectCity.setText(addressGetCity.get(options1).getName());
                    CityIndex = options1;

                    try {
                        JSONObject jsonObject1 = jsonArray2.getJSONObject(CityIndex);
                        JSONArray jsonArray = jsonObject1.getJSONArray("children");
                        arrayListArae.clear();
                        addressGetArae.clear();
                        for (int i=0;i<jsonArray.length();i++) {
                            AddressGetBean addressGetBean = new AddressGetBean();
                            addressGetBean.setId(jsonArray.getJSONObject(i).get("id")+"");
                            addressGetBean.setName(jsonArray.getJSONObject(i).get("name")+"");
                            addressGetArae.add(addressGetBean);
                            arrayListArae.add(jsonArray.getJSONObject(i).get("name")+"");
                            Log.e("xxx111",addressGetArae.size()+"");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    AraeId = addressGetArae.get(options1).getId();
                    mSelectArae.setText(addressGetArae.get(options1).getName());
                    AraeIndex = options1;
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

        pvCustomOptions.setPicker(inSelect);//添加数据
        pvCustomOptions.show();
    }

//    下面是网络接口的回调
    @Override
    public void AddressListSuc(List<AddressListBean> DataBean) {

    }

    private boolean isDefaultAddress;

    @Override
    public void AddressCurrentSuc(AddressListBean DataBean) {

        isDefaultAddress=DataBean.isDefaultAddress();
        mAddressDetails.setText(StringUtils.isNull(DataBean.getAddress()));
        mSelectCity.setText(StringUtils.isNull(DataBean.getCity()));
        mSelectArae.setText(StringUtils.isNull(DataBean.getDistrict()));
        peoplePhone.setText(StringUtils.isNull(DataBean.getMobile()));
        peopleName.setText(StringUtils.isNull(DataBean.getName()));
        slectPoi.setText(StringUtils.isNull(DataBean.getProvince()));
        CityId = StringUtils.isNull(DataBean.getCityId());
        AraeId = StringUtils.isNull(DataBean.getDistrictId());
        PoiId = StringUtils.isNull(DataBean.getProvinceId());
    }

    @Override
    public void AddressAddSuc(AddressListBean DataBean) {
        ToastUitl.showShort(this,"添加成功!");
        this.finish();
    }

    @Override
    public void AddressUpdateSuc(AddressListBean DataBean) {
        ToastUitl.showShort(this,"修改成功!");
        this.finish();
    }

    @Override
    public void AddressDeleteSuc() {

    }

    @Override
    public void GetHomePageFail(String Message) {
       ToastUitl.showShort(this,Message+"");
    }

    @Override
    public void updateDefAddress() {

    }

    @Override
    public void updateDefAddressError(String msg) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }
}
