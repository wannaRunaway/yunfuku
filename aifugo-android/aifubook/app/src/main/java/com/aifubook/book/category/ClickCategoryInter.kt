package com.aifubook.book.category

import com.aifubook.book.bean.ProductListBean
import com.aifubook.book.bean.TypeBean

interface ClickCategoryInter {
    fun clicksubject(bean: TypeBean.ChildrenBean)
    fun clickcategory(bean: TypeBean.ChildrenBean.ChildrenBeans)
    fun recommandClick(productListBeaninListBean: ProductListBean.list)
    fun addCartClick(productListBeaninListBean: ProductListBean.list)
}