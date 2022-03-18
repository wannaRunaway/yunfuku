package com.aifubook.book.activity.check;

import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.TypeBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

public interface CheckView extends BaseView {
    // 获取基本数据成功
    void GetDataSuc(List<TypeBean> DataBean);
    // 获取基本数据失败
    void GetDataFail(String Message);
    // 获取首页信息接口失败
    void GetHomePageFail(String Message);
    // 获取首页轮播图
    void GetHomePage(SceneBean DataBean);
}
