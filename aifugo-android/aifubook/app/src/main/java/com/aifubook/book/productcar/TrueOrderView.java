package com.aifubook.book.productcar;

import com.aifubook.book.base.CouponBeans;
import com.aifubook.book.bean.MyChileBean;
import com.aifubook.book.bean.SameBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface TrueOrderView extends BaseView {

    void getDefaultAddress(AddressListBean addressListBean);

    void getDefaultAddressError(String msg);

    // 获取用户所有收货地址
    void AddressListSuc(List<AddressListBean> DataBean);

    // 获取用户所有收货地址
    void getSameCityFeeForApp(SameBean DataBean);

    void getSameCityFeeError(String msg);


    // 获取用户所有收货地址
    void MyCouponsBean(List<CouponBeans> DataBean);


    // 获取用户所有收货地址
    void CouponBeansListSuc(List<CouponBeans> DataBean);

    // 获取默认的收货地址
    void AddressCurrentSuc(AddressListBean DataBean);

    // 创建订单
    void CreateOrderSuc(CreateOrderBean DataBean);

    // 拿到createOrder 返回的支付订单支付
    void OrderToPaySuc(ToPayBean DataBean);

    // 未支付的订单重新支付
    void OrderRepaySuc(ToPayBean DataBean);

    // 判断在线支付订单是否完成
    void OrderIsPaySuccessSuc(Boolean DataBean);

    //订单详情 (物理订单 非支付订单)
    void OrderDetailSuc(OrderDetailsBean DataBean);

    // 订单确认收货并完成
    void OrderCompletedSuc(String DataBean);

    // 取消订单
    void OrderSetCancleSuc(String DataBean);

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    // 团长详情信息
    void ChiefDetailSuc(ChiefDetailsBean DataBean);

    // 取消订单
    void GetShopSuc(ShopBean DataBean);

    // 取消订单
    void myChief(MyChileBean DataBean);

    void getChiefError(String msg);

    // 获取首页信息接口失败
    void GetShopFail(String Message);


    void MemberInfoSuc(MemberInfoBean DataBean);

}
