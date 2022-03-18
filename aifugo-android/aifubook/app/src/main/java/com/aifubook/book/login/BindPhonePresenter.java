package com.aifubook.book.login;

import com.aifubook.book.bean.LoginHomePageBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class BindPhonePresenter extends BasePresenter<BindPhoneView, BindPhoneModel> {

    BindPhonePresenter(BindPhoneView view) {
        setVM(view, new BindPhoneModel());
    }

    /**
     * 获得评测列表
     *
     * @param requestData 参数
     */
    void sendSmsCode(Map<String, String> requestData) {
        mRxManage.add(
                mModel.sendSmsCode(requestData, new RxSubscriber<Integer>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(Integer DataBean) {
                        mView.stopLoading();
                        mView.GetverificationCodeSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetverificationCodeFail(message);
                    }
                }));
    }

    /**
     * 获得评测列表
     *
     * @param requestData 参数
     */
    void checkCode(Map<String, String> requestData) {
        mRxManage.add(
                mModel.checkCode(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.GetHomePageSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 获得评测列表
     *
     * @param requestData 参数
     */
    void updateMemberEmail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.updateMemberEmail(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.updateMemberEmail(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 手机号注册
     *
     * @param requestData 参数
     */
    void setPayPassword(Map<String, String> requestData) {
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
                        mView.UserRegisterSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 手机号注册
     *
     * @param requestData 参数
     */
    void updateMobile(Map<String, String> requestData) {
        mRxManage.add(
                mModel.updateMobile(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.updateMobile(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 手机号注册
     *
     * @param requestData 参数
     */
    void bindMobile(Map<String, String> requestData) {
        mRxManage.add(
                mModel.bindMobile(requestData, new RxSubscriber<LoginHomePageBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(LoginHomePageBean DataBean) {
                        mView.stopLoading();
                        mView.bindMobile(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }



    /**
     * 发送注册验证码
     */
    void sendRegCode(Map<String, String> requestData) {
        mRxManage.add(
                mModel.sendRegCode(requestData, new RxSubscriber<Integer>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(Integer DataBean) {
                        mView.stopLoading();
                        mView.GetverificationCodeSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetverificationCodeFail(message);
                    }
                }));
    }

    /**
     * 发送注册验证码
     */
    void sendSmsCodeTrue(Map<String, String> requestData) {
        mRxManage.add(
                mModel.sendSmsCodeTrue(requestData, new RxSubscriber<Integer>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(Integer DataBean) {
                        mView.stopLoading();
                        mView.GetverificationCodeSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetverificationCodeFail(message);
                    }
                }));
    }

//    /**
//     * 获得评测列表
//     *
//     * @param requestData 参数
//     */
//    void updateLoginPassword(Map<String, String> requestData) {
//        mRxManage.add(
//                mModel.updateLoginPassword(requestData, new RxSubscriber<String>(mContext) {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        mView.showLoading("正在加载,请稍后...");
//                    }
//
//                    @Override
//                    protected void _onNext(String DataBean) {
//                        mView.stopLoading();
//                        mView.GetHomePageSuc(DataBean);
//                    }
//
//                    @Override
//                    protected void _onError(String message) {
//                        mView.stopLoading();
//                        mView.GetHomePageFail(message);
//                    }
//                }));
//    }

}
