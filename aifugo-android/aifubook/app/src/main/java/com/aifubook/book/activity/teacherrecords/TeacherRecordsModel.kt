package com.aifubook.book.activity.teacherrecords

import com.aifubook.book.api.Api
import com.aifubook.book.base.BaseModel
import com.aifubook.book.mine.order.bean.OrderListBean
import com.jiarui.base.baserx.RxHelper
import com.jiarui.base.baserx.RxSubscriber
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import rx.Subscription

class TeacherRecordsModel : BaseModel() {
    /**
     * 小程序端订单统一查询接口
     */
    fun orderList(
        requestData: Map<String?, String?>?,
        mRxSubscriber: RxSubscriber<OrderListBean>?
    ): Subscription? {
        return Api.getInstance().service
            .orderList(getRequestBody(requestData))
            .compose(RxHelper.handleResult())
            .subscribe(mRxSubscriber)
    }

    fun getRequestBody(hashMap: Map<String?, String?>?): RequestBody? {
        val data = StringBuffer()
        val jsonObjects = JSONObject()
        val jsonObject = JSONObject()
        if (hashMap != null && hashMap.size > 0) {
            val iter: Iterator<*> = hashMap.entries.iterator()
            while (iter.hasNext()) {
                val entry =
                    iter.next() as Map.Entry<*, *>
                val key = entry.key!!
                val `val` = entry.value!!
                data.append(key).append("=").append(`val`).append("&")
                try {
                    jsonObject.put(key.toString() + "", `val`.toString() + "")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        try {
            jsonObjects.put("param", jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jso = data.substring(0, data.length - 1)
        //        Log.e("httphttphttp", "http" + jsonObjects);
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            jsonObject.toString()
        )
    }
}