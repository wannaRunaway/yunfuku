package com.aifubook.book.shop;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.ShopHomeBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import java.util.Map;

import rx.Subscription;

public class ShopHomeModel implements BaseModel {



    Subscription getShopDetail(Map<String, String> requestData, RxSubscriber<ShopHomeBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getHotBooks(requestData)
                .compose(RxHelper.<ShopHomeBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    Subscription getShopTopDetail(Map<String, String> requestData, RxSubscriber<ShopBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .shopDetail(requestData)
                .compose(RxHelper.<ShopBean>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    Subscription carAdd(Map<String, String> requestData, RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .carAdd(requestData)
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }




}
