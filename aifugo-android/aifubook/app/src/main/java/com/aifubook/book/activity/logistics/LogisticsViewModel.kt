package com.aifubook.book.activity.logistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.logisticsflowRequest
import com.aifubook.book.flowapi.next
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LogisticsViewModel: ViewModel() {
    val logisticsBean = MutableStateFlow<BaseResponse<LogisticsAllBean>>(BaseResponse())
    fun getlogisticbean(map: Map<String,String>){
        viewModelScope.launch {
            flowRequest {
                logistics(map)
            }.next {
                logisticsBean.value = this
            }
        }
    }
}