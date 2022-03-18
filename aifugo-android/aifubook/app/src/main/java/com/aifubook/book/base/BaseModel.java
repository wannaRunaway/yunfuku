package com.aifubook.book.base;

import com.aifubook.book.shop.ShopCartActivity;
import com.jiarui.base.baserx.RxManager;

public abstract class BaseModel {


    protected RxManager mRxManage = new RxManager();


    public void onStart() {
    }


    public void onDestroy() {
        mRxManage.clear();

    }


}
