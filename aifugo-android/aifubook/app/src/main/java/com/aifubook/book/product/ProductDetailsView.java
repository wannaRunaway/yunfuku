package com.aifubook.book.product;

import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.WeChatToken;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface ProductDetailsView extends BaseView {

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

}
