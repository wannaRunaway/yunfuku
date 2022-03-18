package com.aifubook.book.model;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.bean.HomeModel;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.type.TypeModel;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.Map;

public class TeacherAreaModel extends BaseModel {

    private TypeModel mModel;

    private OnCallBack callBack;

    public TeacherAreaModel(TypeModel mModel) {
        this.mModel = mModel;
    }

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }


    public void getProductList(Map<String, Object> requestData, int type){

        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>() {

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message,type);
                    }
                }));
    }
    public void addCart(Map<String, String> requestData, int type) {
        mRxManage.add(mModel.carAdd(requestData, new RxSubscriber<String>() {
            @Override
            protected void _onNext(String s) {
                callBack.onNext(s, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message,type);
            }
        }));
    }


}
