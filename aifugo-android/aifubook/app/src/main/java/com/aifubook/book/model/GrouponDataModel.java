package com.aifubook.book.model;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.GrouponPageBean;
import com.aifubook.book.bean.GrouponShareBean;
import com.aifubook.book.bean.HomeCategoryBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import java.util.List;
import java.util.Map;

import rx.Subscription;

public class GrouponDataModel implements BaseModel {

    public Subscription getGrouponList(Map<String, String> requestData, RxSubscriber<GrouponPageBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getGrouponList(requestData)
                .compose(RxHelper.<GrouponPageBean>handleResult())
                .subscribe(mRxSubscriber);
    }


    public Subscription getGroupnOrderDetail(Map<String, String> requestData, RxSubscriber<GrouponShareBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getGroupnOrderDetail(requestData)
                .compose(RxHelper.<GrouponShareBean>handleResult())
                .subscribe(mRxSubscriber);
    }


}
