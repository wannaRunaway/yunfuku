package com.aifubook.book.api;

import com.aifubook.book.activity.logistics.LogisticsAllBean;
import com.aifubook.book.activity.logistics.dadabeans.DadaResultBean;
import com.aifubook.book.base.CouponBean;
import com.aifubook.book.base.CouponBeans;
import com.aifubook.book.bean.ClassBean;
import com.aifubook.book.bean.FindSchoolClassesBean;
import com.aifubook.book.bean.GrouponPageBean;
import com.aifubook.book.bean.GrouponShareBean;
import com.aifubook.book.bean.HomeCategoryBean;
import com.aifubook.book.bean.LoginHomePageBean;
import com.aifubook.book.bean.MyChileBean;
import com.aifubook.book.bean.NearShopBean;
import com.aifubook.book.bean.OrderCountVO;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.ProductListBean;
import com.aifubook.book.bean.SameBean;
import com.aifubook.book.bean.SceneBean;
import com.aifubook.book.bean.SchoolBean;
import com.aifubook.book.bean.SendRechargeBean;
import com.aifubook.book.bean.ServiceBean;
import com.aifubook.book.bean.ShopBean;
import com.aifubook.book.bean.ShopHomeBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.bean.UpdateBean;
import com.aifubook.book.bean.WeChatToken;
import com.aifubook.book.bean.getKidBean;
import com.aifubook.book.mine.address.AddressListBean;
import com.aifubook.book.mine.coupons.MemberCouponsBean;
import com.aifubook.book.mine.coupons.MyCouponsBean;
import com.aifubook.book.mine.member.GetAccountInfoBean;
import com.aifubook.book.mine.member.MemberInfoBean;
import com.aifubook.book.mine.order.bean.CreateOrderBean;
import com.aifubook.book.mine.order.bean.OrderDetailsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.aifubook.book.mine.order.bean.RefundReasonsBean;
import com.aifubook.book.mine.order.bean.ToPayBean;
import com.aifubook.book.mine.order.bean.afterdetails.CompantItem;
import com.aifubook.book.mine.order.bean.afterdetails.ServiceDetailsBean;
import com.aifubook.book.productcar.cart.CartBean;
import com.aifubook.book.regimental.ChiefDetailsBean;
import com.aifubook.book.regimental.ChiefMembersBean;
import com.aifubook.book.regimental.ChiefMyChiefBean;
import com.aifubook.book.regimental.ChiefOrderByCodeBean;
import com.aifubook.book.regimental.CommissionDetailsBean;
import com.aifubook.book.regimental.RechargeBean;
import com.jiarui.base.baserx.bean.BaseBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by ListKer_Hlg on 2018/7/30
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */

public interface ApiService {

    String IMAGE = "https://yfk.oss-cn-beijing.aliyuncs.com/";   // 请求图片
    String HOSTBASE = "https://www.aifubook.com/";   // h5正式
    String HOST90 = "http://192.168.101.216:8090/";   // h5测试
    //        public static final String BASE_HOST = "http://192.168.101.251:8080/"; //崔旭祥
    String BASE_HOST = "http://192.168.101.216:8080/";   // 测试环境
//    String BASE_HOST = "https://api.aifubook.com/";   // 线上环境
//    String BASE_HOST = "http://192.168.101.17:8080/";   //胡玉江
    String weburl = "h5/#/pages/index/index?";
    String URL_CHECK = "http://jy.51titi.cn/";//教师认证
    String wxappid = "wx494d5354ef916936"; //微信appid
    String shopinvitecode = "inviteCode=-1";
    double onehundred = 100.00;

    @GET("groupBuy/orderDetail")
    Observable<BaseBean<GrouponShareBean>> getGroupnOrderDetail(@QueryMap Map<String, String> map);

    @GET("groupBuy/list")
    Observable<BaseBean<GrouponPageBean>> getGrouponList(@QueryMap Map<String, String> map);

    @POST("order/uploadRefundExpressNo")
    Observable<BaseBean<String>> uploadRefundNo(@Body RequestBody body);

    @GET("product/category/bannerCategory")
    Observable<BaseBean<List<HomeCategoryBean>>> getHomeCategory();

    @GET("product/hotBooks")
    Observable<BaseBean<ShopHomeBean>> getHotBooks(@QueryMap Map<String, String> map);

    @POST("member/bindChief")
    Observable<BaseBean<String>> bindChief(@QueryMap Map<String, String> map);

    @GET("order/orderCount")
    Observable<BaseBean<OrderCountVO>> getOrderCount();

    @GET("config/updateApp")
    Observable<BaseBean<UpdateBean>> updateApp();

    //注销接口
    @POST("member/logoff")
    Observable<BaseBean<String>> logout();

    // 注册接口
    @POST("user/register")
    Observable<BaseBean<String>> userRegister(@Body Map<String, String> map);

    //新的登录接口
    @POST("member/registerNew")
    Observable<BaseBean<LoginHomePageBean>> loginNew(@Body RequestBody body);

    @GET("config/wechatAccessToken")
    Observable<BaseBean<WeChatToken>> getWechatAccessToken();

    @POST("member/mobileLogin")
    Observable<BaseBean<LoginHomePageBean>> UserLogin(@QueryMap Map<String, String> map);

    //
    // 发送验证码
    @POST("sms/sendSmsCode")
    Observable<BaseBean<Integer>> sendSmsCode(@QueryMap Map<String, String> map);

    // 商品分类
    @GET("product/category/all")
    Observable<BaseBean<List<TypeBean>>> categoryAll(@QueryMap Map<String, String> map);

    // 商品列表接口
    @POST("product/list")
    Observable<BaseBean<ProductListBean>> productList(@Body RequestBody body);

    // 商品列表接口---免费领取书本
    @POST("product/list")
    Observable<BaseBean<ProductListBean>> teacherFreebooks(@Body RequestBody body);

    // 商品列表接口
    @POST("shop/findShops")
    Observable<BaseBean<List<NearShopBean>>> findShops(@QueryMap Map<String, Object> map);

    // 商品详情接口
    @GET("product/detail")
    Observable<BaseBean<ProductDetailBean>> productDetail(@QueryMap Map<String, String> map);

    // 商品详情接口
    @GET("shop/detail")
    Observable<BaseBean<ShopBean>> shopDetail(@QueryMap Map<String, String> map);


    // 多个文件上传。图片上传，大小超过1MB,将会被压缩【并生成记录】
    @Multipart
    @POST("uploadImage")
    Observable<BaseBean<String>> uploadMulti(@PartMap() Map<String, RequestBody> files);

    // 商品列表接口
    @POST("shop/apply")
    Observable<BaseBean<String>> shopApply(@Body RequestBody body);

    @GET("shop/appliedShop")
    Observable<BaseBean<ShopBean>> appliedShop(@QueryMap Map<String, String> map);


//    /member/mobileLogin?mobile=&smsCode=


    /**********************************************************************新增接口******************************************************/

    // 注册接口
    @POST("member/registerNew")
    Observable<BaseBean<LoginHomePageBean>> userRegister(@Body RequestBody body);

    // 发送注册验证码
    @POST("sms/sendRegCode")
    Observable<BaseBean<Integer>> sendRegCode(@QueryMap Map<String, String> map);

    // 获取用户基本信息
    @GET("member/info")
    Observable<BaseBean<MemberInfoBean>> memberInfo(@QueryMap Map<String, String> map);

    // 修改用户信息
    @POST("member/updateMemberInfo")
    Observable<BaseBean<String>> updateMemberInfo(@Body RequestBody body);

    // 修改用户信息
    @POST("member/updateMemberEmail")
    Observable<BaseBean<String>> updateMemberEmail(@QueryMap Map<String, String> map);


    // 获取最近得一个店铺
    @POST("shop/getShopForHome")
    Observable<BaseBean<NearShopBean>> getMostNearShop(@QueryMap Map<String, Object> map);


    // 获取所有店铺可用优惠券
    @GET("coupon/shopCoupons")
    Observable<BaseBean<List<MyCouponsBean>>> shopCoupons(@QueryMap Map<String, String> map);

    // 获取所有店铺可用优惠券
    @GET("coupon/shopCoupons")
    Observable<BaseBean<List<CouponBean>>> getShopCoupons(@QueryMap Map<String, String> map);

    // 用户领取优惠券 前端判断下最好防重入
    @POST("coupon/receive")
    Observable<BaseBean<String>> couponReceive(@QueryMap Map<String, String> map);

    // 用户领取过的优惠券, 个人中心页面 优惠券
    @POST("coupon/memberCoupons")
    Observable<BaseBean<MemberCouponsBean>> memberCoupons(@Body RequestBody body);

    // 创建订单
    @POST("order/createOrder")
    Observable<BaseBean<CreateOrderBean>> createOrder(@Body RequestBody body);

    // 创建订单
    @POST("order/getSameCityFeeForApp")
    Observable<BaseBean<SameBean>> getSameCityFeeForApp(@Body RequestBody requestBody);

    // 拿到createOrder 返回的支付订单支付
    @POST("order/toPay")
    Observable<BaseBean<String>> orderToPayObject(@Body RequestBody requestBody);

    // 拿到createOrder 返回的支付订单支付
    @POST("order/toPay")
    Observable<BaseBean<SendRechargeBean>> orderToPayWeChat(@Body RequestBody requestBody);

    // 未支付的订单使用微信重新支付
    @POST("order/repay")
    Observable<BaseBean<SendRechargeBean>> orderWXRepay(@Body RequestBody requestBody);

    // 判断在线支付订单是否完成
    @GET("order/isPaySuccess")
    Observable<BaseBean<Boolean>> orderIsPaySuccess(@QueryMap Map<String, String> map);

    // 订单详情 (物理订单 非支付订单)
    @GET("order/detail")
    Observable<BaseBean<OrderDetailsBean>> orderDetail(@QueryMap Map<String, String> map);

    // 订单确认收货并完成
    @GET("order/completed")
    Observable<BaseBean<String>> orderCompleted(@QueryMap Map<String, String> map);

    // 取消订单
    @GET("order/setCancle")
    Observable<BaseBean<String>> orderSetCancle(@QueryMap Map<String, String> map);

    // 获取购物车信息
    @GET("car/get")
    Observable<BaseBean<CartBean>> carGet(@QueryMap Map<String, String> map);

    // 添加商品进购物车,或者购物车已有商品数量加减操作
    @POST("car/add")
    Observable<BaseBean<String>> carAdd(@QueryMap Map<String, String> map);

    // 订单页面 再次购买
    @POST("car/rebuy")
    Observable<BaseBean<String>> reBuy(@QueryMap Map<String, String> map);

    // 重置购物车数量
    @POST("car/resetCount")
    Observable<BaseBean<String>> carResetCount(@Body RequestBody requestBody);

    // 删除购物车商品 直接删除或者单个商品减到0
    @POST("car/remove")
    Observable<BaseBean<String>> carRemove(@QueryMap Map<String, String> map);

    // 批量删除购物车商品
    @POST("car/batchRemove")
    Observable<BaseBean<String>> carBatchRemove(@Body RequestBody body);

    // 获取用户所有收货地址
    @GET("member/address/list")
    Observable<BaseBean<List<AddressListBean>>> addressList(@QueryMap Map<String, String> map);

    // 获取用户所有收货地址
    @GET("member/address/current")
    Observable<BaseBean<AddressListBean>> getDefaultAddress();

    // 获取用户所有收货地址
    @GET("key/value/config/getConfigValue")
    Observable<BaseBean<String>> getConfigValue(@QueryMap Map<String, String> map);


    // 获取默认的收货地址
    @GET("member/address/getById")
    Observable<BaseBean<AddressListBean>> addressCurrent(@QueryMap Map<String, String> map);

    // 修改默认的收货地址
    @POST("member/address/updateDefault")
    Observable<BaseBean<String>> updateDefaultAddress(@Body RequestBody body);

    // 添加收货地址
    @POST("member/address/add")
    Observable<BaseBean<AddressListBean>> addressAdd(@Body RequestBody body);

    // 更新收货地址
    @POST("member/address/update")
    Observable<BaseBean<AddressListBean>> addressUpdate(@Body RequestBody body);

    // 删除收货地址
    @POST("member/address/delete")
    Observable<BaseBean<AddressListBean>> addressDelete(@QueryMap Map<String, String> map);

    // 获取可用余额
    @GET("member/account/getCanUsedBalance")
    Observable<BaseBean<String>> getCanUsedBalance(@QueryMap Map<String, String> map);

    // 获取用户账户信息
    @GET("member/account/getAccountInfo")
    Observable<BaseBean<GetAccountInfoBean>> getAccountInfo(@QueryMap Map<String, String> map);

    // 判断用户当前有没有设置支付密码
    @GET("member/account/hasPayPassword")
    Observable<BaseBean<String>> hasPayPassword(@QueryMap Map<String, String> map);

    // 设置支付密码
    @POST("member/account/setPayPassword")
    Observable<BaseBean<String>> setPayPassword(@QueryMap Map<String, String> map);

    // 小程序端订单统一查询接口
    @POST("order/list")
    Observable<BaseBean<OrderListBean>> orderList(@Body RequestBody body);

    // 删除订单
    @POST("order/delete")
    Observable<BaseBean<String>> orderDelete(@QueryMap Map<String, String> map);


    // 获取可用优惠券
    @GET("coupon/shopCoupons")
    Observable<BaseBean<List<CouponBeans>>> couponShopCoupons(@QueryMap Map<String, String> map);

    // 获取可用优惠券
    @GET("member/child/list")
    Observable<BaseBean<List<ClassBean>>> childList(@QueryMap Map<String, String> map);

    // 获取可用优惠券
    @GET("advertisement/getByScene")
    Observable<BaseBean<SceneBean>> getByScene(@QueryMap Map<String, Object> map);

    // 根据CityId查找学校
    @GET("school/findByCityId")
    Observable<BaseBean<List<SchoolBean>>> findByCityId(@QueryMap Map<String, String> map);


    // 获取学校下面得班级
    @GET("school/findSchoolClasses")
    Observable<BaseBean<List<FindSchoolClassesBean>>> findSchoolClasses(@QueryMap Map<String, Object> map);

    // 获取学校下面得班级
    @POST("member/weChatLoginForApp")
    Observable<BaseBean<LoginHomePageBean>> weChatLoginForApp(@QueryMap Map<String, Object> map);


    // 获取学校下面得班级
    @POST("member/child/addChild")
    Observable<BaseBean<String>> childaddChild(@Body RequestBody map);

    // 获取学校下面得班级
    @GET("coupon/fullSiteReduceCoupons")
    Observable<BaseBean<List<MyCouponsBean>>> fullSiteReduceCoupons(@QueryMap Map<String, String> map);

    //    /member/mobileLogin?mobile=&smsCode=
// 团长申请 手机号必填
    @POST("chief/apply")
    Observable<BaseBean<String>> apply(@Body RequestBody body);

    // 团长详情信息
    @GET("chief/detail")
    Observable<BaseBean<ChiefDetailsBean>> chiefDetail(@QueryMap Map<String, String> map);

    // 团长详情信息
    @GET("chief/getChief")
    Observable<BaseBean<MyChileBean>> myChief(@QueryMap Map<String, String> map);


    // 团长介绍的用户的分页查询
    @POST("chief/members")
    Observable<BaseBean<ChiefMembersBean>> chiefMembers(@Body RequestBody body);

    // 获取用户的团长
    @GET("chief/myChief")
    Observable<BaseBean<ChiefMyChiefBean>> chiefMyChief(@QueryMap Map<String, String> map);

    // 通用传图接口
    @Multipart
    @POST("uploadImage")
    Observable<BaseBean<String>> uploadImage(@QueryMap() Map<String, String> params,
                                             @PartMap() Map<String, RequestBody> files);

    // 通用传图接口
    @Multipart
    @POST("uploadImage")
    Observable<BaseBean<String>> uploadImageSingle(@QueryMap() Map<String, String> params,
                                                   @PartMap() Map<String, RequestBody> files);

    // 获取用户未使用
    @GET("coupon/memberUnUsedCoupons")
    Observable<BaseBean<List<CouponBeans>>> memberUnUsedCoupons(@QueryMap Map<String, String> map);


    // 获取修改手机号
    @POST("sms/sendUpdateCode")
    Observable<BaseBean<Integer>> sendUpdateCode(@QueryMap Map<String, String> map);

    // 获取修改手机号
    @POST("sms/checkCode")
    Observable<BaseBean<String>> checkCode(@QueryMap Map<String, String> map);


    // 获取修改手机号
    @POST("member/updateMobile")
    Observable<BaseBean<String>> updateMobile(@QueryMap Map<String, String> map);

    // 获取修改手机号
    @POST("member/appBindWeChat")
    Observable<BaseBean<String>> appBindWeChat(@QueryMap Map<String, String> map);

    @POST("member/bindMobile")
    Observable<BaseBean<LoginHomePageBean>> bindMobile(@QueryMap Map<String, String> map);


    // 团长根据核销码查询订单详情
    @GET("order/getChiefOrderByCode")
    Observable<BaseBean<ChiefOrderByCodeBean>> getChiefOrderByCode(@QueryMap Map<String, String> map);

    // 订单团长提货核销 团长使用
    @GET("order/setFetched")
    Observable<BaseBean<String>> setFetched(@QueryMap Map<String, String> map);

    // 获取佣金明细
    @POST("member/finance/record/list")
    Observable<BaseBean<CommissionDetailsBean>> recordList(@Body RequestBody body);

    // 获取佣金明细
    @POST("sys/notice/list")
    Observable<BaseBean<ServiceBean>> noticeList(@QueryMap Map<String, String> map);

    // 获取佣金明细
    @GET("member/child/delete")
    Observable<BaseBean<String>> childDelete(@QueryMap Map<String, Object> map);

    // 获取佣金明细
    @GET("member/child/getChildById")
    Observable<BaseBean<getKidBean>> getChildById(@QueryMap Map<String, Object> map);

    // 获取佣金明细
    @POST("member/child/updateChild")
    Observable<BaseBean<String>> childUpdateChild(@Body RequestBody body);

    // 获取退款原因枚举定义
    @GET("order/getRefundReasons")
    Observable<BaseBean<List<RefundReasonsBean>>> getRefundReasons(@QueryMap Map<String, String> map);

    // 退款接口
    @POST("order/refund")
    Observable<BaseBean<String>> refund(@Body RequestBody body);

    // 目前团长申请佣金提现
    @POST("draw/commission/apply")
    Observable<BaseBean<String>> commissionApply(@QueryMap Map<String, String> map);

    // 创建充值订单
    @POST("charge/order/create")
    Observable<BaseBean<RechargeBean>> orderCreate(@QueryMap Map<String, String> map);

    //查询物流接口
    @GET("order/expressInfo")
    Observable<BaseBean<LogisticsAllBean>> logistics(@QueryMap Map<String, String> map);

//    //查询物流接口嗒哒
//    @GET("order/expressInfo")
//    Observable<BaseBean<List<ResultDTO>>> logisticsDADA(@QueryMap Map<String, String> map);

    //GET /order/refundRecordDetail
    //售后详情
    @GET("order/refundRecordDetail")
    Observable<BaseBean<ServiceDetailsBean>> servicedetails(@QueryMap Map<String, String> map);

    //取消申请售后
    @POST("order/cancelRefund")
    Observable<BaseBean<String>> cancelrefund(@Body RequestBody body);

    //物流公司列表
    @GET("express/list")
    Observable<BaseBean<List<CompantItem>>> companylist(@QueryMap Map<String, String> map);

    //嗒哒物流查询
    @GET("order/getDadaRecordInfo")
    Observable<BaseBean<List<DadaResultBean>>> getdadarecordInfo(@QueryMap Map<String, String> map);

    //查询最大可退款数额
    @GET("order/getRefundMaxFee")
    Observable<BaseBean<String>> getrefundmaxfee(@QueryMap Map<String, String> map);
}
