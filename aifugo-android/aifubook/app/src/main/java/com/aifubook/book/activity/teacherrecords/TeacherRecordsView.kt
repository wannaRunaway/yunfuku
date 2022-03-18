package com.aifubook.book.activity.teacherrecords

import com.aifubook.book.mine.order.bean.OrderListBean
import com.jiarui.base.bases.BaseView

interface TeacherRecordsView : BaseView {
    fun orderList(orderListBean: OrderListBean, _isrefresh: Boolean)
    fun failed(message: String)
}