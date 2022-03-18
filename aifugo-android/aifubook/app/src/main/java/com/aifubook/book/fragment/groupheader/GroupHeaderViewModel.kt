package com.aifubook.book.fragment.groupheader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.bean.NearShopBean
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.getRequestBody
import com.aifubook.book.flowapi.next
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class GroupHeaderViewModel : ViewModel() {
    val shoplistbean = MutableStateFlow<BaseResponse<List<NearShopBean>>>(BaseResponse())
    val bindingresults = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    fun getmyshoplist(map: HashMap<String,String>){
        viewModelScope.launch {
            flowRequest {
                headgroupsfindShops(map)
            }.next {
                shoplistbean.value = this
            }
        }
    }
    fun bindshop(map: HashMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                bindshopchief(getRequestBody(map))
            }.next {
                bindingresults.value = this
            }
        }
    }
}