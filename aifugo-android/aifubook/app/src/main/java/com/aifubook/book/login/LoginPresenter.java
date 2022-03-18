package com.aifubook.book.login;

import com.aifubook.book.bean.LoginHomePageBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.Map;

public class LoginPresenter extends BasePresenter<LoginView, PhoneModel> {


    public LoginPresenter(LoginView view) {
        setVM(view, new PhoneModel());
    }


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


    public void loginNew(Map<String, String> requestData) {
        mRxManage.add(
                mModel.loginNew(requestData, new RxSubscriber<LoginHomePageBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(LoginHomePageBean dataBean) {
                        mView.loginSuccess(dataBean);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.loginFail(message);
                    }
                }));
    }

    public void weChatLoginForApp(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.weChatLoginForApp(requestData, new RxSubscriber<LoginHomePageBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(LoginHomePageBean DataBean) {
                        mView.loginSuccess(DataBean);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.weChatLoginForFail(message);
                    }
                }));
    }


}
