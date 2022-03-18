package com.aifubook.book.mine.member;

import com.aifubook.book.api.ApiService;
import com.aifubook.book.bean.LogoutBean;
import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.utils.OkHttpUtils;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class MemberInfoPresenter extends BasePresenter<MemberInfoView, MemberInfoModel> {

    public MemberInfoPresenter(MemberInfoView view) {
        setVM(view, new MemberInfoModel());
    }


    public void getWechatAccessToken() {

        String url = ApiService.BASE_HOST+"config/wechatAccessToken";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    String access_token = obj.optString("result");
                    if(mView!=null) {
                        mView.getWeChatToken(access_token);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        OkHttpUtils.get(url, resultCallback);
    }


    public void getOrderCount() {
        mRxManage.add(mModel.getOrderCount(new RxSubscriber<OrderCountVO>(mContext) {
            @Override
            protected void _onNext(OrderCountVO orderCountVO) {
                mView.getOrderCountResult(orderCountVO);
            }

            @Override
            protected void _onError(String message) {
                mView.getOrderCountError(message);
            }
        }));
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
     * 获得评测列表
     *
     * @param requestData 参数
     */
    public void getNextImageSend(Map<String, String> requestData, List<File> files) {
        mRxManage.add(
                mModel.requestGetTip(requestData, files, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String notDataBean) {
                        mView.stopLoading();
                        mView.GetSendImageSuc(notDataBean);
                    }

                    @Override
                    protected void _onError(String EssentialInformation) {
                        mView.stopLoading();
                        mView.GetSendImageFail(EssentialInformation);
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
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.UpdateMemberInfoSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 修改用户基本信息
     */
    public void appliedShop(Map<String, String> requestData) {
        mRxManage.add(
                mModel.appliedShop(requestData, new RxSubscriber<ShopBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ShopBean DataBean) {
                        mView.stopLoading();
                        mView.appliedShop(DataBean);
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
    public void shopDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.shopDetail(requestData, new RxSubscriber<ShopBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ShopBean DataBean) {
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
     * 获取佣金明细
     *
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
     * 获得基本数据
     *
     * @param requestData 参数
     */
    public void productList(Map<String, String> requestData) {
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
                        mView.GetDataSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetDataFail(message);
                    }
                }));
    }
}
