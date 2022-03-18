package com.aifubook.book.classmanger;

import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.bean.getKidBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface ClassView extends BaseView {

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

    // 获取基本数据成功
    void childDelete(String DataBean);

    // 获取基本数据成功
    void getChildById(getKidBean DataBean);



    // 获取基本数据失败
    void GetListDataFail(String Message);


}
