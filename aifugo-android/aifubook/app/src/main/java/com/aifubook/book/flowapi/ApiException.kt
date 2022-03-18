package com.aifubook.book.flowapi

class  ApiException  (val code:String?, private val msg:String):Exception(msg) {

}
