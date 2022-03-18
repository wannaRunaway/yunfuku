package com.aifubook.book.activity.teacherfreeproductsdetails;

import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.jiarui.base.bases.BaseView;

public interface TeacherFreeBookView extends BaseView {
    // 获取基本数据成功
    void GetDataSuc(ProductDetailBean DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

    // 获取基本数据成功
    void GetShopSuc(ShopBean DataBean);

    // 获取基本数据失败
    void GetShopFail(String Message);

    // 获取基本数据成功
    void SendSuc(ProductListBean DataBean);

    // 获取基本数据失败
    void SendSucFail(String Message);

    // 获取基本数据成功
    void CardAddSuc(String DataBean);

    // 获取基本数据成功
    void StringRol(String DataBean);


    // 获取基本数据失败
    void CardAddFail(String Message);

    void getWeChatToken(String weChatToken);

    //获取当前订单
    void getCreateOrder(CreateOrderBean createOrderBean);
    //获取当前地址
    void getDefaultAddressBean(AddressListBean addressListBean);
}
