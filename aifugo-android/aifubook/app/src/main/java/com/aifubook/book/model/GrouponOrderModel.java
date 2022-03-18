package com.aifubook.book.model;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.OrderModel;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.Map;

public class GrouponOrderModel extends BaseModel {

    private OnCallBack callBack;
    private OrderModel model;

    public void setOnCallBackListener(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public GrouponOrderModel(OrderModel model) {
        this.model = model;
    }


    public void getOrderList(Map<String, String> requestData, int type) {

        model.orderList(requestData, new RxSubscriber<OrderListBean>() {
            @Override
            protected void _onNext(OrderListBean orderListBean) {
                callBack.onNext(orderListBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });

    }

    public void orderDelete(Map<String, String> requestData, int type) {
        model.orderDelete(requestData, new RxSubscriber<String>() {

            @Override
            protected void _onNext(String DataBean) {
                callBack.onNext(DataBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });
    }

    public void uploadRefundNo(Map<String, String> requestData, int type) {
        model.uploadRefundNo(requestData, new RxSubscriber<String>() {
            @Override
            protected void _onNext(String s) {
                callBack.onNext(s, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });
    }

    public void orderSetCancle(Map<String, String> requestData, int type) {
        model.orderSetCancle(requestData, new RxSubscriber<String>() {

            @Override
            protected void _onNext(String DataBean) {
                callBack.onNext(DataBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });
    }

    public void setFetched(Map<String, String> requestData, int type) {
        model.setFetched(requestData, new RxSubscriber<String>() {

            @Override
            protected void _onNext(String DataBean) {
                callBack.onNext(DataBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });
    }

    public void orderWXRepay(Map<String, String> requestData, int type) {

        model.orderWXRepay(requestData, new RxSubscriber<SendRechargeBean>() {

            @Override
            protected void _onNext(SendRechargeBean DataBean) {
                callBack.onNext(DataBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });
    }

    public void orderCompleted(Map<String, String> requestData, int type) {
        model.orderCompleted(requestData, new RxSubscriber<String>() {

            @Override
            protected void _onNext(String DataBean) {
                callBack.onNext(DataBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        });
    }

    public void reBuy(Map<String, String> requestData,int type) {
                model.reBuy(requestData, new RxSubscriber<String>() {

                    @Override
                    protected void _onNext(String DataBean) {
                      callBack.onNext(DataBean,type);
                    }

                    @Override
                    protected void _onError(String message) {
                       callBack.onError(message,type);
                    }
                });
    }


}
