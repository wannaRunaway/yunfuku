package com.aifubook.book.home.product;

import com.aifubook.book.bean.ProductListBean;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface ProductListView extends BaseView {

    // 获取基本数据成功
    void GetDataSuc(ProductListBean DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

}
