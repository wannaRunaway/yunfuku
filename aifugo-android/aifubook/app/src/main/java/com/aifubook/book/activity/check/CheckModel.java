package com.aifubook.book.activity.check;


import com.aifubook.book.api.Api;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.TypeBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import java.util.List;
import java.util.Map;

import rx.Subscription;

public class CheckModel implements BaseModel {
    /**
     * 获得评测列表
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription categoryAll(Map<String, String> requestData, RxSubscriber<List<TypeBean>> mRxSubscriber) {
        return Api.getInstance()
                .service
                .categoryAll(requestData)
                .compose(RxHelper.<List<TypeBean>>handleResult())
                .subscribe(mRxSubscriber);
    }

    /**
     * 首页轮播图
     *
     * @param requestData   报文
     * @param mRxSubscriber 订阅
     * @return 获得到订阅者
     */
    public Subscription getByScene(Map<String, Object> requestData, RxSubscriber<SceneBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .getByScene(requestData)
                .compose(RxHelper.<SceneBean>handleResult())
                .subscribe(mRxSubscriber);
    }
}
