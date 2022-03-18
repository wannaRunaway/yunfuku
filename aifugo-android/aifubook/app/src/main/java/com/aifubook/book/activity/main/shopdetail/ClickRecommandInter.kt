package com.aifubook.book.activity.main.shopdetail

import com.aifubook.book.bean.ProductListBean

interface ClickRecommandInter {
    fun clickrecommandproducts(bean: ProductListBean.list)
    fun clickrecommandaddCart(bean: ProductListBean.list)
}