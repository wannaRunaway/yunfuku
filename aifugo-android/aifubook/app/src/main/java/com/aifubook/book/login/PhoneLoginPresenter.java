package com.aifubook.book.login;

import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.bean.SchoolBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class PhoneLoginPresenter extends BasePresenter<PhoneLoginView, PhoneModel> {

    public PhoneLoginPresenter(PhoneLoginView view) {
        setVM(view, new PhoneModel());
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
    void loginHttp(Map<String, String> requestData) {
        mRxManage.add(
                mModel.loginHttp(requestData, new RxSubscriber<LoginHomePageBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(LoginHomePageBean DataBean) {
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


    void registerChiefApply(Map<String, String> requestData) {
        mRxManage.add(mModel.registerApply(requestData, new RxSubscriber<String>(mContext) {
            @Override
            protected void _onNext(String s) {
                mView.registerChief();
            }

            @Override
            protected void _onError(String message) {
                mView.registerChiefError(message);
            }
        }));

    }

    /**
     * 手机号注册
     *
     * @param requestData 参数
     */
    void userRegister(Map<String, String> requestData) {
        mRxManage.add(
                mModel.userRegister(requestData, new RxSubscriber<LoginHomePageBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(LoginHomePageBean DataBean) {
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

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void findByCityId(Map<String, String> requestData) {
        mRxManage.add(
                mModel.findByCityId(requestData, new RxSubscriber<List<SchoolBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<SchoolBean> DataBean) {
                        mView.stopLoading();
                        mView.GetDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void childList(Map<String, String> requestData) {
        mRxManage.add(
                mModel.childList(requestData, new RxSubscriber<List<ClassBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<ClassBean> DataBean) {
                        mView.stopLoading();
                        mView.GetShopSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetShopFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void findSchoolClasses(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.findSchoolClasses(requestData, new RxSubscriber<List<FindSchoolClassesBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<FindSchoolClassesBean> DataBean) {
                        mView.stopLoading();
                        mView.GetListDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetListDataFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void childaddChild(Map<String, String> requestData) {
        mRxManage.add(
                mModel.childaddChild(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.AddDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetListDataFail(message);
                    }
                }));
    }

}
