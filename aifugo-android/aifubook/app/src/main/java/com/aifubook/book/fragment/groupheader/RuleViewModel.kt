package com.aifubook.book.fragment.groupheader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aifubook.book.flowapi.BaseResponse
import com.aifubook.book.flowapi.flowRequest
import com.aifubook.book.flowapi.next
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RuleViewModel: ViewModel() {
    val textstring = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    fun getbeansConfig(map: MutableMap<String, String>){
        viewModelScope.launch {
            flowRequest {
                getConfigValue(map)
            }.next {
                textstring.value = this
            }
        }
    }
}