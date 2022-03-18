package com.aifubook.book.bean;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.model.GrouponDataModel;
import com.aifubook.book.model.OnCallBack;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.Map;

public class GrouponModel extends BaseModel {

    private OnCallBack callBack;
    private GrouponDataModel model;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public GrouponModel(GrouponDataModel model) {
        this.model = model;
    }

    public void getGrouponList(Map<String, String> requestData,int type) {

        model.getGrouponList(requestData, new RxSubscriber<GrouponPageBean>() {
            @Override
            protected void _onNext(GrouponPageBean grouponPageBean) {
                callBack.onNext(grouponPageBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });

    }


}
