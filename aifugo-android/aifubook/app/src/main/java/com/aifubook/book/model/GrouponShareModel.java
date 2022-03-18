package com.aifubook.book.model;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.bean.GrouponPageBean;
import com.aifubook.book.bean.GrouponShareBean;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.Map;

public class GrouponShareModel extends BaseModel {

    private OnCallBack callBack;
    private GrouponDataModel model;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public GrouponShareModel(GrouponDataModel model) {
        this.model = model;
    }

    public void getGroupnOrderDetail(Map<String, String> requestData, int type) {

        model.getGroupnOrderDetail(requestData, new RxSubscriber<GrouponShareBean>() {
            @Override
            protected void _onNext(GrouponShareBean grouponPageBean) {
                callBack.onNext(grouponPageBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });

    }

}
