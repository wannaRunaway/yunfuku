package com.aifubook.book.shop;

import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.ShopHomeBean;
import com.jiarui.base.bases.BaseView;

public interface ShopHomeView extends BaseView {


     void showShopData(ShopHomeBean bean);
     void getDataError(String msg);

     void showTopData(ShopBean bean);
     void showTopDataError(String msg);

    void CardAddSuc(String dataBean);

    void CardAddFail(String message);
}
