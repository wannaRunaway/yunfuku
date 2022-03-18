package com.aifubook.book.mine.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aifubook.book.R;
import com.aifubook.book.activity.main.BaseActivity;
import com.aifubook.book.base.BaseRecyclerAdapter;
import com.aifubook.book.base.BaseRecyclerHolder;
import com.aifubook.book.dialog.AffirmMessageDialog;
import com.jiarui.base.utils.ToastUitl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ListKer_Hlg on 2021/4/26 1:23
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class AddressListActivity extends BaseActivity<AddressPresenter> implements AddressView {

    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    BaseRecyclerAdapter<AddressListBean> typeBeanBaseRecyclerAdapter;
    List<AddressListBean> addressListBeans = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initPresenter() {
        mPresenter = new AddressPresenter(this);
    }

    // 获取地址列表
    private void addressList() {
        startProgressDialog();
        Map<String, String> map = new HashMap<>();
        mPresenter.addressList(map);
    }

    @Override
    public void initView() {
        setTitle("管理地址");
        initRighRecycler();
//        addressList();
    }

    @OnClick({R.id.addNewAddress})
    void Onclicks(View view) {
        switch (view.getId()) {
            case R.id.addNewAddress:
                Bundle bundle = new Bundle();
                bundle.putString("typeSelect", "1");
                startActivity(CreatAddress.class, bundle);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressList();
    }

    //    /***************************************************************************************热门景点*********************************************************************************/
//

    // 删除地址
    private void addressDelete(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "" + id);
        mPresenter.addressDelete(map);
    }


    private void initRighRecycler() {
        typeBeanBaseRecyclerAdapter = new BaseRecyclerAdapter<AddressListBean>(this, addressListBeans, R.layout.address_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, AddressListBean item, int position, boolean isScrolling) {
                CheckBox checkBox = holder.getView(R.id.mCheckBox);
                holder.setText(R.id.mName, "" + item.getName());
                holder.setText(R.id.mPhone, "" + item.getMobile());
                holder.setText(R.id.mAddress, "" + item.getProvince() + "" + item.getCity() + "" + item.getDistrict() + "" + item.getAddress());
                boolean defaultAddress = item.isDefaultAddress();
                checkBox.setChecked(defaultAddress);
                checkBox.setEnabled(!checkBox.isChecked());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Map map = new HashMap();
                            map.put("id", item.getId());
                            mPresenter.updateDefaultAddress(map);
                        }
                    }
                });


                holder.getView(R.id.mDetele).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AffirmMessageDialog affirmMessageDialog = new AffirmMessageDialog(AddressListActivity.this);
                        affirmMessageDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.getId() == R.id.dialog_affirm_btn) {
                                    addressDelete(item.getId());
                                    affirmMessageDialog.dismiss();
                                }
                            }
                        });
                        affirmMessageDialog.show("确认要删除该地址？");
                    }
                });


                holder.getView(R.id.mUpDate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("typeSelect", "2");
                        bundle.putString("id", "" + item.getId());
                        startActivity(CreatAddress.class, bundle);
                    }
                });

                holder.getView(R.id.outSelect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getIntent().getExtras().getString("typeSelect").equals("1")) {
                            Intent intent = new Intent();
                            intent.putExtra("addid", "" + item.getId());
                            intent.putExtra("name", "" + item.getName());
                            intent.putExtra("address", "" + item.getProvince() + item.getCity() + item.getDistrict() + item.getAddress());
                            intent.putExtra("phone", "" + item.getMobile());
                            setResult(RESULT_OK, intent);
                            AddressListActivity.this.finish();
                        } else {

                        }
                    }
                });

            }
        };
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(typeBeanBaseRecyclerAdapter);
    }

    @Override
    public void AddressListSuc(List<AddressListBean> DataBean) {
        stopProgressDialog();
        addressListBeans.clear();
        addressListBeans.addAll(DataBean);
        typeBeanBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void AddressCurrentSuc(AddressListBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void AddressAddSuc(AddressListBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void AddressUpdateSuc(AddressListBean DataBean) {
        stopProgressDialog();
    }

    @Override
    public void AddressDeleteSuc() {
        stopProgressDialog();
        addressList();
    }

    @Override
    public void GetHomePageFail(String Message) {
        stopProgressDialog();
        ToastUitl.showShort(this, Message + "!");
    }

    @Override
    public void updateDefAddress() {
        showToast("修改成功");
        addressList();
    }

    @Override
    public void updateDefAddressError(String msg) {
        stopProgressDialog();
        showToast("修改失败");
    }

    @Override
    public void showLoading(String title) {
//        startProgressDialog();
    }

    @Override
    public void stopLoading() {
//        stopProgressDialog();
    }
}
