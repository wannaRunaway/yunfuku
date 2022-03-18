package com.aifubook.book.activity.teacher;

import com.aifubook.book.bean.ProductListBean;
import com.jiarui.base.bases.BaseView;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

interface TeacherFreeBooksListView extends BaseView {
    // 获取基本数据成功
    void getProductBean(ProductListBean DataBean, boolean isRefresh);
    // 获取基本数据失败
    void GetDataFail(String Message);

}
