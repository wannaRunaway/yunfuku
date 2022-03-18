package com.aifubook.book.mine.address;

import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface AddressView extends BaseView {

    // 获取用户所有收货地址
    void AddressListSuc(List<AddressListBean> DataBean);

    // 获取默认的收货地址
    void AddressCurrentSuc(AddressListBean DataBean);

    // 添加收货地址
    void AddressAddSuc(AddressListBean DataBean);

    // 更新收货地址
    void AddressUpdateSuc(AddressListBean DataBean);

    // 删除收货地址
    void AddressDeleteSuc();


    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    void updateDefAddress();

    void updateDefAddressError(String msg);

}
