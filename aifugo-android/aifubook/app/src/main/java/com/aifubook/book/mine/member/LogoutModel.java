package com.aifubook.book.mine.member;

import com.aifubook.book.api.Api;
import com.aifubook.book.bean.LogoutBean;
import com.jiarui.base.baserx.RxHelper;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BaseModel;

import org.json.JSONObject;

import rx.Subscription;

public class LogoutModel implements BaseModel {


    public Subscription logout(RxSubscriber<String> mRxSubscriber) {
        return Api.getInstance()
                .service
                .logout()
                .compose(RxHelper.<String>handleResult())
                .subscribe(mRxSubscriber);
    }
}
