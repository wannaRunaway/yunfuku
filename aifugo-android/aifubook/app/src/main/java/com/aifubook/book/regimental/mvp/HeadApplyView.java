package com.aifubook.book.regimental.mvp;

import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.ChiefMembersBean;
import com.aifubook.book.regimental.ChiefMyChiefBean;
import com.aifubook.book.regimental.ChiefOrderByCodeBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.regimental.RechargeBean;
import com.jiarui.base.bases.BaseView;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2018/10/17
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface HeadApplyView extends BaseView {

    // 团长申请 手机号必填
    void ChiefApplySuc(String DataBean);

    // 团长详情信息
    void ChiefDetailSuc(ChiefDetailsBean DataBean);

    // 团长介绍的用户的分页查询
    void ChiefMembersSuc(ChiefMembersBean DataBean);

    // 获取用户的团长
    void ChiefMyChiefSuc(ChiefMyChiefBean DataBean);

    // 获取基本数据成功
    void GetDataSuc(List<SchoolBean> DataBean);

    // 获取基本数据失败
    void GetDataFail(String Message);

    // 获取基本数据成功
    void GetListDataSuc(List<FindSchoolClassesBean> DataBean);

    // 获取用户信息接口成功
    void GetverificationCodeSuc(Integer DataBean);

    // 上传图片成功
    void UploadImageSuc(String DataBean);

    // 团长根据核销码查询订单详情
    void GetChiefOrderByCodeSuc(ChiefOrderByCodeBean DataBean);

    // 订单团长提货核销 团长使用
    void SetFetchedSuc(String DataBean);

    // 小程序端订单统一查询接口
    void OrderListSuc(OrderListBean DataBean);

    // 获取佣金明细
    void RecordListSuc(CommissionDetailsBean DataBean);

    // 申请提现
    void CommissionApplySuc(String DataBean);

    // 创建充值订单
    void OrderCreateSuc(RechargeBean DataBean);


    // 获取首页信息接口失败
    void GetHomePageFail(String Message);

    // 获取基本数据成功
    void GetDataSucs(ProductListBean DataBean);

}
