package com.aifubook.book.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.getRequestBody
import com.aifubook.book.flowapi.next
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    var recommandProducts = MutableStateFlow<BaseResponse<ProductListBean>>(BaseResponse())
    fun flowrequestRecommandProducts(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                productList(getRequestBody(map))
            }.next {
                recommandProducts.value = this
            }
        }
    }
}