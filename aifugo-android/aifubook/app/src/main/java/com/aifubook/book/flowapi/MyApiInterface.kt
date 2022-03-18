package com.aifubook.book.flowapi

import com.aifubook.book.activity.logistics.LogisticsAllBean
import com.aifubook.book.activity.main.mainfragment.EveryOneBuys
import com.aifubook.book.bean.*
import com.aifubook.book.mine.member.GetAccountInfoBean
import com.aifubook.book.mine.member.MemberInfoBean
import com.aifubook.book.regimental.CommissionDetailsBean
import com.jiarui.base.baserx.bean.BaseBean
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap
import rx.Observable


val Api: ApiInterface by lazy {
    retrofit.create(ApiInterface::class.java)
}

interface ApiInterface {
    @GET("advertisement/getByScene")//banner
    suspend fun getByScene(@QueryMap map: MutableMap<String, Any>): BaseResponse<SceneBean>

    @GET("product/category/bannerCategory")
    suspend fun getHomeCategory(): BaseResponse<List<HomeCategoryBean>>

    // 商品分类
    @GET("product/category/all")
    suspend fun categoryAll(@QueryMap map: MutableMap<String, String>): BaseResponse<List<TypeBean>>

    // 商品列表接口
    @POST("product/list")
    suspend fun productList(@Body body: RequestBody): BaseResponse<ProductListBean>

    @POST("member/bindChief")
    suspend fun bindChief(@QueryMap map: Map<String, String>): BaseResponse<String>

    // 获取最近得一个店铺
    @POST("shop/getShopForHome")
    suspend fun getMostNearShop(@QueryMap map: Map<String, String>): BaseResponse<NearShopBean>

    // 商品详情接口
    @GET("shop/detail")
    suspend fun shopDetail(@QueryMap map: Map<String, String>): BaseResponse<ShopNew>

    // 大家都在买
    @POST("product/buyList")
    suspend fun productbuyList(@Body body: RequestBody): BaseResponse<List<EveryOneBuys>>

    // 添加商品进购物车,或者购物车已有商品数量加减操作
    @POST("car/add")
    suspend fun carAdd(@QueryMap map: Map<String, String>): BaseResponse<String>

    // 获取佣金明细
    @POST("member/finance/record/list")
    suspend fun recordList(@Body body: RequestBody): BaseResponse<CommissionDetailsBean>

    // 获取用户基本信息
    @GET("member/info")
    suspend fun memberInfo(@QueryMap map: Map<String, String>): BaseResponse<MemberInfoBean>

    // 获取用户账户信息
    @GET("member/account/getAccountInfo")
    suspend fun getAccountInfo(@QueryMap map: Map<String, String>): BaseResponse<GetAccountInfoBean>

    // 配置字典
    @GET("key/value/config/getConfigValue")
    suspend fun getConfigValue(@QueryMap map: Map<String, String>): BaseResponse<String>

    //粉豆兑换
    @GET("member/account/exchangeBalance")
    suspend fun beanstomoney(@QueryMap map: Map<String, String>): BaseResponse<String>

    //门店列表、
    @POST("shop/findShops")
    suspend fun headgroupsfindShops(@QueryMap map: Map<String, String>): BaseResponse<List<NearShopBean>>

    //GET {{baseUrl}}/order/expressInfo?expressCompanyCode=shunfeng&expressNo=SF1340254693978
    @GET("order/expressInfo")
    suspend fun logistics(@QueryMap map: Map<String, String>): BaseResponse<LogisticsAllBean>

    //POST /chief/chiefBindShop
    @POST("chief/chiefBindShop")
    suspend fun bindshopchief(@Body body: RequestBody): BaseResponse<String>
}