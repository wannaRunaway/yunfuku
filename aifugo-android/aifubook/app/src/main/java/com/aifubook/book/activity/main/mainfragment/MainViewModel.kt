package com.aifubook.book.activity.main.mainfragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.bean.*
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.getRequestBody
import com.aifubook.book.flowapi.next
import com.jiarui.base.utils.LogUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class MainViewModel : ViewModel() {

    //    private lateinit var baseResponse:BaseResponse<T>;
    val categoryList = MutableStateFlow<BaseResponse<List<HomeCategoryBean>>>(BaseResponse())
    val bannerList = MutableStateFlow<BaseResponse<SceneBean>>(BaseResponse())
    var categoryAllList = MutableStateFlow<BaseResponse<List<TypeBean>>>(BaseResponse())
    var recommandProducts = MutableStateFlow<BaseResponse<ProductListBean>>(BaseResponse())
    val bindChief = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    val getNearlyShop = MutableStateFlow<BaseResponse<NearShopBean>>(BaseResponse())

    //    val shopDetail = MutableStateFlow<BaseResponse<ShopNew>>(BaseResponse())
    var productbuylist = MutableStateFlow<BaseResponse<List<EveryOneBuys>>>(BaseResponse())
    var addcart = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    fun requestCategoryList() {
        viewModelScope.launch {
            //单独处理异常
            flowRequest {
                getHomeCategory()
            }.next {
                categoryList.value = this
            }
        }
    }

    fun requestBannerList(map: MutableMap<String, Any>) {
        viewModelScope.launch {
            flowRequest {
                getByScene(map)
            }.next {
                bannerList.value = this
            }
        }
    }

    fun requestCategoryAllList(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                categoryAll(map)
            }.next {
                categoryAllList.value = this
            }
        }
    }

    fun flowrequestRecommandProducts(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                productList(getRequestBody(map))
            }.next {
                recommandProducts.value = this
            }
        }
    }

    fun flowBindChief(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                bindChief(map)
            }.next {
                bindChief.value = this
            }
        }
    }

    fun flowNearShop(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                getMostNearShop(map)
            }.next {
                getNearlyShop.value = this
            }
        }
    }

    //    fun flowShopDetail(map: MutableMap<String, String>){
//        viewModelScope.launch {
//            flowRequest {
//                shopDetail(map)
//            }.next {
//                shopDetail.value = this
//            }
//        }
//    }
    fun flowProductbuylist(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                productbuyList(getRequestBody(map))
            }.next {
                productbuylist.value = this
            }
        }
    }

    fun addcart(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                carAdd(map)
            }.next {
                addcart.value = this
            }
        }
    }
}