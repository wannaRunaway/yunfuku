package com.aifubook.book.mine.member;

import com.aifubook.book.bean.LogoutBean;
import com.jiarui.base.bases.BaseView;

import org.json.JSONObject;

public interface LogoutView extends BaseView {


    void setLogout();

    void getLogoutResult(String logoutBean);

}
