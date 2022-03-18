package com.aifubook.book.activity.main.shopdetail

import com.aifubook.book.bean.TypeBean

interface SelectItem {
    fun selectList(childrenBeans: TypeBean.ChildrenBean.ChildrenBeans)
    fun selectname(string: String)
}