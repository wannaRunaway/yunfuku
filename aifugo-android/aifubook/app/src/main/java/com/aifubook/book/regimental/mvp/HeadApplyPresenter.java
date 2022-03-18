package com.aifubook.book.regimental.mvp;

import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.ChiefMembersBean;
import com.aifubook.book.regimental.ChiefMyChiefBean;
import com.aifubook.book.regimental.ChiefOrderByCodeBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.regimental.RechargeBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class HeadApplyPresenter extends BasePresenter<HeadApplyView, HeadApplyModel> {

    public HeadApplyPresenter(HeadApplyView view) {
        setVM(view, new HeadApplyModel());
    }

    /**
     * 团长申请 手机号必填
     *
     * @param requestData 参数
     */
   public void chiefApply(Map<String, String> requestData) {
        mRxManage.add(
                mModel.chiefApply(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.ChiefApplySuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productList(Map<String, Object> requestData) {
        mRxManage.add(
                mModel.productList(requestData, new RxSubscriber<ProductListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ProductListBean DataBean) {
                        mView.stopLoading();
                        mView.GetDataSucs(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }

    /**
     * 上传图片
     *
     * @param requestData 参数
     */
    public void uploadImage(Map<String, String> requestData,List<File> filelist) {
        mRxManage.add(
                mModel.uploadImage(requestData,filelist, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.UploadImageSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 团长详情信息
     *
     * @param requestData 参数
     */
    public void chiefDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.chiefDetail(requestData, new RxSubscriber<ChiefDetailsBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ChiefDetailsBean DataBean) {
                        mView.stopLoading();
                        mView.ChiefDetailSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 团长介绍的用户的分页查询
     *
     * @param requestData 参数
     */
   public void chiefMembers(Map<String, String> requestData) {
        mRxManage.add(
                mModel.chiefMembers(requestData, new RxSubscriber<ChiefMembersBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ChiefMembersBean DataBean) {
                        mView.stopLoading();
                        mView.ChiefMembersSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 获取用户的团长
     *
     * @param requestData 参数
     */
    void chiefMyChief(Map<String, String> requestData) {
        mRxManage.add(
                mModel.chiefMyChief(requestData, new RxSubscriber<ChiefMyChiefBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ChiefMyChiefBean DataBean) {
                        mView.stopLoading();
                        mView.ChiefMyChiefSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

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
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 获得评测列表
     *
     * @param requestData 参数
     */
    public void sendSmsCode(Map<String, String> requestData) {
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
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 团长根据核销码查询订单详情
     *
     * @param requestData 参数
     */
    public void getChiefOrderByCode(Map<String, String> requestData) {
        mRxManage.add(
                mModel.getChiefOrderByCode(requestData, new RxSubscriber<ChiefOrderByCodeBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ChiefOrderByCodeBean DataBean) {
                        mView.stopLoading();
                        mView.GetChiefOrderByCodeSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 订单团长提货核销 团长使用
     *
     * @param requestData 参数
     */
    public void setFetched(Map<String, String> requestData) {
        mRxManage.add(
                mModel.setFetched(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.SetFetchedSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 小程序端订单统一查询接口
     * @param requestData
     */
    public void orderList(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderList(requestData, new RxSubscriber<OrderListBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(OrderListBean DataBean) {
                        mView.stopLoading();
                        mView.OrderListSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 获取佣金明细
     * @param requestData
     */
    public void recordList(Map<String, String> requestData) {
        mRxManage.add(
                mModel.recordList(requestData, new RxSubscriber<CommissionDetailsBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(CommissionDetailsBean DataBean) {
                        mView.stopLoading();
                        mView.RecordListSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 申请提现
     *
     * @param requestData 参数
     */
    public void commissionApply(Map<String, String> requestData) {
        mRxManage.add(
                mModel.commissionApply(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.CommissionApplySuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 创建充值订单
     *
     * @param requestData 参数
     */
    public void orderCreate(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderCreate(requestData, new RxSubscriber<RechargeBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(RechargeBean DataBean) {
                        mView.stopLoading();
                        mView.OrderCreateSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }
}
