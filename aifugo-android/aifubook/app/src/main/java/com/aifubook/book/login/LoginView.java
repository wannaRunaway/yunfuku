package com.aifubook.book.login;

import com.aifubook.book.bean.LoginHomePageBean;
import com.jiarui.base.bases.BaseView;

public interface LoginView extends BaseView {


    // 获取用户信息接口成功
    void GetverificationCodeSuc(Integer DataBean);

    // 获取用户信息接口失败
    void GetverificationCodeFail(String Message);

    // 手机号登录
    void loginSuccess(LoginHomePageBean DataBean);

    // 手机号注册
    void UserRegisterSuc(LoginHomePageBean DataBean);


    void weChatLoginForFail(String DataBean);


    void loginFail(String DataBean);

}
