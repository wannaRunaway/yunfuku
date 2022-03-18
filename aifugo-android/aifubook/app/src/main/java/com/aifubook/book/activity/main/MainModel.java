package com.aifubook.book.activity.main;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.UpdateBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import rx.Subscription;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class MainModel implements BaseModel {

    Subscription updateApp(RxSubscriber<UpdateBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .updateApp()
                .compose(RxHelper.<UpdateBean>handleResult())
                .subscribe(mRxSubscriber);
    }

}
