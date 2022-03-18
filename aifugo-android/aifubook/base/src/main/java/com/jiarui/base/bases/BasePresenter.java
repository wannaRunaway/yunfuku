package com.jiarui.base.bases;


import android.content.Context;

import com.jiarui.base.baserx.RxManager;

public abstract class BasePresenter<V, M> {
    public Context mContext;
    protected M mModel;
    public V mView;
    protected RxManager mRxManage = new RxManager();

    protected void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {
    }


    public void onDestroy() {
        mRxManage.clear();
        this.mModel = null;
        this.mView = null;
    }


}
