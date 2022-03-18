package com.aifubook.book.flowapi

import com.aifubook.book.activity.logistics.LogisticsBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.CancellationException

/**
 * 使用flow+retrofit
 * @param showLoading Boolean 是否 显示loading框
 * @param request [@kotlin.ExtensionFunctionType] SuspendFunction1<ApiInterface, BaseResponse<T>?>
 * @return Flow<BaseResponse<T>>
 */
suspend fun <T> flowRequest(
    request: suspend ApiInterface.() -> BaseResponse<T>?
): Flow<BaseResponse<T>> {
    return flow {
        val response = request(Api) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
//        response.throwAPIException();
        emit(response)
    }.flowOn(Dispatchers.IO)
        .onCompletion { cause ->
            run {
                cause?.let { throw catchException(it) }// 这里再重新把捕获的异常再次抛出，调用的时候如果有必要可以再次catch 获取异常
            }

        }
}

suspend fun logisticsflowRequest(
    request: suspend ApiInterface.() -> LogisticsBean?
): Flow<LogisticsBean> {
    return flow {
        val response = request(Api) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
        emit(response)
    }.flowOn(Dispatchers.IO)
        .onCompletion { cause ->
            run {
                cause?.let { throw catchException(it) }// 这里再重新把捕获的异常再次抛出，调用的时候如果有必要可以再次catch 获取异常
            }

        }
}

fun onApiError(exception: ApiException) {
    if (handlerApiException(null, exception)) {
//        apiExceptionEvent.value = exception
        return
    } else {
    }

}

fun catchException(
    e: Throwable,
): Throwable {
    e.printStackTrace()

    if (e is CancellationException) {
        return e;
    }
    val exception = HandlerException.handlerException(e)
    exception?.let {
        if (it is ApiException) {
//            vm.onApiError(it);
        } else {
        }
    }
    return exception;
}

fun <T> Flow<T>.catchError(bloc: Throwable.() -> Unit) = catch { cause -> bloc(cause) }

suspend fun <T> Flow<T>.next(bloc: suspend T.() -> Unit): Unit = catch { }.collect { bloc(it) }

fun getRequestBody(hashMap: Map<String, Any>): RequestBody {
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