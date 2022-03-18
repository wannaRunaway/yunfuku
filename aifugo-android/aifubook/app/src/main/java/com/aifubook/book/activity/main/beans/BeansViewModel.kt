package com.aifubook.book.activity.main.beans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.bean.HomeCategoryBean
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.getRequestBody
import com.aifubook.book.flowapi.next
import com.aifubook.book.mine.member.GetAccountInfoBean
import com.aifubook.book.mine.member.MemberInfoBean
import com.aifubook.book.regimental.CommissionDetailsBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BeansViewModel : ViewModel() {
    val comissBean = MutableStateFlow<BaseResponse<CommissionDetailsBean>>(BaseResponse())
    val accountInfo = MutableStateFlow<BaseResponse<GetAccountInfoBean>>(BaseResponse())
    val beansconfig = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    val beanstomoney = MutableStateFlow<BaseResponse<String>>(BaseResponse())
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
    fun getbeansConfig(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                getConfigValue(map)
            }.next {
                beansconfig.value = this
            }
        }
    }
    fun beanstomoney(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                beanstomoney(map)
            }.next {
                beanstomoney.value = this
            }
        }
    }
}