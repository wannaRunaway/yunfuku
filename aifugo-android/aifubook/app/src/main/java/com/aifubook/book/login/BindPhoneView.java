package com.aifubook.book.login;

import com.aifubook.book.bean.LoginHomePageBean;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface BindPhoneView extends BaseView {

    // 获取用户信息接口成功
    void GetverificationCodeSuc(Integer DataBean);

    // 获取用户信息接口失败
    void GetverificationCodeFail(String Message);

    // 手机号登录
    void updateMemberEmail(String DataBean);

    // 手机号登录
    void GetHomePageSuc(String DataBean);

    // 手机号注册
    void UserRegisterSuc(String DataBean);

    // 手机号注册
    void updateMobile(String DataBean);

    // 手机号注册
    void bindMobile(LoginHomePageBean DataBean);

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

}
