package com.aifubook.book.mine.member;

import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface MemberInfoView extends BaseView {

    void GetSendImageSuc(String notDataBean);

    void GetSendImageFail(String Message);

    // 获取基本数据成功
    void GetShopSuc(ShopBean DataBean);

    // 获取基本数据失败
    void GetShopFail(String Message);

    // 获取用户信息接口成功
    void MemberInfoSuc(MemberInfoBean DataBean);


    // 修改用户信息
    void appliedShop(ShopBean DataBean);

    // 修改用户信息
    void UpdateMemberInfoSuc(String DataBean);

    // 获取可用余额
    void GetCanUsedBalanceSuc(String DataBean);

    // 获取用户账户信息
    void GetAccountInfoSuc(GetAccountInfoBean DataBean);

    // 判断用户当前有没有设置支付密码
    void HasPayPasswordSuc(String DataBean);

    // 设置支付密码
    void SetPayPasswordSuc(String DataBean);

    // 团长详情信息
    void ChiefDetailSuc(ChiefDetailsBean DataBean);

    // 获取佣金明细
    void RecordListSuc(CommissionDetailsBean DataBean);

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    // 获取基本数据成功
    void GetDataSuc(ProductListBean DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

    void getOrderCountResult(OrderCountVO orderCountVO);

    void getOrderCountError(String msg);

    void getWeChatToken(String token);

}
