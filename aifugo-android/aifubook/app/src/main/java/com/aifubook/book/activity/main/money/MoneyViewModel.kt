package com.aifubook.book.activity.main.money

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.getRequestBody
import com.aifubook.book.flowapi.next
import com.aifubook.book.mine.member.GetAccountInfoBean
import com.aifubook.book.regimental.CommissionDetailsBean
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoneyViewModel : ViewModel() {
    val comissBean = MutableStateFlow<BaseResponse<CommissionDetailsBean>>(BaseResponse())
    val accountInfo = MutableStateFlow<BaseResponse<GetAccountInfoBean>>(BaseResponse())
    fun commissionlist(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                recordList(getRequestBody(map))
            }.next {
                comissBean.value = this
            }
        }
    }
    fun getaccountInfo(map: MutableMap<String, String>) {
        viewModelScope.launch {
            flowRequest {
                getAccountInfo(map)
            }.next {
                accountInfo.value = this
            }
        }
    }
}