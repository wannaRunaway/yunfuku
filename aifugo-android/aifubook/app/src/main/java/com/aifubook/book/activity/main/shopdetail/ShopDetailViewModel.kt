package com.aifubook.book.activity.main.shopdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.ShopBean
import com.aifubook.book.bean.ShopNew
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.getRequestBody
import com.aifubook.book.flowapi.next
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ShopDetailViewModel : ViewModel() {
    var getproducts = MutableStateFlow<BaseResponse<ProductListBean>>(BaseResponse())
    val shopDetail = MutableStateFlow<BaseResponse<ShopNew>>(BaseResponse())
    var addcart = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    var recommandProducts = MutableStateFlow<BaseResponse<ProductListBean>>(BaseResponse())
    fun flowproducts(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                productList(getRequestBody(map))
            }.next {
                getproducts.value = this
            }
        }
    }
    fun flowShopDetail(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                shopDetail(map)
            }.next {
                shopDetail.value = this
            }
        }
    }
    fun addcart(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                carAdd(map)
            }.next {
                addcart.value = this
            }
        }
    }
    fun flowrequestRecommandProducts(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                productList(getRequestBody(map))
            }.next {
                recommandProducts.value = this
            }
        }
    }
}