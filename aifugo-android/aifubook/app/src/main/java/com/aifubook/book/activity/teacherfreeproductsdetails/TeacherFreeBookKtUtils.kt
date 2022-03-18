package com.aifubook.book.activity.teacherfreeproductsdetails

import com.aifubook.book.bean.CarInBean
import com.aifubook.book.bean.ProductDetailBean
import com.aifubook.book.bean.ProductListBean
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import okhttp3.MediaType
import okhttp3.RequestBody

object TeacherFreeBookKtUtils {
    fun initgetBookRequest(
        addressId: String,
        productDetailBean: ProductListBean.list
    ): RequestBody {
        val carInBeans: ArrayList<CarInBean> = ArrayList()
        val carInBean = CarInBean()
        val productListBeans: MutableList<CarInBean.productListBean> = java.util.ArrayList()
        val productListBean = CarInBean.productListBean()
        productListBean.id = productDetailBean.getId()
        productListBean.discountPrice = productDetailBean.getDiscountPrice()
        productListBean.image = productDetailBean.getImage()
        productListBean.name = productDetailBean.getName()
        productListBean.price = productDetailBean.getPrice()
        productListBean.shopId = productDetailBean.getShopId()
        productListBean.stock = productDetailBean.getStock()
        productListBean.count = 1
        productListBean.limit = productDetailBean.getLimit()
        productListBean.zeroBuy = 1
        productListBeans.add(productListBean)
        carInBean.productListBeans = productListBeans
        carInBeans.add(carInBean)
        val jsonObject3 = JSONObject()
        val jsonArray = JSONArray()
        for (i in carInBeans.indices) {
            for (j in carInBeans[i].productListBeans.indices) {
                val jsonObject =
                    JSONObject()
                jsonObject["productId"] = carInBeans[i].productListBeans[j].id
                jsonObject["count"] = carInBeans[i].productListBeans[j].count
                jsonObject["recommend"] = 0
                jsonObject["shopId"] = carInBeans[i].productListBeans[j].shopId
                jsonObject["chiefId"] = carInBeans[i].productListBeans[j].chiefId
                jsonObject["zeroBuy"] = 1
                jsonArray.add(jsonObject)
            }
        }
        jsonObject3["orderItems"] = jsonArray
        jsonObject3["from"] = "app"
        jsonObject3["memberAddressId"] = addressId
        jsonObject3["zeroBuy"] = 1
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            jsonObject3.toString()
        )
    }
}