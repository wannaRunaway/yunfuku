package com.aifubook.book.bean;

import com.aifubook.book.base.BaseModel;
import com.aifubook.book.base.CouponBeans;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.productcar.TrueOrderModel;
import com.jiarui.base.baserx.RxSubscriber;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class TrueOrderNModel extends BaseModel {


    private TrueOrderModel mModel;

    private TrueOrderNModel.OnCallBack callBack;

    public void setOnCallBackListener(TrueOrderNModel.OnCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnCallBack {
        void onNext(Object result, int type);

        void onError(String error, int type);
    }

    public TrueOrderNModel(TrueOrderModel model) {
        this.mModel = model;
    }

    public void orderToPayWeChat(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.orderToPayWeChat(requestData, new RxSubscriber<SendRechargeBean>() {

                    @Override
                    protected void _onNext(SendRechargeBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }


    public void createOrder(RequestBody requestData, int type) {
        mRxManage.add(
                mModel.createOrder(requestData, new RxSubscriber<CreateOrderBean>() {

                    @Override
                    protected void _onNext(CreateOrderBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }

    //获取未使用的优惠券
    public void memberUnUsedCoupons(Map<String, String> requestData, int type) {

        mRxManage.add(
                mModel.memberUnUsedCoupons(requestData, new RxSubscriber<>() {

                    @Override
                    protected void _onNext(List<CouponBeans> DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }


    public void getSameCityInfo(Map<String, String> requestData, int type) {

        mRxManage.add(mModel.getSameCityFeeForApp(requestData, new RxSubscriber<SameBean>() {
            @Override
            protected void _onNext(SameBean sameBean) {
                callBack.onNext(sameBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        }));

    }


    public void myChief(Map<String, String> requestData, int type) {
        mRxManage.add(mModel.myChief(requestData, new RxSubscriber<>() {
            @Override
            protected void _onNext(MyChileBean myChileBean) {
                callBack.onNext(myChileBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        }));
    }

    public void getDefaultAddress(int type) {

        mRxManage.add(mModel.getDefaultAddress(new RxSubscriber<AddressListBean>() {
            @Override
            protected void _onNext(AddressListBean addressListBean) {
                callBack.onNext(addressListBean, type);
            }

            @Override
            protected void _onError(String message) {
                callBack.onError(message, type);
            }
        }));

    }

    public void shopDetail(Map<String, String> requestData, int type) {
        mRxManage.add(
                mModel.shopDetail(requestData, new RxSubscriber<>() {

                    @Override
                    protected void _onNext(ShopBean DataBean) {
                        callBack.onNext(DataBean, type);
                    }

                    @Override
                    protected void _onError(String message) {
                        callBack.onError(message, type);
                    }
                }));
    }


}
