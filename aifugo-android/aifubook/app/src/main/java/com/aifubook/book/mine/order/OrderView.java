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
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface OrderView extends BaseView {

    // 小程序端订单统一查询接口
    void OrderListSuc(OrderListBean DataBean);

    // 创建订单
    void CreateOrderSuc(CreateOrderBean DataBean);

    // 拿到createOrder 返回的支付订单支付
    void OrderToPaySuc(ToPayBean DataBean);

    // 未支付的订单重新支付
    void OrderRepaySuc(ToPayBean DataBean);

    void orderToPayWeChat(SendRechargeBean Message);

    // 判断在线支付订单是否完成
    void OrderIsPaySuccessSuc(Boolean DataBean);

    //订单详情 (物理订单 非支付订单)
    void OrderDetailSuc(OrderDetailsBean DataBean);

    // 订单确认收货并完成
    void OrderCompletedSuc(String DataBean);

    // 取消订单
    void OrderSetCancleSuc(String DataBean);

    // 删除订单
    void OrderDeleteSuc(String DataBean);

    // 上传图片成功
    void UploadImageSuc(String DataBean);

    // 获取退款原因枚举定义
    void GetRefundReasonsSuc(List<RefundReasonsBean> DataBean);

    // 退款接口
    void RefundSuc(String DataBean);

    // 订单团长提货核销 团长使用
    void SetFetchedSuc(String DataBean);

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    void CardAddSuc(String DataBean);

    void CardAddFail(String message);

    void uploadRefundNoResult(String result);

    void uploadRefundNoError(String error);
    //顺丰物流信息
    void shunfenMessage(LogisticsAllBean logisticsAllBean);
    //嗒哒物流信息
    void dadamessage(List<DadaResultBean> list);
    //售后详情
    void servicedetails(ServiceDetailsBean serviceDetailsBean);
    //撤销申请
    void cancelrefund(String s);
    //公司列表
    void companylist(List<CompantItem> list);
    //获取最大可退款数额
    void getmaxrefundfee(String money);
    //获取最大可退款数额
    void getmaxrefundfeeError(String message);
}
