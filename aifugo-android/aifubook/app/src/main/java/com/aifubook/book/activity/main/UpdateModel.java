package com.aifubook.book.activity.main;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.UpdateBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;


import rx.Subscription;

public class UpdateModel implements BaseModel {

    Subscription updateApp(RxSubscriber<UpdateBean> mRxSubscriber) {
        return Api.getInstance()
                .service
                .updateApp()
                .compose(RxHelper.<UpdateBean>handleResult())
                .subscribe(mRxSubscriber);
    }


}
