package com.aifubook.book.model;

public interface OnCallBack {

    void onNext(Object result, int type);

    void onError(String error,int type);
}
