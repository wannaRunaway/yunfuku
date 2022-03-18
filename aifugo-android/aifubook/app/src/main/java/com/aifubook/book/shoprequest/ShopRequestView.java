package com.aifubook.book.shoprequest;

import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface ShopRequestView extends BaseView {

    void GetSendImageSuc(String notDataBean);

    void GetSendImageFail(String Message);

    // 获取基本数据成功
    void GetDataSuc(ProductDetailBean DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

    // 获取基本数据成功
    void GetShopSuc(String DataBean);

    // 获取基本数据失败
    void GetShopFail(String Message);

    // 获取基本数据成功
    void GetListDataSuc(ProductListBean DataBean);

    // 获取基本数据失败
    void GetListDataFail(String Message);


}
