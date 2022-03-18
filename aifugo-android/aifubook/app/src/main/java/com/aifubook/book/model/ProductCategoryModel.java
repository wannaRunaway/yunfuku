package com.aifubook.book.model;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.type.TypeModel;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.List;
import java.util.Map;

public class ProductCategoryModel extends BaseModel {

    private TypeModel mModel;

    private OnCallBack callBack;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public ProductCategoryModel(TypeModel model) {
        this.mModel = model;
    }

    public void categoryAll(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.categoryAll(requestData, new RxSubscriber<List<TypeBean>>() {

                    @Override
                    protected void _onNext(List<TypeBean> DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    public void getByScene(Map<String, Object> requestData, int type) {
        mRxManage.add(
                mModel.getByScene(requestData, new RxSubscriber<SceneBean>() {

                    @Override
                    protected void _onNext(SceneBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }


}
