package com.aifubook.book.model;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.mine.setting.PaySettingModel;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.Map;

public class ModifyModel extends BaseModel {

    private PaySettingModel mModel;

    private OnCallBack callBack;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public ModifyModel(PaySettingModel mModel) {
        this.mModel = mModel;
    }


    public void checkCode(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.checkCode(requestData, new RxSubscriber<String>() {

                    @Override
                    protected void _onNext(String DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    public void sendMobileCode(Map<String, String> requestData, int type){
        mRxManage.add(mModel.sendMobileCode(requestData, new RxSubscriber<Integer>() {
            @Override
            protected void _onNext(Integer integer) {
                callBack.onNext(integer,type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message,type);
            }
        }));
    }


}
