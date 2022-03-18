package com.aifubook.book.productcar.trueorder;

import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class PayOrderPresenter extends BasePresenter<PayOrderView, PayOrderModel> {

    public PayOrderPresenter(PayOrderView view) {
        setVM(view, new PayOrderModel());
    }

    /**
     * 获取用户基本信息
     */
    public void memberInfo(Map<String, String> requestData) {
        mRxManage.add(
                mModel.memberInfo(requestData, new RxSubscriber<MemberInfoBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(MemberInfoBean DataBean) {
                        mView.stopLoading();
                        mView.MemberInfoSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 拿到createOrder 返回的支付订单支付
     */
    public void orderToPay(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderToPay(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.OrderToPaySuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 拿到createOrder 返回的支付订单支付
     */
    public void orderToPayWeChat(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderToPayWeChat(requestData, new RxSubscriber<SendRechargeBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(SendRechargeBean DataBean) {
                        mView.stopLoading();
                        mView.orderToPayWeChat(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 修改用户基本信息
     */
    public void updateMemberInfo(Map<String, String> requestData) {
        mRxManage.add(
                mModel.updateMemberInfo(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.UpdateMemberInfoSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 获取可用余额
     */
    public void getCanUsedBalance(Map<String, String> requestData) {
        mRxManage.add(
                mModel.getCanUsedBalance(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.GetCanUsedBalanceSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 获取用户账户信息
     */
    public void getAccountInfo(Map<String, String> requestData) {
        mRxManage.add(
                mModel.getAccountInfo(requestData, new RxSubscriber<GetAccountInfoBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(GetAccountInfoBean DataBean) {
                        mView.stopLoading();
                        mView.GetAccountInfoSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 判断用户当前有没有设置支付密码
     */
    public void hasPayPassword(Map<String, String> requestData) {
        mRxManage.add(
                mModel.hasPayPassword(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.HasPayPasswordSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 设置支付密码
     */
    public void setPayPassword(Map<String, String> requestData) {
        mRxManage.add(
                mModel.setPayPassword(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.SetPayPasswordSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }
}
