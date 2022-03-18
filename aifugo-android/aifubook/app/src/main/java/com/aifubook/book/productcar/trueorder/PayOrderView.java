package com.aifubook.book.productcar.trueorder;

import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface PayOrderView extends BaseView {

    // 获取用户信息接口成功
    void MemberInfoSuc(MemberInfoBean DataBean);

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

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    // 获取首页信息接口失败
    void orderToPayWeChat(SendRechargeBean Message);


    void OrderToPaySuc(String DataBean);


}
