package com.aifubook.book.activity.TeacherFreeBooksList;

import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.jiarui.base.bases.BaseView;

public interface CheckDetailView extends BaseView {

    // 获取基本数据成功
    void GetShopSuc(ShopBean DataBean);

    // 获取基本数据失败
    void GetShopFail(String Message);

    // 获取基本数据成功
    void GetListDataSuc(ProductListBean DataBean);

    // 获取基本数据失败
    void GetListDataFail(String Message);
}
