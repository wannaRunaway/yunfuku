package com.aifubook.book.flowapi

import androidx.appcompat.app.AppCompatActivity

/**
 *
 * @param activity 可能为null 一定要判断
 * @param e
 * @return
 */
fun handlerApiException(activity: AppCompatActivity?, e: ApiException): Boolean {
    val code: String? = e.code
    val message: String? = e.message
    when (code) {
        "2001" -> {
            if (activity != null) {

            }

            return true
        }
    }
    return false
}
