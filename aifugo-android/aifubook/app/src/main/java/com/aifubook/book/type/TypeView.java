package com.aifubook.book.type;

import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.mine.coupons.MyCouponsBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface TypeView extends BaseView {

    // 获取基本数据成功
    void GetDataSuc(List<TypeBean> DataBean);

    // 获取距离最近得店铺
    void GetNearShop(NearShopBean DataBean);

    // 获取首页轮播图
    void GetHomePage(SceneBean DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

    // 获取基本数据成功
    void GetProSuc(ProductListBean DataBean);

    // 获取基本数据成功
    void GetProSucs(ProductListBean DataBean);


    // 获取基本数据失败
    void GetProFail(String Message);

    // 获取基本数据成功
    void GetShopSuc(ShopBean DataBean);

    // 获取基本数据失败
    void GetShopFail(String Message);

    // 获取所有店铺可用优惠券
    void ShopCouponsSuc(List<MyCouponsBean> DataBean);

    // 团长详情信息
    void ChiefDetailSuc(ChiefDetailsBean DataBean);


    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    void getChiefResult();

    void showChiefResultError(String msg);



}
