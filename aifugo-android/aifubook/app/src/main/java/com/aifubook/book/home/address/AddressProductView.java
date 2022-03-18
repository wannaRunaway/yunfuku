package com.aifubook.book.home.address;

import com.aifubook.book.bean.NearShopBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface AddressProductView extends BaseView {

    // 获取基本数据成功
    void GetDataSuc(List<NearShopBean> DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

}
