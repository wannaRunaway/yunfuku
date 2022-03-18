package com.aifubook.book.mine.order;

import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
import com.jiarui.base.baserx.RxSubscriber;
import com.jiarui.base.bases.BasePresenter;
import com.jiarui.base.utils.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public class OrderPresenter extends BasePresenter<OrderView, OrderModel> {

    public OrderPresenter(OrderView view) {
        setVM(view, new OrderModel());
    }


    public void uploadRefundNo(Map<String, String> requestData) {
        mRxManage.add(mModel.uploadRefundNo(requestData, new RxSubscriber<String>() {
            @Override
            protected void _onNext(String s) {
                mView.uploadRefundNoResult(s);
            }

            @Override
            protected void _onError(String message) {
                mView.uploadRefundNoError(message);
            }
        }));
    }


    /**
     * 小程序端订单统一查询接口
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
     * 查询运单号
    * */
    public void getmylogisticsMessage(Map<String,String> map){
        mRxManage.add(
                mModel.logisticsget(map, new RxSubscriber<>() {
                    @Override
                    protected void _onNext(LogisticsAllBean logisticsAllBean) {
                        mView.shunfenMessage(logisticsAllBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        LogUtil.d(message);
                    }
                })
        );
    }

    /**
     * 嗒哒查询运单号
     * */
    public void getDadamessage(Map<String,String> map){
        mRxManage.add(
                mModel.dadaget(map, new RxSubscriber<>() {
                    @Override
                    protected void _onNext(List<DadaResultBean> list) {
                        mView.dadamessage(list);
                    }

                    @Override
                    protected void _onError(String message) {
                    }
                })
        );
    }


    /**
     * 创建订单
     */
    public void createOrder(Map<String, String> requestData) {
        mRxManage.add(
                mModel.createOrder(requestData, new RxSubscriber<CreateOrderBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(CreateOrderBean DataBean) {
                        mView.stopLoading();
                        mView.CreateOrderSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 添加商品进购物车,或者购物车已有商品数量加减操作
     */
    public void carAdd(Map<String, String> requestData) {
        mRxManage.add(
                mModel.carAdd(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.CardAddSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.CardAddFail(message);
                    }
                }));
    }

    /**
     * 订单页面 再次购买
     *
     * @param requestData
     */
    public void reBuy(Map<String, String> requestData) {
        mRxManage.add(
                mModel.reBuy(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.CardAddSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.CardAddFail(message);
                    }
                }));
    }

    /**
     * 微信未支付的订单重新支付
     */
    public void orderWXRepay(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderWXRepay(requestData, new RxSubscriber<SendRechargeBean>(mContext) {
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
     * 订单详情 (物理订单 非支付订单)
     */
    public void orderDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderDetail(requestData, new RxSubscriber<>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(OrderDetailsBean DataBean) {
                        mView.stopLoading();
                        mView.OrderDetailSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 售后详情
     * */
    public void serviceDetail(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderService(requestData, new RxSubscriber<>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(ServiceDetailsBean DataBean) {
                        mView.stopLoading();
                        mView.servicedetails(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 撤销申请
     * */
    public void cancelfrefund(Map<String, String> requestData) {
        mRxManage.add(
                mModel.cancelrefund(requestData, new RxSubscriber<>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.cancelrefund(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 获取公司列表
     * */
    public void companylist(Map<String, String> requestData) {
        mRxManage.add(
                mModel.companylist(requestData, new RxSubscriber<>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<CompantItem> DataBean) {
                        mView.stopLoading();
                        mView.companylist(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 订单确认收货并完成
     */
    public void orderCompleted(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderCompleted(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.OrderCompletedSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 取消订单
     */
    public void orderSetCancle(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderSetCancle(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.OrderSetCancleSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 删除订单
     */
    public void orderDelete(Map<String, String> requestData) {
        mRxManage.add(
                mModel.orderDelete(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.OrderDeleteSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }

    /**
     * 上传图片
     *
     * @param requestData 参数
     */
    public void uploadImage(Map<String, String> requestData, List<File> filelist) {
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
     * 获取退款原因枚举定义
     *
     * @param requestData 参数
     */
    public void getRefundReasons(Map<String, String> requestData) {
        mRxManage.add(
                mModel.getRefundReasons(requestData, new RxSubscriber<List<RefundReasonsBean>>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(List<RefundReasonsBean> DataBean) {
                        mView.stopLoading();
                        mView.GetRefundReasonsSuc(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.GetHomePageFail(message);
                    }
                }));
    }


    /**
     * 退款接口
     *
     * @param requestData 参数
     */
    public void refund(RequestBody requestData) {
        mRxManage.add(
                mModel.refund(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.RefundSuc(DataBean);
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
     * 查询最大可退款数额
     * */
    public void getmaxrefundfee(HashMap<String, String> requestData) {
        mRxManage.add(
                mModel.getmaxrefundfee(requestData, new RxSubscriber<String>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("正在加载,请稍后...");
                    }

                    @Override
                    protected void _onNext(String DataBean) {
                        mView.stopLoading();
                        mView.getmaxrefundfee(DataBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopLoading();
                        mView.getmaxrefundfeeError(message);
                    }
                }));
    }
}
