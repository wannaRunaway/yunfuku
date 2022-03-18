package com.aifubook.book.mine.coupons;

import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface CouponsView extends BaseView {

    // 获取所有店铺可用优惠券
    void ShopCouponsSuc(List<MyCouponsBean> DataBean);

    // 用户领取优惠券
    void CouponReceiveSuc(String DataBean);

    // 用户领取过的优惠券, 个人中心页面 优惠券
    void MemberCouponsSuc(MemberCouponsBean DataBean);

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

}
