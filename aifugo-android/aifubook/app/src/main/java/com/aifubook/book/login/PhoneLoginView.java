package com.aifubook.book.login;

import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.bean.SchoolBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface PhoneLoginView extends BaseView {

    // 获取用户信息接口成功
    void GetverificationCodeSuc(Integer DataBean);

    // 获取用户信息接口失败
    void GetverificationCodeFail(String Message);

    // 手机号登录
    void GetHomePageSuc(LoginHomePageBean DataBean);

    // 手机号注册
    void UserRegisterSuc(LoginHomePageBean DataBean);

    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    // 获取基本数据成功
    void GetDataSuc(List<SchoolBean> DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

    // 获取基本数据成功
    void GetShopSuc(List<ClassBean> DataBean);

    // 获取基本数据失败
    void GetShopFail(String Message);

    // 获取基本数据成功
    void GetListDataSuc(List<FindSchoolClassesBean> DataBean);

    // 获取基本数据成功
    void AddDataSuc(String DataBean);

    // 获取基本数据失败
    void GetListDataFail(String Message);

    void weChatLoginForApp(String DataBean);

    void weChatLoginForFail(String DataBean);

    void registerChief();

    void registerChiefError(String msg);

}
