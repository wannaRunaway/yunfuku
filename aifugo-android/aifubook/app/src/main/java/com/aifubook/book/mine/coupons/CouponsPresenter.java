package com.aifubook.book.mine.coupons;

import android.util.Log;

import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class CouponsPresenter extends BasePresenter<CouponsView, CouponsModel> {

    public CouponsPresenter(CouponsView view) {
        setVM(view, new CouponsModel());
    }


    /**
     * 获取所有店铺可用优惠券
     */
    public void shopCoupons(Map<String, String> requestData) {
        mRxManage.add(
                mModel.shopCoupons(requestData, new RxSubscriber<List<MyCouponsBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<MyCouponsBean> DataBean) {
                        mView.stopLoading();
                        mView.ShopCouponsSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 获取所有店铺可用优惠券
     */
    public void fullSiteReduceCoupons(Map<String, String> requestData) {
        Log.e("htttp","ininin");
        mRxManage.add(
                mModel.fullSiteReduceCoupons(requestData, new RxSubscriber<List<MyCouponsBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<MyCouponsBean> DataBean) {
                        mView.stopLoading();
                        mView.ShopCouponsSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 用户领取优惠券
     */
    public void couponReceive(Map<String, String> requestData) {
        mRxManage.add(
                mModel.couponReceive(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.CouponReceiveSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 用户领取过的优惠券, 个人中心页面 优惠券
     */
    public void memberCoupons(Map<String, String> requestData) {
        mRxManage.add(
                mModel.memberCoupons(requestData, new RxSubscriber<MemberCouponsBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(MemberCouponsBean DataBean) {
                        mView.stopLoading();
                        mView.MemberCouponsSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

}
